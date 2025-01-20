package com.wellcare.repository.eserciziGiornalieri;

import com.wellcare.model.ContenutoSchedaAllenamento;
import com.wellcare.model.EsercizioGiornaliero;
import com.wellcare.model.PastoGiornaliero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EserciziGiornalieriRepository extends JpaRepository<EsercizioGiornaliero, Integer>{

    @Query(value = "SELECT * FROM daily_exercise WHERE id_scheda_allenamento=:schedaAllenamento AND giorno=:giorno ", nativeQuery = true)
    public List<EsercizioGiornaliero> findByGiorno(@Param("schedaAllenamento") Integer id, @Param("giorno") Integer giorno);
    @Query(value = "SELECT * FROM daily_exercise WHERE id_scheda_allenamento=:schedaAllenamento", nativeQuery = true)
    public List<EsercizioGiornaliero> findBySchedaAllenamento(@Param("schedaAllenamento") Integer id);
    @Query(value = "SELECT * FROM daily_exercise WHERE id_cliente=:cliente", nativeQuery = true)
    public List<EsercizioGiornaliero> findByIdCliente(@Param("cliente") Integer id);
    @Query(value = "SELECT * FROM daily_exercise WHERE id_cliente=:Cliente AND data=:data", nativeQuery = true)
    public List<EsercizioGiornaliero> findByDataCreazione(@Param("Cliente") Integer id, @Param("data") String data);
}
