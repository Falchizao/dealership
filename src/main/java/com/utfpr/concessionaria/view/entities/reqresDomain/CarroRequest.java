package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;


public class CarroRequest {

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

    @Getter
    @Setter
    private Integer quantity;
}
