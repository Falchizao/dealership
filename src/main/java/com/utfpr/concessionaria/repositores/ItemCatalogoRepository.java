package com.utfpr.concessionaria.repositores;

import com.utfpr.concessionaria.generic.IRepository;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemCatalogoRepository extends IRepository<ItemCatalogo> {

    ItemCatalogo findByCarro(Carro carro);

}
