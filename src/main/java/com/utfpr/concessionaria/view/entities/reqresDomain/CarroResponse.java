package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CarroResponse {

    private Long idCarro;
    private String modelo;
    private String marca;
    private String cor;
    private Date ano;
}
