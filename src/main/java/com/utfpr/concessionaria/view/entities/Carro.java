package com.utfpr.concessionaria.view.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tb_carro")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carro")
    private Long idCarro;

    @Column(name = "modelo_carro", length = 255, nullable = false)
    private String modelo;

    @Column(name = "marca_carro", length = 255, nullable = false)
    private String marca;

    @Column(name = "cor_carro", length = 255, nullable = false)
    private String cor;

    @Column(name = "data_carro",nullable = false)
    private Date ano;

    @Column(name = "placa_carro", length = 7, nullable = false)
    private String placa;

    @Column(name = "chassi_carro",length = 17, nullable = false)
    private String chassi;

}
