package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.repositores.CatalogoRepository;
import com.utfpr.concessionaria.repositores.ItemCatalogoRepository;
import com.utfpr.concessionaria.utils.Utils;
import com.utfpr.concessionaria.view.entities.Catalogo;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record CatalogoService(CatalogoRepository catalogoRepository, ItemCatalogoRepository itemCatalogoRepository, Utils utils) {

    public void attCarOptions(ItemCatalogo itemCatalogo){
        log.info("Creating a new fresh catalog");
        //Save a new one
        createNewCatalog(itemCatalogo);
    }

    private void createNewCatalog(ItemCatalogo item){
        log.info("Adding Itens");
        Catalogo catalogo = catalogoRepository.saveAndFlush(
                Catalogo.builder()
                        .itemCatalogo(itemCatalogoRepository.findAll())
                        .build());

        persistEntity(item.getId(), catalogo);
        log.info("Catalog created!");
    }

    private void persistEntity(Long idItemCatalog, Catalogo catalogo){
        log.info("Updating relation");
        itemCatalogoRepository.updateCatalogo(idItemCatalog, catalogo);
    }
}



