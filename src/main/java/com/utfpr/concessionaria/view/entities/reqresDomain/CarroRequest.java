package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarroRequest {

    private Long idCarro;
    private String modelo;
    private String marca;
    private String cor;
    private Date ano;
    private String placa;
    private String chassi;
}
