package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.CarroRepository;
import com.utfpr.concessionaria.view.entities.Carro;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class CarrosCRUDservice {

    @Autowired
    private CarroRepository carroRepository;

    public List<CarroDTO> getCars(){
        List<Carro> carros = carroRepository.findAll();

        log.info("Consultando carros...");

        return carros.stream()
                .map(car -> new ModelMapper().map(car, CarroDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<CarroDTO> getCarById(Long id){
        Optional<Carro> car = carroRepository.findById(id);
        CarroDTO dto = new ModelMapper().map(car.get(), CarroDTO.class);

        log.info("Consultando carro desejado...");
        return Optional.of(dto);
    }

    public CarroDTO addCar(CarroDTO carroDTO){
        ModelMapper map = new ModelMapper();
        Carro car = map.map(carroDTO, Carro.class);

        log.info("Adicionando carros...");
        carroRepository.save(car);
        carroDTO.setIdCarro(car.getIdCarro());

        return carroDTO;
    }

    public void deleteCar(Long id){
        Optional<Carro> car = carroRepository.findById(id);

        if(car.isEmpty()){
            throw new ResourceNotFound("Carro by id Not found!");
        }

        log.info("Deletando carro...");
        carroRepository.deleteById(id);
    }

    public CarroDTO uptadeCar(CarroDTO carroDTO, Long id){
        carroDTO.setIdCarro(id);
        deleteCar(id);

        log.info("Atualizando carro...");
        return addCar(carroDTO);
    }
}
