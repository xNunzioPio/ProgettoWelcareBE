package com.wellcare.service.nutrizionista;

import com.wellcare.controller.dto.nutrizionista.CustomerAdditionToNutritionistDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerRemovalToNutritionistDTO;
import com.wellcare.controller.dto.personalTrainer.CustomerRemovalToPersonalTrainerDTO;
import com.wellcare.controller.dto.registration.NutrizionistaCreationDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaUpdateDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;

import java.util.List;

public interface NutrizionistaService {

    public NutrizionistaViewDTO createNutrizionista(NutrizionistaCreationDTO dto);

    public NutrizionistaViewDTO updateNutrizionista(NutrizionistaUpdateDTO dto,Integer id)throws NutrizionistaException;

    public NutrizionistaViewDTO acceptCustomerRequest(CustomerAdditionToNutritionistDTO dto,Integer id)throws NutrizionistaException;

    public SuccessResponseDTO rejectCustomerRequest(CustomerRemovalToNutritionistDTO dto, Integer id) throws NutrizionistaException;
    public NutrizionistaViewDTO removeCustomer(CustomerRemovalToNutritionistDTO dto,Integer id) throws NutrizionistaException;

    public List<NutrizionistaViewDTO> findAll() throws NutrizionistaException;
    public NutrizionistaViewDTO findById(Integer id) throws NutrizionistaException;
    public NutrizionistaViewDTO findByIdUtente(Integer id) throws NutrizionistaException;

}
