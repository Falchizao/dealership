package com.utfpr.concessionaria.services;

import com.utfpr.concessionaria.models.Atendente;
import com.utfpr.concessionaria.models.Carro;
import com.utfpr.concessionaria.models.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j  // log4j
public class BuyCarService {

    public Date venda;
    public Cliente cliente;
    public Carro carro;
    public Atendente atendente;
    //log.info("Venda realizada com sucesso!", item.getdescricao());



}
