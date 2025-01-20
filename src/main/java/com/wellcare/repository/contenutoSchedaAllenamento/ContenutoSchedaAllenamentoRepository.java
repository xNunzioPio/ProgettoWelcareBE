package com.wellcare.repository.contenutoSchedaAllenamento;

import com.wellcare.model.ContenutoSchedaAllenamento;
import com.wellcare.model.SchedaAllenamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenutoSchedaAllenamentoRepository extends JpaRepository<ContenutoSchedaAllenamento, Integer> {

    @Query(value = "SELECT * FROM training_sheet_content WHERE priorita=:priorita", nativeQuery = true)
    public List<ContenutoSchedaAllenamento> findByPriorità(@Param("priorita") Integer prio);

    @Query(value = "SELECT * FROM training_sheet_content WHERE id_scheda_allenamento=:schedaAllenamento AND giorno=:giorno ", nativeQuery = true)
    public List<ContenutoSchedaAllenamento> findByGiorno(@Param("schedaAllenamento") Integer id,@Param("giorno") Integer giorno);

    @Query(value = "SELECT * FROM training_sheet_content WHERE id_scheda_allenamento=:schedaAllenamento", nativeQuery = true)
    public List<ContenutoSchedaAllenamento> findByIdSchedaAllenamento(@Param("schedaAllenamento") Integer id);

    @Query(value = "SELECT * FROM training_sheet_content WHERE principale=:principale", nativeQuery = true)
    public List<ContenutoSchedaAllenamento> findAlternativeBySchedaPrincipale(@Param("principale") Integer idSchedaPrincipale);

}