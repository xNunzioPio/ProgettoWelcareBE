package com.wellcare.service.personalTrainer;

import com.wellcare.controller.dto.nutrizionista.CustomerAdditionToNutritionistDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerRemovalToNutritionistDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.personalTrainer.CustomerAdditionToPersonalTrainerDTO;
import com.wellcare.controller.dto.personalTrainer.CustomerRemovalToPersonalTrainerDTO;
import com.wellcare.controller.dto.registration.PersonalTrainerCreationDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerUpdateDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;

import java.util.List;

public interface PersonalTrainerService {

    public PersonalTrainerViewDTO createPersonalTrainer(PersonalTrainerCreationDTO dto);
    public PersonalTrainerViewDTO updatePersonalTrainer(PersonalTrainerUpdateDTO dto,Integer id)throws PersonalTrainerException;


    public PersonalTrainerViewDTO acceptCustomerRequest(CustomerAdditionToPersonalTrainerDTO dto, Integer id)throws PersonalTrainerException;
    public SuccessResponseDTO rejectCustomerRequest(CustomerRemovalToPersonalTrainerDTO dto, Integer id) throws PersonalTrainerException;
    public PersonalTrainerViewDTO removeCustomer(CustomerRemovalToPersonalTrainerDTO dto, Integer id) throws PersonalTrainerException;




    public List<PersonalTrainerViewDTO> findAll() throws PersonalTrainerException;
    public PersonalTrainerViewDTO findById(Integer id) throws PersonalTrainerException;
    public PersonalTrainerViewDTO findByIdUtente(Integer id) throws PersonalTrainerException;
}
