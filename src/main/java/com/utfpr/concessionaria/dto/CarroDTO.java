package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class CarroDTO extends IModel {


    private String modelo;
    private String marca;
    private String cor;
    private Date ano;
    private String placa;
    private String chassi;
    private Double valor;


}


