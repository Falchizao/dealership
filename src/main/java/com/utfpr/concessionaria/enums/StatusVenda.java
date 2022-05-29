package com.utfpr.concessionaria.enums;

import lombok.*;

@Getter
@ToString
public enum StatusVenda {
    ABERTA,
    FECHADA,
    CANCELADA,
    PENDENTE,
    PAGAMENTO_PENDENTE,
    PAGAMENTO_REALIZADO,
    PAGAMENTO_CANCELADO;

    StatusVenda() {}
}
