package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.Column;


@Builder
public class AtendenteDTO extends IModel {

    //Atributos Atendente

    @Getter
    @Setter
    private String nomeAtendente;

    @Getter
    @Setter
    private Integer permissao;



}
