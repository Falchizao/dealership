package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.enums.TipoDesconto;
import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.modelException.error.ErrorMessage;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public record DescontoService(VendaRepository vendaRepository) {

    //Aqui vão as regras de desconto da minha concessionaria em relação a descontos de venda

    public Venda inspecionaValorVenda(Venda venda) {
        log.info("Inspecionando valor da venda para atribuição de desconto");
        Double valorVenda = venda.getValorTotal();

        if(valorVenda < 4000) return venda; //Sem desconto pra quem não tem dinheiro, vai trabalhar carai

        BigDecimal valueDiscount = calculaDesconto(venda, venda.getFormaPagamento());
        log.info("Valor do desconto: " + valueDiscount);

        return venda;
    }

    public BigDecimal calculaDesconto(Venda venda, TipoVenda type) {

        Double ret ; //Return
        Double valueVenda = venda.getValorTotal();

        //Desconto Padrão
        if(valueVenda == null){
            throw new ErrorMessage("Valor da venda não pode ser nulo");
        }else if(valueVenda < 5000){ //2% de desconto
            ret = (valueVenda * 0.02 );
            return setDesconto(venda, ret);
        }else if(valueVenda >= 5000 && valueVenda < 10000) { //7% de desconto
            ret = (valueVenda * 0.07 );
            return setDesconto(venda, ret);
        }else if(valueVenda >= 10000) { //10% de desconto
            ret = (valueVenda * 0.10 );
            return setDesconto(venda, ret);
        }else{
            ret = (valueVenda * 0.15 );
            return setDesconto(venda, ret);
        }
    }

    public BigDecimal setDesconto(Venda venda, Double discount) {
        Double valueTotal = venda.getValorTotal();

        int discountPercentage = 0; //temp


        //Desconto Adicional
        if(venda.getFormaPagamento().equals(TipoVenda.DINHEIRO)){ //Mais 10% de desconto
            discount += discount - (valueTotal * 0.10 );
            discountPercentage = 10;

        } else if(venda.getFormaPagamento().equals(TipoVenda.CARTAO_DEBITO)) { //Mais 5% de desconto
            discount += (valueTotal * 0.05);
            discountPercentage = 5;
        }else if(venda.getFormaPagamento().equals(TipoVenda.CARTAO_CREDITO)) { //Mais 6% de desconto
            discount += (valueTotal * 0.06 );
            discountPercentage = 6;
        }else if(venda.getFormaPagamento().equals(TipoVenda.PARCELADO)) { //Mais 2% de desconto
            discount += (valueTotal * 0.02 );
            discountPercentage = 2;
        }

        log.info("Seu método de pagamento permite mais " + discountPercentage+ "% de desconto!");

        venda.setDesconto(new BigDecimal(discount)); //Atribui o desconto
        venda.setValorFinal(new BigDecimal(venda.getValorTotal() - discount)); //Atribui o valor final

        return venda.getDesconto();
    }


}
