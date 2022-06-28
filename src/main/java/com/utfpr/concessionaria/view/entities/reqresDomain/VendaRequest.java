package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

public class VendaRequest {

    @Getter
    @Setter
    private Long idCliente;

    @Getter
    @Setter
    private Long idCarro;

    @Getter
    @Setter
    private Long atendente;

    @Getter
    @Setter
    private Integer formaPagamento;
}
