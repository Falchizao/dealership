package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import com.utfpr.concessionaria.enums.StatusVenda;
import com.utfpr.concessionaria.enums.TipoVenda;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "tb_venda")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Venda extends IModel {

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_carro")
    private Long carro;

    @Column(name = "id_funcionario")
    private Long atendente;

    @Column(name = "dt_venda")
    private Date data_venda;

    @Column(name = "status_venda")
    private StatusVenda statusVenda;

    @Column(name = "email_cliente")
    private String emailCliente;

    @Column(name = "id_pagamento")
    private Long idPagamento;

}
