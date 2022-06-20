package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.repositores.CarUnitsRepository;
import com.utfpr.concessionaria.repositores.ItemCatalogoRepository;
import com.utfpr.concessionaria.services.REGRAS.CatalogoService;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public record ItemCatalogoCRUDservice(ItemCatalogoRepository itemCatalogoRepository, CarUnitsRepository carUnitsRepository, CatalogoService catalogoService) {

    public void addNewCarToCatalog(Carro newCar, Integer quantity){
        itemCatalogoRepository.saveAndFlush(
                ItemCatalogo.builder()
                        .carro(newCar)
                        .quantity(quantity)
                        .build());
        log.info("New Car added to the stock with {} units!", quantity);
        catalogoService.attCarOptions();
    }

    public void removeUnitFromStock(List<ItemCatalogo> cars){
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
}
