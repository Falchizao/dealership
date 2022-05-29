package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.enums.TipoVenda;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamento {
    private TipoVenda tipoVenda;
    private String descricao;
    private Double valor;
    private Integer parcelas;
}


