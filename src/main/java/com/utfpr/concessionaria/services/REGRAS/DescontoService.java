package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.modelException.error.ErrorMessage;
import com.utfpr.concessionaria.repositores.FormaPagamentoRepository;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.view.entities.FormaPagamento;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;
import static com.utfpr.concessionaria.Constants.*;

@Service
@Slf4j
public record DescontoService(VendaRepository vendaRepository, AtendentesCRUDservice atendentesCRUDservice,ParcelamentoService parcelamentoService,
                              CarrosCRUDservice carrosCRUDservice, Utils utils, FormaPagamentoRepository formaPagamentoRepository){

    //Aqui vão as regras de desconto da minha concessionaria em relação a descontos de venda
    public Venda manipulaValorDescontoVenda(Venda venda, Integer typepayment) {
        log.info("Inspecionando valor da venda para atribuição de desconto!");

        Optional<CarroDTO> car = carrosCRUDservice.getById(venda.getCarro());

        Double carTotalValue = car.get().getValor(); //Valor Total do carro
        Integer funcaoFuncionario = utils.tipoDescontoFuncionario(venda); //Woker role

        //Dados Computados com Desconto e Parcelas
        FormaPagamento idPagamento = calculaDescontoPorValorVenda(carTotalValue, typepayment, funcaoFuncionario);
        idPagamento.setTipoVenda(utils.tipoPagamentoVendaByOrdinal(typepayment));
        idPagamento.setDescricao(utils.tipoPagamentoVendaByOrdinal(typepayment));

        //Registrando a forma de pagamento para retornar um id e ligar com a venda
        idPagamento = formaPagamentoRepository.save(idPagamento);

        //Chamada da rotina de calculo de parcelas/c juros, atribuíndo para a entidade infoVenda
        parcelamentoService.manipulaParcelas(idPagamento);

        venda.setIdPagamento(idPagamento.getId()); //Relaciona a tabela da venda com a tabela de informações de pagamento

        return venda;
    }

    public FormaPagamento calculaDescontoPorValorVenda(Double valueCompra, Integer typepayment, Integer tipoFuncionario) {

        Double discount = 0.0; //Discount

        if(tipoFuncionario != 0 && tipoFuncionario != 3) { //Casa the worker has discount permission granted
            //Desconto Padrão
            if(valueCompra == null){
                throw new ErrorMessage("Valor da venda não pode ser nulo!!! Favor verificar");
            }else if(valueCompra < PRIMEIRA_CONDICAO_DESCONTO){ //2% de desconto
                log.info("Seu desconto é de {}%", 2);
                discount = (valueCompra * 0.02 );
            }else if(valueCompra >= PRIMEIRA_CONDICAO_DESCONTO && valueCompra < SEGUNDA_CONDICAO_DESCONTO) { //7% de desconto
                log.info("Seu desconto é de {}%", 7);
                discount = (valueCompra * 0.07 );
            }else if(valueCompra >= SEGUNDA_CONDICAO_DESCONTO && valueCompra < TERCEIRA_CONDICAO_DESCONTO) { //10% de desconto
                log.info("Seu desconto é de {}%",10);
                discount = (valueCompra * 0.10 );
            }else{
                discount = (valueCompra * 0.15 ); //15% para acima de R$ 10.000,00
                log.info("Seu desconto é de {}%",15);
            }
        }

        FormaPagamento paymentInfo = FormaPagamento.builder()
                .desconto(new BigDecimal(discount))
                .valorTotal(new BigDecimal(valueCompra - discount))
                .build();

        return calculaDescontoExtraPorNivelUser(paymentInfo, tipoFuncionario, typepayment);
    }

    public FormaPagamento calculaDescontoExtraPorNivelUser(FormaPagamento paymentInfo, Integer tipoFuncionario, Integer typepayment) {
        Integer discountPercentage = 0; //temp
        Double discount = 0.0; //temp
        Double valueCompra = paymentInfo.getValorTotal().doubleValue(); //Valor do carro

        if(tipoFuncionario == 1){ //Case the worker has fulldiscount permission

            //Desconto Adicional
            switch(typepayment) {
                case 0 -> {//Mais 10% de desconto
                    discount += discount - (valueCompra * DESCONTO_DINHEIRO);
                    discountPercentage = 10;
                    paymentInfo.setDesconto(new BigDecimal(discount));
                    break;
                }
                case 1 -> {//Mais 2% de desconto
                    discount += (valueCompra * DESCONTO_PARCELADO);
                    discountPercentage = 2;
                    paymentInfo.setDesconto(new BigDecimal(discount));
                    break;
                }
                case 2 -> {//Mais 5% de desconto
                    discount += (valueCompra * DESCONTO_DEBITO);
                    discountPercentage = 5;
                    paymentInfo.setDesconto(new BigDecimal(discount));
                    break;
                }
                case 3 -> {//Mais 6% de desconto
                    discount += (valueCompra * DESCONTO_CREDITO);
                    discountPercentage = 6;
                    paymentInfo.setDesconto(new BigDecimal(discount));

                    break;
                }
            }
        }
        log.info("Seu método de pagamento permite mais {}% de desconto!",discountPercentage);

        return paymentInfo;
    }
}
