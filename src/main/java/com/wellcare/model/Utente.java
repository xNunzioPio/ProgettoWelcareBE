package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Data
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cognome;
    @Column(name = "data_nascita", nullable = false)
    private Date dataNascita;
    private String email;
    private String password;
    @Column(name = "num_tel", nullable = false)
    private String numTel;
    private String citta;
    private String cap;
    @Column(name = "img_path",nullable = true)
    private String imgPath;

    @Transient
    private Set<UserRole> roles = new HashSet<>(Collections.singletonList(UserRole.USER));

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

}
