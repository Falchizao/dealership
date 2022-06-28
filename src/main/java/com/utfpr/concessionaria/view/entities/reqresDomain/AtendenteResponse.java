package com.utfpr.concessionaria.view.entities.reqresDomain;
import lombok.*;

@Builder
public class AtendenteResponse {

    @Getter
    @Setter
    private String nomeAtendente;

    @Getter
    @Setter
    private Integer permissoesAtendente;

}
