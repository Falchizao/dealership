package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.modelException.error.ErrorMessage;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@Slf4j
public record DescontoService(VendaRepository vendaRepository, AtendentesCRUDservice atendentesCRUDservice){

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
    public Venda inspecionaValorVenda(Venda venda) {

        log.info("Inspecionando valor da venda para atribuição de desconto");

        Double valorVenda = venda.getValorTotal();
        if(valorVenda < 4000){
            log.info("Valor da venda é menor que R$ 4000,00, sem desconto!");
            return venda;
        }

        if(this.tipoDescontoFuncionario(venda) != 0 && this.tipoDescontoFuncionario(venda) != 3){ //Plus Discount Worker
            BigDecimal valueDiscount = calculaDesconto(venda, venda.getFormaPagamento());
            log.info("Valor do desconto: " + valueDiscount);
        }
        return venda;
    }

    public BigDecimal calculaDesconto(Venda venda, TipoVenda type) {

        Double ret ; //Return
        Double valueVenda = venda.getValorTotal();

        //Desconto Padrão
        if(valueVenda == null){
            throw new ErrorMessage("Valor da venda não pode ser nulo");
        }else if(valueVenda < conditionDiscount){ //2% de desconto
            ret = (valueVenda * 0.02 );
            return setDesconto(venda, ret);
        }else if(valueVenda >= conditionDiscount && valueVenda < conditionDiscountTwo) { //7% de desconto
            ret = (valueVenda * 0.07 );
            return setDesconto(venda, ret);
        }else if(valueVenda >= conditionDiscountTwo && valueVenda < conditionDiscountThree) { //10% de desconto
            ret = (valueVenda * 0.10 );
            return setDesconto(venda, ret);
        }else{
            ret = (valueVenda * 0.15 ); //15% para acima de R$ 10.000,00
            return setDesconto(venda, ret);
        }
    }

    public BigDecimal setDesconto(Venda venda, Double discount) {
        Double valueTotal = venda.getValorTotal();

        int discountPercentage = 0; //temp

        if(tipoDescontoFuncionario(venda) == 1){
            //Desconto Adicional
            if(venda.getFormaPagamento().equals(TipoVenda.DINHEIRO)){ //Mais 10% de desconto
                discount += discount - (valueTotal * discountDinheiro);
                discountPercentage = 10;
            } else if(venda.getFormaPagamento().equals(TipoVenda.CARTAO_DEBITO)) { //Mais 5% de desconto
                discount += (valueTotal * discountDebito);
                discountPercentage = 5;
            }else if(venda.getFormaPagamento().equals(TipoVenda.CARTAO_CREDITO)) { //Mais 6% de desconto
                discount += (valueTotal * disctountCredito);
                discountPercentage = 7;
            }else if(venda.getFormaPagamento().equals(TipoVenda.PARCELADO)) { //Mais 2% de desconto
                discount += (valueTotal * discountParcelado);
                discountPercentage = 2;
            }
            log.info("Seu método de pagamento permite mais " + discountPercentage+ "% de desconto!");
        }

        venda.setDesconto(new BigDecimal(discount)); //Atribui o desconto
        venda.setValorFinal(new BigDecimal(venda.getValorTotal() - discount)); //Atribui o valor final

        return venda.getDesconto();
    }

    public Integer tipoDescontoFuncionario(Venda venda) {
        PermissoesAtendente ps = atendentesCRUDservice.getPermissao(venda.getAtendente());
        switch(ps){
            case PERMITEDESCONTOCOMPLETO:
                return 1;
            case PERMITEDESCONTOPARCIAL:
                return 2;
            case SEMDESCONTO:
                return 3;
            default:
                return 0;
        }
    }

}
