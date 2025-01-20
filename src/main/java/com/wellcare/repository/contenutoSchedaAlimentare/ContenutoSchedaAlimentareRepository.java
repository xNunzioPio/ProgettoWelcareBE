package com.wellcare.repository.contenutoSchedaAlimentare;

import com.wellcare.model.ContenutoSchedaAlimentare;
import com.wellcare.model.SchedaAlimentare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenutoSchedaAlimentareRepository extends JpaRepository<ContenutoSchedaAlimentare, Integer> {
    @Query(value = "SELECT * FROM food_sheet_content WHERE priorita=:priorita", nativeQuery = true)
    public List<ContenutoSchedaAlimentare> findByPriorita(@Param("priorita") Integer priorita);

    @Query(value = "SELECT * FROM food_sheet_content WHERE id_scheda_alimentare=:id AND giorno=:giorno", nativeQuery = true)
    public List<ContenutoSchedaAlimentare> findByGiorno(@Param("id") Integer id,@Param("giorno") String giorno);

    @Query(value = "SELECT * FROM food_sheet_content WHERE id_scheda_alimentare=:schedaAlimentare", nativeQuery = true)
    public List<ContenutoSchedaAlimentare> findByIdSchedaAlimentare(@Param("schedaAlimentare") Integer id);

    @Query(value = "SELECT * FROM food_sheet_content WHERE principale=:principale", nativeQuery = true)
    public List<ContenutoSchedaAlimentare> findAlternativeBySchedaPrincipale(@Param("principale") Integer principale);
}