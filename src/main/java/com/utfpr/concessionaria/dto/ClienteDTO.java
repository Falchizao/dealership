package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.models.Cliente;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteDTO {

    //Atributos Cliente
    private Long idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private String rgCliente;
    private String enderecoCliente;
    private String telefoneCliente;
    private String emailCliente;

    private ClienteDTO cliente;

    public ClienteDTO(Long idCliente, String nomeCliente, String cpfCliente, String rgCliente, String enderecoCliente, String telefoneCliente, String emailCliente) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
        this.rgCliente = rgCliente;
        this.enderecoCliente = enderecoCliente;
        this.telefoneCliente = telefoneCliente;
        this.emailCliente = emailCliente;
    }

    public ClienteDTO(Cliente cliente){
        this.idCliente = cliente.getIdCliente();
        this.nomeCliente = cliente.getNomeCliente();
        this.cpfCliente = cliente.getCpfCliente();
        this.rgCliente = cliente.getRgCliente();
        this.enderecoCliente = cliente.getEnderecoCliente();
        this.telefoneCliente = cliente.getTelefoneCliente();
        this.emailCliente = cliente.getEmailCliente();
    }
}
