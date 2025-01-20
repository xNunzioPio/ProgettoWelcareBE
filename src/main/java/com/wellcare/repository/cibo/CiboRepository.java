package com.wellcare.repository.cibo;

import com.wellcare.model.Cibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CiboRepository extends JpaRepository<Cibo, Integer> {
    @Query(value = "SELECT * FROM food WHERE nome LIKE %:nome%", nativeQuery = true)
    public List<Cibo> findByNome(@Param("nome") String nome);

}