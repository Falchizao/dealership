package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

@Builder
public class ClienteDTO extends IModel {

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
