package com.utfpr.concessionaria.models;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Atendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atendente")
    private Long idAtendente;

    @Column(name = "nome_atendente")
    private String nomeAtendente;


}
