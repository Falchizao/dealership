package com.utfpr.concessionaria.models;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tb_carro")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carro")
    private Long idCarro;

    @Column(length = 255, nullable = false)
    private String modelo;

    @Column(length = 255, nullable = false)
    private String marca;

    @Column(length = 255, nullable = false)
    private String cor;

    @Column(length = 255, nullable = false)
    private Date ano;

    @Column(length = 255, nullable = false)
    private String placa;

    @Column(length = 255, nullable = false)
    private String chassi;



}
