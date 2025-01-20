package com.wellcare.repository.schedaAlimentare;

import com.wellcare.model.SchedaAlimentare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SchedaAlimentareRepository extends JpaRepository<SchedaAlimentare, Integer> {
    @Query(value = "SELECT * FROM food_sheet WHERE id_nutrizionista=:id", nativeQuery = true)
    public List<SchedaAlimentare> findByIdNutrizionista(@Param("id") Integer id);
    @Query(value = "SELECT * FROM food_sheet WHERE id_cliente=:id", nativeQuery = true)
    public List<SchedaAlimentare> findByIdCliente(@Param("id") Integer id);
    @Query(value = "SELECT * FROM food_sheet WHERE data_creazione=:data", nativeQuery = true)
    public List<SchedaAlimentare> findByDataCreazione(@Param("data") Date data);
}