package com.utfpr.concessionaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
public class ConcessionariaApplication {
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
    public void greetings() {
        System.out.println("Obrigado por utilizar nossos serviços de concessionaria!");
    }

}
