package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.utils.Utils;
import com.utfpr.concessionaria.view.entities.FormaPagamento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

@Service
@Slf4j
public class ParcelamentoService{

    @Value("${max.parcelas.parcelado}")
    Integer MAX_PARCELAS_PARCELADO;

    @Value("${max.parcelas.debito}")
    Integer MAX_PARCELAS_DEBITO;

    @Value("${max.parcelas.credito}")
    Integer MAX_PARCELAS_CREDITO;

    @Value("${entrada.parcelado}")
    Double ENTRADA_PARCELADO;

    @Value("${entrada.debito}")
    Double ENTRADA_DEBITO;

    @Value("${entrada.credito}")
    Double ENTRADA_CREDITO;

    @Value("${taxa.juros.parcelado}")
    Double TAXA_JUROS_PARCELADO;

    @Value("${taxa.juros.debito}")
    Double TAXA_JUROS_DEBITO;

    @Value("${taxa.juros.credito}")
    Double TAXA_JUROS_CREDITO;

    private final Utils utils;

    public ParcelamentoService(Utils utils){
        this.utils = utils;
    }

    public void manipulaParcelas(FormaPagamento formaPagamento){
        log.info("Executando métodos para calculo de parcelas");
        attTribution(formaPagamento);
    }

    private void attTribution(FormaPagamento formaPagamento){

        switch (formaPagamento.getDescricao()){ //Gerar valor das parcelas com juros e atribuir para o objeto FormaPagamento
            case DINHEIRO:
                log.info("Sem parcelas!!!");
                break;
            case PARCELADO:
                formaPagamento.setParcelas(MAX_PARCELAS_PARCELADO);
                proceedTransaction(formaPagamento.getValorTotal(), formaPagamento, ENTRADA_PARCELADO, MAX_PARCELAS_PARCELADO, TAXA_JUROS_PARCELADO);
                break;
            case CARTAO_CREDITO:
                formaPagamento.setParcelas(MAX_PARCELAS_CREDITO);
                proceedTransaction(formaPagamento.getValorTotal(), formaPagamento, ENTRADA_CREDITO, MAX_PARCELAS_CREDITO, TAXA_JUROS_CREDITO);
                break;
            case CARTAO_DEBITO:
                formaPagamento.setParcelas(MAX_PARCELAS_DEBITO);
                proceedTransaction(formaPagamento.getValorTotal(), formaPagamento, ENTRADA_DEBITO, MAX_PARCELAS_DEBITO, TAXA_JUROS_DEBITO);
                break;

            default: //Do nothing
        }
    }

    private void proceedTransaction(BigDecimal valorTotal, FormaPagamento formaPagamento, Double entrada, Integer parcelas, Double juros){
        BigDecimal valorEntrada = utils.getvalorentrada(formaPagamento.getValorTotal() ,entrada);
        calcularPrecoComJuros(valorTotal, valorEntrada, parcelas , juros, formaPagamento);
    }

    private void calcularPrecoComJuros(BigDecimal valorBase, BigDecimal entrada, int numeroParcelas, Double taxaJuros, FormaPagamento formaPagamento){
        calcularJurosParcela(valorBase, taxaJuros, entrada, numeroParcelas, formaPagamento); //Aqui já entra em matemática financeira
        addMontante(formaPagamento, numeroParcelas, valorBase, taxaJuros);
    }

    private void addMontante(FormaPagamento formaPagamento, int numeroParcelas, BigDecimal valorBase, Double taxaJuros){
        BigDecimal montante = formaPagamento.getJuros().add(valorBase);
        validateTaxValue(numeroParcelas, taxaJuros);
        utils.imprimeInfo(1, numeroParcelas, formaPagamento.getJuros().multiply(new BigDecimal(100)), Calendar.getInstance(), montante);
        formaPagamento.setValorFinal(montante); //Atribui o valor final ao objeto FormaPagamento

        calculaTotalAPagar(formaPagamento);
    }

    private void validateTaxValue(int numeroParcelas, Double taxaJuros){ //Validações
        if (numeroParcelas <= 0) throw new IllegalArgumentException();
        if (taxaJuros.compareTo(0.0) < 0) throw new IllegalArgumentException();
    }

    private void calcularJurosParcela(BigDecimal valorBase, Double taxaJuros, BigDecimal entrada, Integer parcelas, FormaPagamento formaPagamento) {
        //Formulas:
        //C x (1 + I ÷100) T = JUROS Compostos
        //C x I ÷ 100 x T = JUROS Simples

        BigDecimal taxa = new BigDecimal(taxaJuros);
        BigDecimal diferenca = calculaDiferencial(valorBase, entrada);
        BigDecimal juros = calculaJuros(diferenca, taxa);

        //(1 + i)^n - 1
        taxa = addJuros(juros, diferenca);

        //(1 + i)^n * i
        //BigDecimal t2 = BigDecimal.ONE.add(juros).pow(parcelas).multiply(juros).setScale(2);

        //(1 + i)^n - 1 / (1 + i)^n * i -> t1 / t2
        BigDecimal valueParcela = taxa.divide(new BigDecimal(parcelas), 2, RoundingMode.HALF_EVEN);
        BigDecimal total = taxa.add(entrada);

        atribuicaoFormaPag(formaPagamento, juros, valueParcela, entrada);

        utils.setLog(valorBase, entrada, valueParcela, total);
    }

    private void atribuicaoFormaPag(FormaPagamento formaPagamento, BigDecimal juros, BigDecimal valueParcela, BigDecimal entrada){
        formaPagamento.setJuros(juros);
        formaPagamento.setValor_parcelas(valueParcela);
        formaPagamento.setEntradaVenda(entrada);
    }

    private void calculaTotalAPagar(FormaPagamento formaPagamento){
        formaPagamento.setTotalAPagar(formaPagamento.getValorFinal().subtract(formaPagamento.getDesconto()));
    }

    private BigDecimal calculaDiferencial(BigDecimal valorBase ,BigDecimal entrada){
        return valorBase.subtract(entrada);
    }

    private BigDecimal calculaJuros(BigDecimal diferenca,BigDecimal taxa){
        return diferenca.multiply(taxa).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal addJuros(BigDecimal juros, BigDecimal diferenca){
        return juros.add(diferenca);
    }
}

