package com.wellcare.repository.personalTrainer;

import com.wellcare.model.Cliente;
import com.wellcare.model.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Integer> {

    @Query(value = "SELECT * FROM personal_trainer WHERE id_utente=:id", nativeQuery = true)
    public Optional<PersonalTrainer> findByIdUtente(@Param("id") Integer id);
}