package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteRequest {

    private Long idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private String rgCliente;
    private String enderecoCliente;
    private String telefoneCliente;
    private String emailCliente;
}
