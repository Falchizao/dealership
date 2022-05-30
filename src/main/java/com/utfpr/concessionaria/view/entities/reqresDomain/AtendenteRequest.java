package com.utfpr.concessionaria.view.entities.reqresDomain;

import com.utfpr.concessionaria.enums.PermissoesAtendente;
import lombok.*;

@Getter
@Setter
public class AtendenteRequest {

    private String nomeAtendente;
    private Integer permissao;

}
