package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
public class AtendenteDTO extends IModel {

    //Atributos Atendente

    private String nomeAtendente;
    private Integer permissao;


}
