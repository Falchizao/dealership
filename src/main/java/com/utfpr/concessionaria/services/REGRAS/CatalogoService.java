package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.repositores.CatalogoRepository;
import com.utfpr.concessionaria.repositores.ItemCatalogoRepository;
import com.utfpr.concessionaria.view.entities.Catalogo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record CatalogoService(CatalogoRepository catalogoRepository, ItemCatalogoRepository itemCatalogoRepository) {

    public void attCarOptions(){
        catalogoRepository.save(
                Catalogo.builder()
                        .itemCatalogo(itemCatalogoRepository.findAll())
                        .build());

        log.info("Database Att!");
    }
}



