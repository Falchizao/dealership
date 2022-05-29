package com.utfpr.concessionaria.view.entities.reqresDomain;

import com.utfpr.concessionaria.view.entities.Atendente;
import com.utfpr.concessionaria.view.entities.Cliente;
import com.utfpr.concessionaria.enums.StatusVenda;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class VendaRequest {


    private Long idCliente;
    private Long idCarro;
    private Long atendente;
    private String formaPagamento;
}
