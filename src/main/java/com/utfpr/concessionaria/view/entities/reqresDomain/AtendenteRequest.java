package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AtendenteRequest {

    private String nomeAtendente;
    private Long idAtendente;
}
