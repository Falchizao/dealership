package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.Atendente;
import com.utfpr.concessionaria.view.entities.Venda;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface VendaRepository extends IRepository<Venda> {

    @Transactional
    @Modifying
    @Query("delete from tb_venda where atendente = ?1")
    void deleteVendaByAtendente(Atendente atendente); //Soon
}
