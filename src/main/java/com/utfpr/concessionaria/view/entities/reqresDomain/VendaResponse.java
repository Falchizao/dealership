package com.utfpr.concessionaria.view.entities.reqresDomain;

import com.utfpr.concessionaria.enums.StatusVenda;
import lombok.*;
import java.util.Date;

@Builder
public class VendaResponse {

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
    private Date dtaVenda;

    @Getter
    @Setter
    private StatusVenda statusVenda;

    @Getter
    @Setter
    private Long idPagamento;

    @Getter
    @Setter
    private String emailCliente;

}
