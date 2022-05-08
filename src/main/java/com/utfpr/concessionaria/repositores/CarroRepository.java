package com.utfpr.concessionaria.repositores;


import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {

    Carro findByplaca(String placa);

    Carro findBychassi(String chassi);
}
