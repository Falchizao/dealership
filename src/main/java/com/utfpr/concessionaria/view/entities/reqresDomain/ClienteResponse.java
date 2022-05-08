package com.utfpr.concessionaria.view.entities.reqresDomain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClienteResponse {
    private Long idCliente;
    private String nomeCliente;
    private String emailCliente;
}
