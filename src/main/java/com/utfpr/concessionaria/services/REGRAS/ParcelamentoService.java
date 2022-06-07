package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.repositores.FormaPagamentoRepository;
import com.utfpr.concessionaria.repositores.PaymentRepository;
import com.utfpr.concessionaria.view.entities.FormaPagamento;
import lombok.extern.slf4j.Slf4j;
import static com.utfpr.concessionaria.Constants.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

@Service
@Slf4j
public record ParcelamentoService(PaymentRepository paymentRepository, FormaPagamentoRepository formaPagamentorepository,
                                  Utils utils){

    public void manipulaParcelas(FormaPagamento formaPagamento){
        log.info("Executando métodos para calculo de parcelas");

        BigDecimal valorTotal = formaPagamento.getValorTotal();
        BigDecimal valorEntrada;//Temp

        //Gerar valor das parcelas com juros e atribuir para o objeto FormaPagamento
        switch (formaPagamento.getDescricao()){
            case DINHEIRO:
                log.info("Sem parcelas!!!");
                break;
            case PARCELADO:
                formaPagamento.setParcelas(MAX_PARCELAS_PARCELADO);
                valorEntrada = utils.getvalorentrada(valorTotal ,ENTRADA_PARCELADO);
                calcularPrecoComJuros(valorTotal,valorEntrada,MAX_PARCELAS_PARCELADO ,TAXA_JUROS_PARCELADO, formaPagamento);
                break;
            case CARTAO_CREDITO:
                formaPagamento.setParcelas(MAX_PARCELAS_CREDITO);
                valorEntrada = utils.getvalorentrada(valorTotal ,ENTRADA_CREDITO);
                calcularPrecoComJuros(valorTotal,valorEntrada,MAX_PARCELAS_CREDITO ,TAXA_JUROS_CREDITO, formaPagamento);
                break;
            case CARTAO_DEBITO:
                formaPagamento.setParcelas(MAX_PARCELAS_DEBITO);
                valorEntrada = utils.getvalorentrada(valorTotal ,ENTRADA_DEBITO);
                calcularPrecoComJuros(valorTotal,valorEntrada,MAX_PARCELAS_DEBITO , TAXA_JUROS_DEBITO, formaPagamento);
                break;

            default: //Do nothing
        }
    }

    //Aqui já entra em matemática financeira
    public void calcularPrecoComJuros(BigDecimal valorBase, BigDecimal entrada, int numeroParcelas, Double taxaJuros, FormaPagamento formaPagamento){

        calcularJurosParcela(valorBase, taxaJuros, entrada, numeroParcelas, formaPagamento);

        BigDecimal montante = formaPagamento.getJuros().add(valorBase);

        utils.imprimeInfo(1, numeroParcelas, formaPagamento.getJuros().multiply(new BigDecimal(100)), Calendar.getInstance());

        log.info("%nTotal do acerto: R${}", montante);

        //Validações
        if (numeroParcelas <= 0) throw new IllegalArgumentException();
        if (taxaJuros.compareTo(0.0) < 0) throw new IllegalArgumentException();

        formaPagamento.setValorFinal(montante); //Atribui o valor final ao objeto FormaPagamento

    }

    private void calcularJurosParcela(BigDecimal valorBase, Double taxaJuros, BigDecimal entrada, Integer parcelas, FormaPagamento formaPagamento) {

//        C x (1 + I ÷100) T = JUROS Compostos
//        C x I ÷ 100 x T = JUROS Simples

        BigDecimal taxa = new BigDecimal(taxaJuros);

        //calcula diferenca entre valor do veiculo e a entrada
        BigDecimal diferenca = valorBase.subtract(entrada);

        BigDecimal juros = diferenca.multiply(taxa).setScale(2, RoundingMode.HALF_EVEN);

        formaPagamento.setJuros(juros);
        //(1 + i)^n - 1
        taxa = juros.add(diferenca);
        log.info("Valor da taxa: {}", taxa);

        //(1 + i)^n * i
//        BigDecimal t2 = BigDecimal.ONE.add(juros).pow(parcelas).multiply(juros).setScale(2);

        //(1 + i)^n - 1 / (1 + i)^n * i -> t1 / t2

        BigDecimal valueParcela = taxa.divide(new BigDecimal(parcelas), 2, RoundingMode.HALF_EVEN);
        BigDecimal total = taxa.add(entrada);

        formaPagamento.setValor_parcelas(valueParcela);

        formaPagamento.setEntradaVenda(entrada);

        log.info("Valor do veiculo: {}", valorBase);
        log.info("Valor da entrada: {}", entrada);

        log.info("Valor parcelas {}", valueParcela);
        log.info("Total a ser pago nas parcelas {}", total);
    }
}

