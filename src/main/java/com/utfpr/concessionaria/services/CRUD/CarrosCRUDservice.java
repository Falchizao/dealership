package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.CarroRepository;
import com.utfpr.concessionaria.utils.Utils;
import com.utfpr.concessionaria.view.entities.Carro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class CarrosCRUDservice {

    private final CarroRepository carroRepository;
    private final ItemCatalogoCRUDservice itemCatalogoCRUDservice;
    private final Utils utils;

    @Autowired
    public CarrosCRUDservice(CarroRepository carroRepository, ItemCatalogoCRUDservice itemCatalogoCRUDservice, Utils utils) {
        this.carroRepository = carroRepository;
        this.itemCatalogoCRUDservice = itemCatalogoCRUDservice;
        this.utils = utils;
    }

    public List<CarroDTO> getAll(){
        log.info("Consultando carros...");
        List<Carro> carros = carroRepository.findAll();

        return carros.stream()
                .map(car -> CarroDTO.builder() //Entitie builder for return
                        .modelo(car.getModelo())
                        .marca(car.getMarca())
                        .cor(car.getCor())
                        .ano(car.getAno())
                        .placa(car.getPlaca())
                        .chassi(car.getChassi())
                        .valor(car.getValor())
                        .build())
                .collect(Collectors.toList());
    }

    public CarroDTO add(CarroDTO carroDTO, Integer quantity){
        log.info("Adding new Car to DataBase...");
        Carro car= utils.mapCarDtoToCar(carroDTO); //Mapping

        car = carroRepository.saveAndFlush(car);//Adding a new Car to dataBase
        carroDTO.setId(car.getId());

        itemCatalogoCRUDservice.addNewCarToCatalog(car, quantity); //Add Car and Unit to Stock
        log.info("New Car Added...");

        return carroDTO;
    }

    public Optional<CarroDTO> getById(Long id){
        Optional<Carro> car = findCar(id);

        CarroDTO dto = CarroDTO.builder()
                .modelo(car.get().getModelo())
                .marca(car.get().getMarca())
                .cor(car.get().getCor())
                .ano(car.get().getAno())
                .placa(car.get().getPlaca())
                .chassi(car.get().getChassi())
                .valor(car.get().getValor())
                .build();

        return Optional.of(dto);
    }

    public void delete(Long id){
        carroRepository.deleteById(verifyCar(id));
    }

    public CarroDTO update(CarroDTO carroDTO,Integer quantity, Long id){
        delete(id);

        log.info("Atualizando carro...");
        return add(carroDTO, quantity);
    }

    private Long verifyCar(Long id){
        Optional<Carro> car = carroRepository.findById(id);
        if(car.isEmpty()){
            throw new ResourceNotFound("Carro Not found!");
        }

        log.info("Deletando carro...");
        return id;
    }

    public Optional<Carro> findCar(Long id){
        log.info("Consultando carro desejado...");
        Optional<Carro> car = carroRepository.findById(id);

        if(car.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("car by id Not found!");
        }

        log.info("Carro selecionado: {}",  car.get().getMarca());
        return car;
    }

    public Double getCarValue(Long id){
        Optional<Carro> car = findCar(id);
        return car.get().getValor();
    }
}
