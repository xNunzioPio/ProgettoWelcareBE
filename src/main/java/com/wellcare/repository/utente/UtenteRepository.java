package com.wellcare.repository.utente;

import com.wellcare.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    @Query(value = "SELECT * FROM user WHERE email=:email AND password=:password", nativeQuery = true)
    public Utente findUtente(@Param("email") String email,@Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)
    public Utente findByEmail(@Param("email") String email);
    @Query(value = "SELECT * FROM user WHERE nome LIKE %:nome%", nativeQuery = true)
    List<Utente> findByNome(@Param("nome") String nome);
    @Query(value = "SELECT * FROM user WHERE cognome LIKE %:cognome%", nativeQuery = true)
    List<Utente> findByCognome(@Param("cognome") String cognome);
    @Query(value = "SELECT * FROM user WHERE numTel=:numTel", nativeQuery = true)
    Utente findByTelefono(@Param("numTel") String n);
    @Query(value = "SELECT * FROM user WHERE citta=:citta", nativeQuery = true)
    List<Utente> findByCittà(@Param("citta") String n);
    @Query(value = "SELECT * FROM user WHERE ruolo=:ruolo", nativeQuery = true)
    List<Utente> findByRuolo(@Param("ruolo") Integer ruolo);

    boolean existsByEmail(String email);

}