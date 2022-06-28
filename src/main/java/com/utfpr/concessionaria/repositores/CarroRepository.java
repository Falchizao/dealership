package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CarroRepository extends IRepository<Carro>{

    @Transactional
    @Modifying
    @Query("UPDATE tb_carro set itemCatalogo=:pkItemCatalog where id=:pkCarro")
    void updateCarro(long pkCarro, ItemCatalogo pkItemCatalog);
}
