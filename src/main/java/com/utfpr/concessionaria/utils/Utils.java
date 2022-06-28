package com.utfpr.concessionaria.utils;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.view.entities.Atendente;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.Cliente;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import static com.utfpr.concessionaria.enums.TipoVenda.*;

@Slf4j
@Component
public record Utils(AtendentesCRUDservice atendentesCRUDservice) {

    public Integer tipoDescontoFuncionario(Venda venda) { //Return the permission of the worker to apply discount
        PermissoesAtendente ps = atendentesCRUDservice.getPermissao(venda.getAtendente());
        return switch (ps) {
            case PERMITEDESCONTOCOMPLETO -> 1;
            case PERMITEDESCONTOPARCIAL -> 2;
            case SEMDESCONTO -> 3;
        };
    }

    public TipoVenda tipoPagamentoVendaByOrdinal(Integer ordinal) { //Return the Tipe of payment by its ordinal number
        return switch (ordinal) {
            case 0 -> DINHEIRO;
            case 1 -> PARCELADO;
            case 2 -> CARTAO_CREDITO;
            case 3 -> CARTAO_DEBITO;
            default -> null;
        };
    }

    public BigDecimal getvalorentrada(BigDecimal saleValue, Double percentage){ //Return the entry price for the sale
        return saleValue.multiply(BigDecimal.valueOf(percentage));
    }

    public void imprimeInfo(int numeroParcela, int totalParcelas, BigDecimal valorEmCentavos, Calendar data, BigDecimal montante) { //Status to String
        log.info("Informação de venda: %02d/%02d\t%td/%tm/%ty\tR$ %.2f%n", numeroParcela,
                totalParcelas, data, data, data, valorEmCentavos);
        log.info("%nTotal do acerto: R${}", montante);
    }

    public BigDecimal calculaValorParcela(BigDecimal juros, int numeroParcelas, BigDecimal valorParcelado) {
        BigDecimal potencia = juros.add(BigDecimal.ONE).pow(numeroParcelas);
        BigDecimal denominador = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(potencia, 20, RoundingMode.HALF_EVEN));
        return valorParcelado.multiply(juros).divide(denominador, 2, RoundingMode.HALF_EVEN);
    }

    public Carro mapCarDtoToCar(CarroDTO dto){
        var car = Carro.builder()
                .modelo(dto.getModelo())
                .marca(dto.getMarca())
                .cor(dto.getCor())
                .ano(dto.getAno())
                .placa(dto.getPlaca())
                .chassi(dto.getChassi())
                .valor(dto.getValor())
                .build();
        car.setId(dto.getId());

        return car;
    }

    public CarroDTO mapCarToCarDTO(Carro carro){
        var dto = CarroDTO.builder()
                .modelo(carro.getModelo())
                .marca(carro.getMarca())
                .cor(carro.getCor())
                .ano(carro.getAno())
                .placa(carro.getPlaca())
                .chassi(carro.getChassi())
                .valor(carro.getValor())
                .build();
        dto.setId(carro.getId());

        return dto;
    }

    public VendaDTO mapVendaToVendaDTO(Venda venda){
        return VendaDTO.builder()
                .emailCliente(venda.getEmailCliente())
                .idPagamento(venda.getIdPagamento())
                .atendente(venda.getAtendente())
                .data_venda(venda.getData_venda())
                .idCarro(venda.getCarro())
                .idCliente(venda.getIdCliente())
                .build();
    }

    public AtendenteDTO mapAtendenteToAtendenteDTO(Atendente atendente){
        return AtendenteDTO.builder()
                .nomeAtendente(atendente.getNomeAtendente())
                .build();
    }

    public ClienteDTO mapClienteToClienteDTO(Cliente cliente){
        return ClienteDTO.builder()
                .emailCliente(cliente.getEmailCliente())
                .cpfCliente(cliente.getCpfCliente())
                .nomeCliente(cliente.getNomeCliente())
                .enderecoCliente(cliente.getEnderecoCliente())
                .build();
    }




    public void cleanRepository(IRepository repository){
        repository.deleteAll();
    }

    public void setLog(BigDecimal valorBase,BigDecimal entrada, BigDecimal valueParcela, BigDecimal total){
        log.info("Valor do veiculo: {}", valorBase);
        log.info("Valor da entrada: {}", entrada);
        log.info("Valor parcelas {}", valueParcela);
        log.info("Total a ser pago nas parcelas {}", total);
    }

}
