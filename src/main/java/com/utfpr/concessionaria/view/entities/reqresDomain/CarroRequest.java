package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class CarroRequest {
    private String modelo;
    private String marca;
    private String cor;
    private Date ano;
    private String placa;
    private String chassi;
    private Double valor;
}
