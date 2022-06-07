package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import static com.utfpr.concessionaria.enums.TipoVenda.*;

@Slf4j
@Component
public record Utils(AtendentesCRUDservice atendentesCRUDservice) { //Service to deploy useful codes

    //Return the permission of the worker to apply discount
    public Integer tipoDescontoFuncionario(Venda venda) {
        PermissoesAtendente ps = atendentesCRUDservice.getPermissao(venda.getAtendente());
        return switch (ps) {
            case PERMITEDESCONTOCOMPLETO -> 1;
            case PERMITEDESCONTOPARCIAL -> 2;
            case SEMDESCONTO -> 3;
        };
    }

    //Return the Tipe of payment by its ordinal number
    public TipoVenda tipoPagamentoVendaByOrdinal(Integer ordinal) {
        return switch (ordinal) {
            case 0 -> DINHEIRO;
            case 1 -> PARCELADO;
            case 2 -> CARTAO_CREDITO;
            case 3 -> CARTAO_DEBITO;
            default -> null;
        };
    }

    //Return the entry price for the sale
    public BigDecimal getvalorentrada(BigDecimal saleValue, Double percentage){
        return saleValue.multiply(BigDecimal.valueOf(percentage));
    }

    public void imprimeInfo(int numeroParcela, int totalParcelas, BigDecimal valorEmCentavos, Calendar data) {
        log.info("Informação de venda: %02d/%02d\t%td/%tm/%ty\tR$ %.2f%n", numeroParcela,
                totalParcelas, data, data, data, valorEmCentavos);
    }

    public BigDecimal calculaValorParcela(BigDecimal juros, int numeroParcelas, BigDecimal valorParcelado) {
        BigDecimal potencia = juros.add(BigDecimal.ONE).pow(numeroParcelas);
        BigDecimal denominador = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(potencia, 20, RoundingMode.HALF_EVEN));
        return valorParcelado.multiply(juros).divide(denominador, 2, RoundingMode.HALF_EVEN);
    }

}
