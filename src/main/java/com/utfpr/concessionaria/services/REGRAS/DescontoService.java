package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.modelException.error.ErrorMessage;
import com.utfpr.concessionaria.repositores.PaymentRepository;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.utils.Utils;
import com.utfpr.concessionaria.view.entities.FormaPagamento;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@Slf4j
public class DescontoService{

    @Value("${primeira.condicao.desconto}")
    Integer PORCENTAGEM_BAIXA;

    @Value("${segunda.condicao.desconto}")
    Integer PORCENTAGEM_MEDIANA;

    @Value("${terceira.condicao.desconto}")
    Integer PORCENTAGEM_ALTA;

    @Value("${desconto.parcelado}")
    Double DESCONTO_PARCELADO;

    @Value("${desconto.debito}")
    Double DESCONTO_DEBITO;

    @Value("${desconto.credito}")
    Double DESCONTO_CREDITO;

    @Value("${desconto.DINHEIRO}")
    Double DESCONTO_DINHEIRO;

    private final ParcelamentoService parcelamentoService;
    private final Utils utils;
    private final PaymentRepository formaPagamentoRepository;
    private final CarrosCRUDservice carrosCRUDservice;

    @Autowired
    public DescontoService(CarrosCRUDservice carrosCRUDservice, ParcelamentoService parcelamentoService, Utils utils, PaymentRepository formaPagamentoRepository){
        this.parcelamentoService = parcelamentoService;
        this.utils = utils;
        this.formaPagamentoRepository = formaPagamentoRepository;
        this.carrosCRUDservice = carrosCRUDservice;
    }

    public Venda manipulaValorDescontoVenda(Venda venda, Integer typepayment) {
        FormaPagamento idPagamento = calculaDescontoPorValorVenda(typepayment, venda);
        venda.setIdPagamento(idPagamento.getId()); //Relate Tables
        return venda;
    }

    private FormaPagamento calculaDescontoPorValorVenda(Integer typepayment, Venda venda) {
        return setFormaPagamento(utils.tipoDescontoFuncionario(venda),carrosCRUDservice.getCarValue(venda.getCarro()), typepayment);
    }

    private FormaPagamento setFormaPagamento(Integer tipoFuncionario, Double valueCompra, Integer typepayment){
        return manipulacaoParcelasPagamento(tipoFuncionario, typepayment, paymentBuilder(tipoFuncionario, valueCompra));
    }

    private FormaPagamento manipulacaoParcelasPagamento(Integer tipoFuncionario, Integer typepayment, FormaPagamento paymentInfo){
        FormaPagamento idPagamento = formaPagamentoRepository.save(calculaDescontoExtraPorNivelUser(paymentInfo, tipoFuncionario, typepayment)); //Registrando a forma de pagamento para retornar um id e ligar com a venda

        return relateReturn(idPagamento, typepayment);
    }

    private FormaPagamento paymentBuilder(Integer tipoFuncionario, Double valueCompra){
        return FormaPagamento.builder()
                .desconto(new BigDecimal(getDiscount(tipoFuncionario, valueCompra)))
                .valorTotal(new BigDecimal(valueCompra - getDiscount(tipoFuncionario, valueCompra)))
                .build();
    }

    private FormaPagamento relateReturn(FormaPagamento formaPagamento, Integer typePayment){
        formaPagamento.setTipoVenda(utils.tipoPagamentoVendaByOrdinal(typePayment));
        formaPagamento.setDescricao(utils.tipoPagamentoVendaByOrdinal(typePayment));

        parcelamentoService.manipulaParcelas(formaPagamento); //Chamada da rotina de calculo de parcelas/c juros, atribuíndo para a entidade infoVenda

        return formaPagamento;
    }

    private FormaPagamento calculaDescontoExtraPorNivelUser(FormaPagamento paymentInfo, Integer tipoFuncionario, Integer typepayment) {

        Double valueCompra = paymentInfo.getValorTotal().doubleValue(); //Valor do carro

        if(tipoFuncionario == 1){ //Case the worker has full-discount permission
            switch(typepayment) {
                case 0 -> {//Mais 10% de desconto
                    log.info("Seu método de pagamento permite mais {}% de desconto!", 10);
                    paymentInfo.setDesconto(new BigDecimal(valueCompra * DESCONTO_DINHEIRO));
                    break;
                }
                case 1 -> {//Mais 2% de desconto
                    log.info("Seu método de pagamento permite mais {}% de desconto!", 2);
                    paymentInfo.setDesconto(new BigDecimal(valueCompra * DESCONTO_PARCELADO));
                    break;
                }
                case 2 -> {//Mais 5% de desconto
                    log.info("Seu método de pagamento permite mais {}% de desconto!", 5);
                    paymentInfo.setDesconto(new BigDecimal(valueCompra * DESCONTO_DEBITO));
                    break;
                }
                case 3 -> {//Mais 6% de desconto
                    log.info("Seu método de pagamento permite mais {}% de desconto!", 6);
                    paymentInfo.setDesconto(new BigDecimal(valueCompra * DESCONTO_CREDITO));
                    break;
                }
            }
        }

        return paymentInfo;
    }

    private Double getDiscount(Integer tipoFuncionario, Double valueCompra){
        if(tipoFuncionario != 0 && tipoFuncionario != 3) { //Case the worker has discount permission granted
            if(valueCompra == null){ //Desconto Padrão
                throw new ErrorMessage("Valor da venda não pode ser nulo!!! Favor verificar");
            }else if(valueCompra < PORCENTAGEM_BAIXA){ //2% de desconto
                log.info("Seu desconto é de {}%", 2);
                return (valueCompra * 0.02 );
            }else if(valueCompra >= PORCENTAGEM_BAIXA && valueCompra < PORCENTAGEM_MEDIANA) { //7% de desconto
                log.info("Seu desconto é de {}%", 7);
                return (valueCompra * 0.07 );
            }else if(valueCompra >= PORCENTAGEM_MEDIANA && valueCompra < PORCENTAGEM_ALTA) { //10% de desconto
                log.info("Seu desconto é de {}%",10);
                return (valueCompra * 0.10 );
            }else{ //15% para acima de R$ 10.000,00
                log.info("Seu desconto é de {}%",15);
                return (valueCompra * 0.15 );
            }
        }

        return 0.0;
    }
}
