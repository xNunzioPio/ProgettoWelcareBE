package com.wellcare.repository.cliente;

import com.wellcare.model.Cliente;
import com.wellcare.model.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query(value = "SELECT * FROM customer WHERE id_utente=:id", nativeQuery = true)
    public Optional<Cliente> findByIdUtente(@Param("id") Integer id);
}