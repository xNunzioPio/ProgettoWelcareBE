package com.wellcare.repository.tipoRisorsa;


import com.wellcare.model.TipoRisorsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRisorsaRepository extends JpaRepository<TipoRisorsa, Integer> {
}
