package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.repositores.VendaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record MultiplasFormasPagService(VendaRepository vendaRepository, VendaService vendaService) {

//    public void calcularMultiplasFormasPag(Long idVenda){
//        log.info("Inserindo multiplas formas de pagamento");
//        vendaService.inserirMultiplasFormasPag(idVenda);
//    }
}
