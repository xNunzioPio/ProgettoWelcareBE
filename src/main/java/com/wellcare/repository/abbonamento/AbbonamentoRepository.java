package com.wellcare.repository.abbonamento;

import com.wellcare.model.Abbonamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbbonamentoRepository extends JpaRepository<Abbonamento, Integer> {



}