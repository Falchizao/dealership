package com.utfpr.concessionaria.dto;

import com.utfpr.concessionaria.models.Carro;
import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarroDTO {

    //Atributos Carro
    private Long idCarro;
    private String modelo;
    private String marca;
    private String cor;
    private Date ano;
    private String placa;
    private String chassi;

    private CarroDTO carro;

    public CarroDTO(Long idCarro, String modelo, String marca, String cor, Date ano, String placa, String chassi) {
        this.idCarro = idCarro;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.ano = ano;
        this.placa = placa;
        this.chassi = chassi;
    }

    public CarroDTO(Carro carro) {
        this.idCarro = carro.getIdCarro();
        this.modelo = carro.getModelo();
        this.marca = carro.getMarca();
        this.cor = carro.getCor();
        this.ano = carro.getAno();
        this.placa = carro.getPlaca();
        this.chassi = carro.getChassi();
    }

}


