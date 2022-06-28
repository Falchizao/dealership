package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.repositores.CarroRepository;
import com.utfpr.concessionaria.repositores.ItemCatalogoRepository;
import com.utfpr.concessionaria.services.REGRAS.CatalogoService;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public record ItemCatalogoCRUDservice(ItemCatalogoRepository itemCatalogoRepository, CatalogoService catalogoService, CarroRepository carroRepository) {

    public void addNewCarToCatalog(Carro newCar, Integer quantity){
        ItemCatalogo itemCatalogo = itemCatalogoRepository.saveAndFlush(
                ItemCatalogo.builder()
                        .carro(newCar)
                        .quantity(quantity)
                        .build());
        log.info("New Car added to the stock with {} units!", quantity);

        persistEntity(newCar.getId(), itemCatalogo);
    }

    private void removeUnitFromStock(List<ItemCatalogo> cars){ //Soon
        log.info("Removing units from stock!");
        cars.stream()
                .map(
                carsAtt -> ItemCatalogo.builder()
                        .carro(carsAtt.getCarro())
                        .quantity(carsAtt.getQuantity())
                        .catalogo(carsAtt.getCatalogo())
                        .build())
                .forEach(itemCatalogoRepository::save);
    }

    private void persistEntity(Long idCarro, ItemCatalogo itemCatalog){
        log.info("Updating relation");
        carroRepository.updateCarro(idCarro, itemCatalog);

        updateCatalog(itemCatalog);
    }

    private void updateCatalog(ItemCatalogo item){
        catalogoService.attCarOptions(item);
    }
}
