package com.utfpr.concessionaria.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@Entity(name = "tb_venda")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Venda {
    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venda")
    private Long idVenda;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_carro")
    private Long idCarro;

//    @OneToMany(mappedBy = "venda")
    @Column(name = "id_funcionario")
    private Long idFuncionario;

    @Column(name = "dt_venda")
    private Date venda;


//    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "venda") //Varios Atendentes para uma venda
    @Column(name = "forma_pagamento")
    private Integer formaPagamento;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "email_cliente")
    private String emailCliente;

}
