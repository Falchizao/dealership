package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import com.utfpr.concessionaria.enums.StatusVenda;
import com.utfpr.concessionaria.enums.TipoVenda;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "tb_venda")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Venda extends IModel {

    @Getter
    @Setter
    @Column(name = "id_cliente")
    private Long idCliente;

    @Getter
    @Setter
    @Column(name = "id_carro")
    private Long carro;

    @Getter
    @Setter
    @Column(name = "id_funcionario")
    private Long atendente;

    @Getter
    @Setter
    @Column(name = "dt_venda")
    private Date data_venda;

    @Getter
    @Setter
    @Column(name = "status_venda")
    private StatusVenda statusVenda;

    @Getter
    @Setter
    @Column(name = "email_cliente")
    private String emailCliente;

    @Getter
    @Setter
    @Column(name = "id_pagamento")
    private Long idPagamento;

}
