package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.view.entities.Atendente;
import com.utfpr.concessionaria.view.entities.Cliente;
import com.utfpr.concessionaria.view.entities.enums.StatusVenda;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VendaDTO {

    private Long idVenda;
    private Set<Cliente> idCliente;
    private Long idCarro;
    private Set<Atendente> atendente;
    private Date venda;
    private String formaPagamento;
    private StatusVenda statusVenda;
    private BigDecimal valorTotal;
    private String emailCliente;

    private VendaDTO vendaDTO;

    public VendaDTO(VendaDTO vendaDTO) {
        this.idVenda = vendaDTO.getIdVenda();
        this.idCliente = vendaDTO.getIdCliente();
        this.idCarro = vendaDTO.getIdCarro();
        this.atendente = vendaDTO.getAtendente();
        this.venda = vendaDTO.getVenda();
        this.formaPagamento = vendaDTO.getFormaPagamento();
        this.statusVenda = vendaDTO.getStatusVenda();
        this.valorTotal = vendaDTO.getValorTotal();
        this.emailCliente = vendaDTO.getEmailCliente();

    }

    public VendaDTO(Long idVenda, Set<Cliente> idCliente, Long idCarro, Set<Atendente> atendente, Date venda, String formaPagamento, StatusVenda statusVenda, BigDecimal valorTotal, String emailCliente) {
        this.idVenda = idVenda;
        this.idCliente = idCliente;
        this.idCarro = idCarro;
        this.atendente = atendente;
        this.venda = venda;
        this.formaPagamento = formaPagamento;
        this.statusVenda = statusVenda;
        this.valorTotal = valorTotal;
        this.emailCliente = emailCliente;
    }
}
