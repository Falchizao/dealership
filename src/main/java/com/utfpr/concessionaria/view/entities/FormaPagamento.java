package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity(name = "forma_pagamento")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FormaPagamento extends IModel {

    @Getter
    @Setter
    @Column(name = "tipoVenda")
    private TipoVenda tipoVenda;

    @Getter
    @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Getter
    @Setter
    @Column(name = "descricao")
    private TipoVenda descricao;

    @Getter
    @Setter
    @Column(name = "parcelas")
    private Integer parcelas;

    @Getter
    @Setter
    @Column(name = "valor_parcelas")
    private BigDecimal valor_parcelas;

    @Getter
    @Setter
    @Column(name = "juros")
    private BigDecimal juros;

    @Getter
    @Setter
    @Column(name = "desconto")
    private BigDecimal desconto;

    @Getter
    @Setter
    @Column(name = "valor_final_desconto")
    private BigDecimal valorFinal;

    @Setter
    @Column(name = "entrada_venda")
    private BigDecimal entradaVenda;
}


