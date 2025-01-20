package com.wellcare.repository.risorsa;

import com.wellcare.model.Risorsa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RisorsaRepository extends JpaRepository<Risorsa, Integer> {
    @Query(value = "SELECT * FROM resource WHERE id_utente=:id", nativeQuery = true)
    public List<Risorsa> findByIdUtente(@Param("id") Integer id);

    @Query(value = "SELECT * FROM resource WHERE id_tipo=:id", nativeQuery = true)
    public List<Risorsa> findByIdTipo(@Param("id") Integer id);

    @Query(value = "SELECT * FROM resource WHERE path=:path", nativeQuery = true)
    public List<Risorsa> findByPath(@Param("path") String path);
}