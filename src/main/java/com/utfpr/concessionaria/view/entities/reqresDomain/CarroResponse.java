package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
public class CarroResponse {


    private String modelo;
    private String marca;
    private String cor;
    private Date ano;
}
