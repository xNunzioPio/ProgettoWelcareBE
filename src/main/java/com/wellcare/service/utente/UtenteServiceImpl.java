package com.wellcare.service.utente;

import com.wellcare.controller.dto.password.PasswordChangeDTO;
import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.controller.dto.risorsa.FileStorageDownload;
import com.wellcare.controller.dto.utente.UtenteUpdateDTO;
import com.wellcare.controller.dto.utente.UtenteViewDTO;
import com.wellcare.model.*;
import com.wellcare.repository.abbonamento.AbbonamentoRepository;
import com.wellcare.repository.personalTrainer.PersonalTrainerRepository;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.exceptions.UtenteException;
import com.wellcare.service.mail.MailService;
import com.wellcare.service.specializzazione.SpecializzazioneService;
import com.wellcare.utility.GCStorageConfiguration;
import com.wellcare.utility.MailTemplate;
import com.wellcare.utility.RandomicPassword;
import com.wellcare.validation.ValidatorRegistration;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteServiceImpl implements UtenteService{
    private final Logger log = LoggerFactory.getLogger(UtenteServiceImpl.class);
    @Autowired
    public GCStorageConfiguration configuration;
    SpecializzazioneService spService;

    public UtenteRepository repository;

    @Autowired
    public PersonalTrainerRepository personalTrainerRepository;

    @Autowired
    public AbbonamentoRepository abbonamentoRepository;


    private PasswordEncoder encoder;

    @Autowired
    private MailService emailSender;

    @Autowired
    RandomicPassword randomicPassword;

    @Autowired
    ValidatorRegistration validatorRegistration;

    @Value("${frontend.endpoint.login}")
    private String endpointLogin;

    @Autowired
    public UtenteServiceImpl (UtenteRepository repository, PasswordEncoder encoder, SpecializzazioneService spService){
        this.repository = repository;
        this.encoder = encoder;
        this.spService = spService;
    }

    @Override
    public UtenteViewDTO createUtente(RegistrationDTO dto) throws UtenteException {
        Utente utente=createUtenteCostructor(dto);

        utente=repository.save(utente);

        spService.specializzaUtente(dto,utente);

        return entityToDTO(utente);
    }

    @Override
    public void removeUtente(Integer id) {
        repository.deleteById(id);
    }

    @Transactional
    public byte[] uploadImage(MultipartFile multipartFile, Integer id) throws IOException, UtenteException {
        Optional<Utente> userOpt = repository.findById(id);
        Utente user = userOpt.get();

        String fileName = "profileImage" + user.getId() + ".jpg";
        String path = "users/" + user.getId() + "/" + fileName;

        configuration.writeGCSFile(multipartFile, path);
        user.setImgPath(fileName);
        repository.save(user);

        byte[] bytes = multipartFile.getBytes();
        return bytes;
    }

    @Override
    public FileStorageDownload readImage(Integer id) throws IOException {
        FileStorageDownload fileStorageDownload = new FileStorageDownload();
        Optional<Utente> userOpt = repository.findById(id);
        Utente user = userOpt.get();

        if(user.getImgPath() != null) {
            String img = user.getImgPath();
            String fileFullPath = "users/" + id + "/" + img;
            fileStorageDownload = configuration.readFGCSFile(fileFullPath, img);
        } else {
            throw new RuntimeException("Field null");
        }

        return fileStorageDownload;
    }

    @Override
    public UtenteViewDTO updateUtente(UtenteUpdateDTO dto, Integer id) throws UtenteException{

        if(id==null || id<1){
            throw new UtenteException("Id Utente non valido");
        }
        Optional<Utente> utente=repository.findById(id);

        if(!utente.isPresent()){
            throw new UtenteException("Modifica ai dati dell'Utente non riuscita !");
        }
        else {

            Utente utenteAggiornato=updateUtenteCostructor(dto,utente.get());
            utenteAggiornato=repository.save(utenteAggiornato);
            UtenteViewDTO viewDTO=entityToDTO(utenteAggiornato);
            return viewDTO;

        }

    }

    @Override
    public List<UtenteViewDTO> findAll() throws UtenteException{
        List<UtenteViewDTO> listViewDto=new ArrayList<UtenteViewDTO>();

        List<Utente> listEntity=repository.findAll();
        if(listEntity.isEmpty()){
            throw new UtenteException("Utente non trovato !");
        }
        for(Utente c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    @Override
    public UtenteViewDTO findById(Integer id) throws UtenteException{
        UtenteViewDTO viewDTO=new UtenteViewDTO();

        Optional<Utente> utente=repository.findById(id);

        if (!utente.isPresent()){
            throw new UtenteException("Utente non trovato !");
        }
        else {
            viewDTO=entityToDTO(utente.get());
        }
        return viewDTO;
    }

    @Override
    public UtenteViewDTO findByEmail(String email) throws UtenteException{
        UtenteViewDTO viewDTO=new UtenteViewDTO();

        Utente utente=repository.findByEmail(email);

        if (utente == null){
            throw new UtenteException("Utente non trovato !");
        }
        else {
            viewDTO=entityToDTO(utente);
        }
        return viewDTO;
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordChange, boolean isPublic) {

        // Validate pwd change (password equals repeatPassword) and email
        validatorRegistration.validateUserOnChangePassword(passwordChange);
        log.info("----- Validation change password dto successful done -----");

        // Retrieve user by his email
        try{
            Utente user = repository.findByEmail(passwordChange.getEmail());
            log.info("----- User retrieved by email with success -----");

            // Check if the old password is equals to the parameter put by user
            // If not throw an exception
            checkOldPassword(user.getPassword(), passwordChange.getOldPassword());
            log.info("----- Validation old password successful -----");

            // Change password
            String password = encodePassword(passwordChange.getPassword());
            user.setPassword(password);
            log.info("----- User password changed successful -----");

            // If the change password's method is called by a public api
            // we have to verify the email, so let's to send an email to user
            if (isPublic) {
                changePwdAndSendMail(user);
            }

            // Let's save the new password, and eventually others data, in database
            repository.save(user);
            log.info("----- New password saved in database successful -----");

        } catch (Exception e){
            e.getStackTrace();
        }
    }

    @Override
    @Transactional
    public void generatePasswordAndSendEmail(String email) {

        try{
            // We get the user by email throw by backend
            Utente user = repository.findByEmail(email);
            log.info("----- User found successful -----");

            // Generate a new password
            String newPasswordDecr = randomicPassword.getRandomicPassword();
            log.info("----- Created new password -----");

            // Encrypt new Password
            String newPasswordEncr = encodePassword(newPasswordDecr);
            log.info("----- Encrypted new password -----");

            // Update user with new password
            user.setPassword(newPasswordEncr);
            user = repository.save(user);
            log.info("----- User update successful -----");

            String text = String.format(
                    MailTemplate.GEN_PASS,
                    newPasswordDecr,
                    endpointLogin + "?newPassword=true"
            );
            String subject = String.format(MailTemplate.GEN_PASS_TITLE);
            emailSender.sendSingleMailHTML(user.getEmail(), subject, text);
            log.info("----- Email sent successful -----");
        } catch (Exception e){
            e.getStackTrace();
        }

    }

    private void changePwdAndSendMail(Utente user) {

        String text = String.format(
                MailTemplate.CONF_CHANGE_PASS,
                user.getEmail(),
                endpointLogin
        );
        String subject = String.format(MailTemplate.CONF_CHANGE_PASS_TITLE);
        emailSender.sendSingleMailHTML(user.getEmail(), subject, text);
        log.info("----- Successful mail verification sent --------");

    }

    private String encodePassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);

    }

    private void checkOldPassword(String oldPassword, String actualPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(actualPassword, oldPassword)) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<UtenteViewDTO> findByNome(String nome) throws UtenteException{
        List<UtenteViewDTO> listViewDto=new ArrayList<UtenteViewDTO>();

        List<Utente> listEntity=repository.findByNome(nome);
        if(listEntity.isEmpty()){
            throw new UtenteException("Utente non trovato !");
        }
        for(Utente c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    @Override
    public List<UtenteViewDTO> findByCognome(String cognome) throws UtenteException{
        List<UtenteViewDTO> listViewDto=new ArrayList<UtenteViewDTO>();

        List<Utente> listEntity=repository.findByCognome(cognome);
        if(listEntity.isEmpty()){
            throw new UtenteException("Utente non trovato !");
        }
        for(Utente c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    @Override
    public List<UtenteViewDTO> findByCittà(String città) throws UtenteException{
        List<UtenteViewDTO> listViewDto=new ArrayList<UtenteViewDTO>();

        List<Utente> listEntity=repository.findByCittà(città);
        if(listEntity.isEmpty()){
            throw new UtenteException("Utente non trovato !");
        }
        for(Utente c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    @Override
    public UtenteViewDTO findByTelefono(String tel) throws UtenteException{
        UtenteViewDTO viewDTO=new UtenteViewDTO();

        Utente utente=repository.findByTelefono(tel);

        if (utente == null){
            throw new UtenteException("Utente non trovato !");
        }
        else {
            viewDTO=entityToDTO(utente);
        }
        return viewDTO;
    }

    @Override
    public List<UtenteViewDTO> findByRuolo(Integer ruolo) throws UtenteException{
        List<UtenteViewDTO> listViewDto=new ArrayList<UtenteViewDTO>();

        List<Utente> listEntity=repository.findByRuolo(ruolo);
        if(listEntity.isEmpty()){
            throw new UtenteException("Utente non trovato !");
        }
        for(Utente c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }


    private Utente createUtenteCostructor(RegistrationDTO dto) throws UtenteException {
        Utente utente=new Utente();


        boolean matchNome=dto.getNome().matches("^[A-Za-zà-ù’]{2,20}+$");
        //non deve essere null
        if(dto.getNome() == null || dto.getNome().equals("") || dto.getNome().length() <2 || dto.getNome().length() > 20 || !matchNome)
            throw new UtenteException("Nome utente non valido");
        else
            utente.setNome(dto.getNome());

        boolean matchCognome=dto.getCognome().matches("^[A-Za-zà-ù’]{2,20}+$");
        if(dto.getCognome() == null || dto.getCognome().equals("" ) || dto.getCognome().length() <2 || dto.getCognome().length() > 20 || !matchCognome)
            throw new UtenteException("Cognome utente non valido");
        else
            utente.setCognome(dto.getCognome());

        utente.setDataNascita(dto.getDataNascita());

        boolean matchEmail=dto.getEmail().matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,10}$");
        if(dto.getEmail() == null || dto.getEmail().equals("") || !matchEmail || dto.getEmail().equals("genny.esp@gmail.com"))
            throw new UtenteException("Email non valida!");
        else
            utente.setEmail(dto.getEmail());

        boolean matchPass=dto.getPassword().matches("^[A-Za-zà-ù0-9.!?@_\\-,&()]{8,16}$");
        if(dto.getPassword() == null || dto.getPassword().equals("") || dto.getPassword().length() <8 || dto.getPassword().length() > 16 || !matchPass)
            throw new UtenteException("Password non valida!");
        else
            utente.setPassword(encoder.encode(dto.getPassword()));

        boolean matchNum=dto.getNumTel().matches("^[0-9]{10}$");
        if(dto.getNumTel() == null || dto.getNumTel().equals("") || dto.getNumTel().length() != 10 || !matchNum || dto.getNumTel().equals("0001112223"))
            throw new UtenteException("Numero di telefono non valido!");
        else
            utente.setNumTel(dto.getNumTel());

        boolean matchCittà=dto.getCitta().matches("^[A-Za-zà-ù ’]{2,25}+$");
        if(dto.getCitta() == null || dto.getCitta().equals("") || dto.getCitta().length() <2 || dto.getCitta().length() > 25 || !matchCittà)
            throw new UtenteException("Città non valida!");
        else
            utente.setCitta(dto.getCitta());

        boolean matchCap=dto.getCap().matches("^[0-9]{5}$");
        if(dto.getCap() == null || dto.getCap().equals("") || dto.getCap().length() != 5 || !matchCap)
            throw new UtenteException("Cap non valido!");
        else
            utente.setCap(dto.getCap());

        utente.setImgPath(null);

        return utente;
    }

    private Utente updateUtenteCostructor(UtenteUpdateDTO dto,Utente utente) throws UtenteException {

        boolean matchNome=dto.getNome().matches("^[A-Za-zà-ù’]{2,20}+$");
        //non deve essere null
        if(dto.getNome() == null || dto.getNome().equals("") || dto.getNome().length() <2 || dto.getNome().length() > 20 || !matchNome)
            throw new UtenteException("Nome utente non valido");
        else
            utente.setNome(dto.getNome());

        boolean matchCognome=dto.getCognome().matches("^[A-Za-zà-ù’]{2,20}+$");
        if(dto.getCognome() == null || dto.getCognome().equals("" ) || dto.getCognome().length() <2 || dto.getCognome().length() > 20 || !matchCognome)
            throw new UtenteException("Cognome utente non valido");
        else
            utente.setCognome(dto.getCognome());

        utente.setDataNascita(dto.getDataNascita());

        boolean matchEmail=dto.getEmail().matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,10}$");
        if(dto.getEmail() == null || dto.getEmail().equals("") || !matchEmail || dto.getEmail().equals("MimRos@gmail.com"))
            throw new UtenteException("Email non valida!");
        else
            utente.setEmail(dto.getEmail());

        boolean matchNum=dto.getNumTel().matches("^[0-9]{10}$");
        if(dto.getNumTel() == null || dto.getNumTel().equals("") || dto.getNumTel().length() != 10 || !matchNum || dto.getNumTel().equals("2233445566"))
            throw new UtenteException("Numero di telefono non valido!");
        else
            utente.setNumTel(dto.getNumTel());

        boolean matchCittà=dto.getCitta().matches("^[A-Za-zà-ù ’]{2,25}+$");
        if(dto.getCitta() == null || dto.getCitta().equals("") || dto.getCitta().length() <2 || dto.getCitta().length() > 25 || !matchCittà)
            throw new UtenteException("Città non valida!");
        else
            utente.setCitta(dto.getCitta());

        boolean matchCap=dto.getCap().matches("^[0-9]{5}$");
        if(dto.getCap() == null || dto.getCap().equals("") || dto.getCap().length() != 5 || !matchCap)
            throw new UtenteException("Cap non valido!");
        else
            utente.setCap(dto.getCap());


        utente.setImgPath(dto.getImgPath());

        return utente;
    }

    private UtenteViewDTO entityToDTO(Utente utente){
        UtenteViewDTO viewDTO=new UtenteViewDTO();

        viewDTO.setId(utente.getId());
        viewDTO.setNome(utente.getNome());
        viewDTO.setCognome(utente.getCognome());
        viewDTO.setDataNascita(utente.getDataNascita());
        viewDTO.setEmail(utente.getEmail());
        viewDTO.setPassword(utente.getPassword());
        viewDTO.setNumTel(utente.getNumTel());
        viewDTO.setCitta(utente.getCitta());
        viewDTO.setCap(utente.getCap());
        viewDTO.setImgPath(utente.getImgPath());


        return viewDTO;
    }



}
