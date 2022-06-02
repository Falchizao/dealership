package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.generic.IModel;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "tb_carro")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Carro extends IModel {

    @Getter
    @Setter
    @Column(name = "modelo_carro", length = 255)
    private String modelo;

    @Getter
    @Setter
    @Column(name = "marca_carro", length = 255)
    private String marca;

    @Getter
    @Setter
    @Column(name = "cor_carro", length = 255)
    private String cor;

    @Getter
    @Setter
    @Column(name = "data_carro")
    private Date ano;

    @Getter
    @Setter
    @Column(name = "placa_carro", length = 7)
    private String placa;

    @Getter
    @Setter
    @Column(name = "chassi_carro",length = 17)
    private String chassi;

    @Getter
    @Setter
    @Column(name = "valor_carro")
    private Double valor;



}
