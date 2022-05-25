package com.utfpr.concessionaria.view.entities;

import com.utfpr.concessionaria.enums.TipoDesconto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DescontoVenda {
    public Venda venda;
    public Double valueDesconto;
    public TipoDesconto tipoDesconto;
}
