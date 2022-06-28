package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

@Builder
public class AtendenteDTO extends IModel {

    @Getter
    @Setter
    private String nomeAtendente;

    @Getter
    @Setter
    private Integer permissao;


}
