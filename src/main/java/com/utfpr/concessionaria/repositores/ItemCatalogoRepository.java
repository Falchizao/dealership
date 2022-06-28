package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.Catalogo;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ItemCatalogoRepository extends IRepository<ItemCatalogo> {

    @Transactional
    @Query(value = "SELECT * FROM item_catalogo WHERE carro_id = ?1", nativeQuery = true)
    Optional<ItemCatalogo> finderByCar(long idCarro);

    @Transactional
    @Modifying
    @Query("UPDATE item_catalogo set catalogo=:catalogo where id=:pkItem")
    void updateCatalogo(long pkItem, Catalogo catalogo);

    @Transactional
    @Modifying
    @Query("UPDATE item_catalogo set quantity=:quantity where id=:pkItem")
    void updateUnit(Integer quantity, long pkItem);

}
