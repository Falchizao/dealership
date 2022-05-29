package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.enums.StatusVenda;
import lombok.*;

import java.util.Date;


@Getter
@Setter
public class VendaDTO {

    private Long idCliente;
    private Long idCarro;
    private Long atendente;
    private String formaPagamento;
    private Date venda;
    private StatusVenda statusVenda;
    private Double valorTotal;
    private String emailCliente;




}
