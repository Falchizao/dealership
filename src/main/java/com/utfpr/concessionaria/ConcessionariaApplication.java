package com.utfpr.concessionaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ConcessionariaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConcessionariaApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void greetings() {
        System.out.println("Obrigado por utilizar nossos serviços de concessionaria!");

    }

}
