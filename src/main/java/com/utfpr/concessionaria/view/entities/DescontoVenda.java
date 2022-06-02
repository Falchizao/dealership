package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.enums.TipoDesconto;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DescontoVenda {

    @Getter
    @Setter
    public Venda venda;

    @Getter
    @Setter
    public Double valueDesconto;

    @Getter
    @Setter
    public TipoDesconto tipoDesconto;
}
