package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.Atendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtendenteRepository extends IRepository<Atendente> {

    Atendente findBynomeAtendente(String nomeAtendente);

}
