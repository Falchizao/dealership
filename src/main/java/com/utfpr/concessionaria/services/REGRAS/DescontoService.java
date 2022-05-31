package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.modelException.error.ErrorMessage;
import com.utfpr.concessionaria.repositores.PaymentRepository;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.FormaPagamento;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
public record DescontoService(VendaRepository vendaRepository, AtendentesCRUDservice atendentesCRUDservice, ParcelamentoService parcelamentoService,
                              CarrosCRUDservice carrosCRUDservice){

    @Value("${desconto.condicao.um}")
    private static int conditionDiscount;
    @Value("${desconto.condicao.dois}")
    private static int conditionDiscountTwo;
    @Value("${desconto.condicao.tres}")
    private static int conditionDiscountThree;
    @Value("${desconto.parcelado}")
    private static int discountParcelado;
    @Value("${desconto.debito}")
    private static int discountDebito;
    @Value("${desconto.credito}")
    private static int disctountCredito;
    @Value("${desconto.dinheiro}")
    private static int discountDinheiro;


    //Aqui vão as regras de desconto da minha concessionaria em relação a descontos de venda
    public Venda inspecionaValorVenda(Venda venda, Integer typepayment) {
        log.info("Inspecionando valor da venda para atribuição de desconto");

        Optional<CarroDTO> car = carrosCRUDservice.getById(venda.getCarro()); //Valor Compra

        Long idVenda = setDesconto(car.get().getValor(), typepayment, tipoDescontoFuncionario(venda)); //Dados Computados com desconto
        //E Parcelas

        return venda;
    }

    public Long setDesconto(Double valueCompra, Integer typepayment, Integer tipoFuncionario) {

        Double discount = 0.0; //Discount
        int discountPercentage = 0; //temp

        if(tipoFuncionario != 0 && tipoFuncionario != 3) { //Casa the worker has discount permission granted
            //Desconto Padrão
            if(valueCompra == null){
                throw new ErrorMessage("Valor da venda não pode ser nulo");
            }else if(valueCompra < conditionDiscount){ //2% de desconto
                log.info("Seu desconto é de 2%");
                discount = (valueCompra * 0.02 );
            }else if(valueCompra >= conditionDiscount && valueCompra < conditionDiscountTwo) { //7% de desconto
                log.info("Seu desconto é de 7%");
                discount = (valueCompra * 0.07 );
            }else if(valueCompra >= conditionDiscountTwo && valueCompra < conditionDiscountThree) { //10% de desconto
                log.info("Seu desconto é de 2%");
                discount = (valueCompra * 0.10 );
            }else{
                discount = (valueCompra * 0.15 ); //15% para acima de R$ 10.000,00
                log.info("Seu desconto é de 15%");
            }
        }

        if(tipoFuncionario == 1){ //Case the worker has fulldiscount permission
            //Desconto Adicional
            if(typepayment.equals(TipoVenda.DINHEIRO)){ //Mais 10% de desconto
                discount += discount - (valueCompra * discountDinheiro);
                discountPercentage = 10;
            } else if(typepayment.equals(TipoVenda.CARTAO_DEBITO)) { //Mais 5% de desconto
                discount += (valueCompra * discountDebito);
                discountPercentage = 5;
            }else if(typepayment.equals(TipoVenda.CARTAO_CREDITO)) { //Mais 6% de desconto
                discount += (valueCompra * disctountCredito);
                discountPercentage = 7;
            }else if(typepayment.equals(TipoVenda.PARCELADO)) { //Mais 2% de desconto
                discount += (valueCompra * discountParcelado);
                discountPercentage = 2;
            }
            log.info("Seu método de pagamento permite mais " + discountPercentage+ "% de desconto!");
        }

        FormaPagamento fg = FormaPagamento.builder()
                .desconto(new BigDecimal(discount))
                .valorFinal(new BigDecimal(valueCompra - discount))
                .build();

        return parcelamentoService.calculaParcelas(fg);
    }

    public Integer tipoDescontoFuncionario(Venda venda) { //Return the permission of the worker to apply discount
        PermissoesAtendente ps = atendentesCRUDservice.getPermissao(venda.getAtendente());
        return switch (ps) {
            case PERMITEDESCONTOCOMPLETO -> 1;
            case PERMITEDESCONTOPARCIAL -> 2;
            case SEMDESCONTO -> 3;
        };
    }
}
