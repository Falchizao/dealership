package com.utfpr.concessionaria;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.Catalogo;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class ConcessionariaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConcessionariaApplication.class, args);
    }

    @Autowired
    private CarrosCRUDservice carService;

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasename("classpath:messages");
        bundle.setDefaultEncoding("UTF-8");
        bundle.setCacheSeconds(1);
        return bundle;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startAndGreetings() throws ParseException {

        SimpleDateFormat genericDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        carService.add(
                CarroDTO.builder()
                        .modelo("Sedan")
                        .marca("Ford")
                        .cor("Cinza")
                        .ano(genericDateFormatter.parse("01/01/2018"))
                        .placa("ABC1234")
                        .chassi("123456789012345")
                        .valor(30000.00)
                        .build(), 1);

        carService.add(
                CarroDTO.builder()
                        .modelo("Eletrico")
                        .marca("Tesla")
                        .cor("Preto")
                        .ano(genericDateFormatter.parse("01/01/2020"))
                        .placa("ABC12345")
                        .chassi("123456789012346")
                        .valor(40000.00)
                        .build(), 1);


        carService.add(
                CarroDTO.builder()
                        .modelo("Conversivel")
                        .marca("Ferrari")
                        .cor("Vermelho")
                        .ano(genericDateFormatter.parse("01/01/2014"))
                        .placa("ABC1236")
                        .chassi("123456789012347")
                        .valor(50000.00)
                        .build(), 2);

        carService.add(
                CarroDTO.builder()
                        .modelo("Fusca")
                        .marca("Volkswagen")
                        .cor("Azul")
                        .ano(genericDateFormatter.parse("01/01/1960"))
                        .placa("ABC1237")
                        .chassi("123456789012348")
                        .valor(60000.00)
                        .build(), 2);

        carService.add(
                CarroDTO.builder()
                        .modelo("Gol Bolinha")
                        .marca("Volkswagen")
                        .cor("Verde")
                        .ano(genericDateFormatter.parse("01/01/1990"))
                        .placa("ABC1264")
                        .chassi("123442789012348")
                        .valor(70000.00)
                        .build(), 1);

        System.out.println("Obrigado por utilizar nossos serviços de concessionaria!");
    }

}
