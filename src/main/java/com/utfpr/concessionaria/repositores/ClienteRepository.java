package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.Cliente;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends IRepository<Cliente> {

    Cliente findBynomeCliente(String nomeCliente); //Recuperação de conta

    Cliente findBycpfCliente(String cpfCliente);   //Recuperação de conta
}
