package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;
import java.util.Date;

@Builder
public class CarroDTO extends IModel {

    @Getter
    @Setter
    private String modelo;

    @Getter
    @Setter
    private String marca;

    @Getter
    @Setter
    private String cor;

    @Getter
    @Setter
    private Date ano;

    @Getter
    @Setter
    private String placa;

    @Getter
    @Setter
    private String chassi;

    @Getter
    @Setter
    private Double valor;
}


