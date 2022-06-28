package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.enums.StatusVenda;
import lombok.*;
import java.util.Date;

@Builder
public class VendaDTO {

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
    private Long idPagamento;

    @Getter
    @Setter
    private Date data_venda;

    @Getter
    @Setter
    private Integer formaPagamento;

    @Getter
    @Setter
    private StatusVenda statusVenda;

    @Getter
    @Setter
    private String emailCliente;
}
