package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "forma_pagamento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FormaPagamento extends IModel {

    @Column(name = "tipoVenda")
    private TipoVenda tipoVenda;

    @Column(name = "valor_total")
    private Double valorTotal;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "parcelas")
    private Integer parcelas;

    @Column(name = "desconto")
    private BigDecimal desconto;

    @Column(name = "valor_final_desconto")
    private BigDecimal valorFinal;
}


