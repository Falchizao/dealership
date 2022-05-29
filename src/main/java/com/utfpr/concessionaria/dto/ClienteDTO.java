package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

@Getter
@Setter
public class ClienteDTO extends IModel {


    private String nomeCliente;
    private String cpfCliente;
    private String rgCliente;
    private String enderecoCliente;
    private String telefoneCliente;
    private String emailCliente;




}
