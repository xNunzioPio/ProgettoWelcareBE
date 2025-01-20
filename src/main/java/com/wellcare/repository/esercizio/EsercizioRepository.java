package com.wellcare.repository.esercizio;

import com.wellcare.controller.dto.esercizio.EsercizioCreationDTO;
import com.wellcare.controller.dto.esercizio.EsercizioViewDTO;
import com.wellcare.model.Esercizio;
import com.wellcare.model.SchedaAllenamento;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsercizioRepository extends JpaRepository<Esercizio, Integer> {


    @Query(value = "SELECT * FROM exercise WHERE nome LIKE %:nome%", nativeQuery = true)
    public List<Esercizio> findByNome(@Param("nome") String nome);

    @Query(value = "SELECT descrizione FROM exercise WHERE id=:id", nativeQuery = true)
    public String findDescrizione(@Param("id") Integer id);
}