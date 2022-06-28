package com.utfpr.concessionaria;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootApplication
@Slf4j
@PropertySource("classpath:application.properties")
public class ConcessionariaApplication {

    @Autowired
    private CarrosCRUDservice carService;

    public static void main(String[] args) {
        SpringApplication.run(ConcessionariaApplication.class, args);
    }

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

        //Formatter for CarDate
        SimpleDateFormat genericDateFormatter = new SimpleDateFormat("dd/MM/yyyy");

        //1st Car Routine
        log.info("Attempting to add the car");
        //Create Object
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

        //2nd Car Routine
        log.info("Attempting to add the car");
        //Create Object
        carService.add(
                CarroDTO.builder()
                        .modelo("Eletrico")
                        .marca("Tesla")
                        .cor("Preto")
                        .ano(genericDateFormatter.parse("01/01/2020"))
                        .placa("ABC1434")
                        .chassi("123456784022345")
                        .valor(40000.00)
                        .build(), 1);

        //3rd Car Routine
        log.info("Attempting to add the car");
        //Create Object
        carService.add(
                CarroDTO.builder()
                        .modelo("Conversivel")
                        .marca("Ferrari")
                        .cor("Vermelho")
                        .ano(genericDateFormatter.parse("01/01/2014"))
                        .placa("ABC1236")
                        .chassi("123445689012348")
                        .valor(50000.00)
                        .build(), 2);

        //4th car Routine
        log.info("Attempting to add the car");
        //Create Object
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

        //5th car Routine
        log.info("Attempting to add the car");
        //Create Object
        carService.add(
                CarroDTO.builder()
                        .modelo("Gol Bolinha")
                        .marca("Volkswagen")
                        .cor("Verde")
                        .ano(genericDateFormatter.parse("01/01/1990"))
                        .placa("ABC1264")
                        .chassi("123442789012348")
                        .valor(70000.00)
                        .build(), 3);

        //Finish
        log.info("Obrigado por utilizar nossos serviços de concessionaria!");
    }

}
