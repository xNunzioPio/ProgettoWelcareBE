package com.wellcare.repository.nutrizionista;

import com.wellcare.model.Cliente;
import com.wellcare.model.Nutrizionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutrizionistaRepository extends JpaRepository<Nutrizionista, Integer> {

    @Query(value = "SELECT * FROM nutritionist WHERE id_utente=:id", nativeQuery = true)
    public Optional<Nutrizionista> findByIdUtente(@Param("id") Integer id);

}