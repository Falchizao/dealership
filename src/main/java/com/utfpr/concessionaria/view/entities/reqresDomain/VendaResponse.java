package com.utfpr.concessionaria.view.entities.reqresDomain;

import com.utfpr.concessionaria.view.entities.Atendente;
import com.utfpr.concessionaria.view.entities.Cliente;
import com.utfpr.concessionaria.view.entities.enums.StatusVenda;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VendaResponse {

    private Long idVenda;
    private Set<Cliente> idCliente;
    private Long idCarro;
    private Set<Atendente> atendente;
    private Date venda;
    private String formaPagamento;
    private StatusVenda statusVenda;
    private BigDecimal valorTotal;
    private String emailCliente;

}
