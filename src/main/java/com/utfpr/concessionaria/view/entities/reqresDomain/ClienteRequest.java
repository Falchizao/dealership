package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

import javax.persistence.Column;


public class ClienteRequest {

    @Getter
    @Setter
    private String nomeCliente;

    @Getter
    @Setter
    private String cpfCliente;

    @Getter
    @Setter
    private String rgCliente;

    @Getter
    @Setter
    private String enderecoCliente;

    @Getter
    @Setter
    private String telefoneCliente;

    @Getter
    @Setter
    private String emailCliente;
}
