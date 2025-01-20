package com.wellcare.repository.schedaAllenamento;

import com.wellcare.model.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface SchedaAllenamentoRepository extends JpaRepository<SchedaAllenamento, Integer> {

    @Query(value = "SELECT * FROM training_sheet WHERE id_personal_trainer=:id", nativeQuery = true)
    public List<SchedaAllenamento> findByIdPersonalTrainer(@Param("id") Integer id);
    @Query(value = "SELECT * FROM training_sheet WHERE id_cliente=:id", nativeQuery = true)
    public List<SchedaAllenamento> findByIdCliente(@Param("id") Integer id);
    @Query(value = "SELECT * FROM training_sheet WHERE data_creazione=:data", nativeQuery = true)
    public List<SchedaAllenamento> findByDataCreazione(@Param("data") Date data);
    @Query(value = "SELECT note FROM training_sheet WHERE id=:id", nativeQuery = true)
    public String findNoteById(@Param("id") Integer id);


}