package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.view.entities.Atendente;
import com.utfpr.concessionaria.view.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findBynomeCliente(String nomeCliente);

    Cliente findBycpfCliente(String cpfCliente);
}
