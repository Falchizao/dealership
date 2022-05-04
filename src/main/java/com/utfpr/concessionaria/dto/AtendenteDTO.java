package com.utfpr.concessionaria.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AtendenteDTO {

    //Atributos Atendente
    private Long idAtendente;
    private String nomeAtendente;

    private AtendenteDTO atendente;

    public AtendenteDTO(Long idAtendente, String nomeAtendente) {
        this.idAtendente = idAtendente;
        this.nomeAtendente = nomeAtendente;
    }

    public AtendenteDTO(AtendenteDTO atendente) {
        this.idAtendente = atendente.getIdAtendente();
        this.nomeAtendente = atendente.getNomeAtendente();
    }
}
