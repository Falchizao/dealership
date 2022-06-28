package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.Atendente;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendenteRepository extends IRepository<Atendente> {

    Atendente findBynomeAtendente(String nomeAtendente); //Recuperação de conta

}
