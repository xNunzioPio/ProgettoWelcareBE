package com.wellcare.repository.pastiGiornalieri;


import com.wellcare.model.PastoGiornaliero;;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PastiGiornalieriRepository extends JpaRepository<PastoGiornaliero, Integer>{

    @Query(value = "SELECT * FROM daily_meal WHERE id_scheda_alimentare=:schedaAlimentare AND giorno=:giorno ", nativeQuery = true)
    public List<PastoGiornaliero> findByGiorno(@Param("schedaAlimentare") Integer id, @Param("giorno") Integer giorno);

    @Query(value = "SELECT * FROM daily_meal WHERE id_scheda_alimentare=:schedaAlimentare", nativeQuery = true)
    public List<PastoGiornaliero> findBySchedaAlimentare(@Param("schedaAlimentare") Integer id);

    @Query(value = "SELECT * FROM daily_meal WHERE id_cliente=:Cliente", nativeQuery = true)
    public List<PastoGiornaliero> findByIdCliente(@Param("Cliente") Integer id);

    @Query(value = "SELECT * FROM daily_meal WHERE id_cliente=:Cliente AND data=:data", nativeQuery = true)
    public List<PastoGiornaliero> findByDataCreazione(@Param("Cliente") Integer id, @Param("data") String data);

}

