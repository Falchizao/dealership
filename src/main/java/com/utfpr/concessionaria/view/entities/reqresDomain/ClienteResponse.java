package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;


@Builder
public class ClienteResponse {

    @Getter
    @Setter
    private String nomeCliente;

    @Getter
    @Setter
    private String emailCliente;
}
