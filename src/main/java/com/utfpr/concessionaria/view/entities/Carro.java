package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tb_carro")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Carro extends IModel {


    @Column(name = "modelo_carro", length = 255)
    private String modelo;

    @Column(name = "marca_carro", length = 255)
    private String marca;

    @Column(name = "cor_carro", length = 255)
    private String cor;

    @Column(name = "data_carro")
    private Date ano;

    @Column(name = "placa_carro", length = 7)
    private String placa;

    @Column(name = "chassi_carro",length = 17)
    private String chassi;

    @Column(name = "valor_carro")
    private Double valor;



}
