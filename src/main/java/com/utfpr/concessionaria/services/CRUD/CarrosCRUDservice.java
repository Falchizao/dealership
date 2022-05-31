package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.generic.IService;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.AtendenteRepository;
import com.utfpr.concessionaria.repositores.CarroRepository;
import com.utfpr.concessionaria.view.entities.Carro;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class CarrosCRUDservice extends IService<CarroDTO> {

    private final CarroRepository carroRepository;

    public CarrosCRUDservice(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    @Override
    public List<CarroDTO> getAll(){
        List<Carro> carros = carroRepository.findAll();

        log.info("Consultando carros...");

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


    public Optional<CarroDTO> getById(Long id){
        Optional<Carro> car = carroRepository.findById(id);
        if(car.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("car by id Not found!");
        }

        CarroDTO dto = CarroDTO.builder() //Entitie builder for return
                .modelo(car.get().getModelo())
                .marca(car.get().getMarca())
                .cor(car.get().getCor())
                .ano(car.get().getAno())
                .placa(car.get().getPlaca())
                .chassi(car.get().getChassi())
                .valor(car.get().getValor())
                .build();

        log.info("Consultando carro desejado...");
        return Optional.of(dto);
    }

    public CarroDTO add(CarroDTO carroDTO){

        Carro car = Carro.builder()
                .modelo(carroDTO.getModelo())
                .marca(carroDTO.getMarca())
                .cor(carroDTO.getCor())
                .ano(carroDTO.getAno())
                .placa(carroDTO.getPlaca())
                .chassi(carroDTO.getChassi())
                .valor(carroDTO.getValor())
                .build();

        log.info("Adicionando carros...");
        carroRepository.save(car);

        return carroDTO;
    }

    public void delete(Long id){

        Optional<Carro> car = carroRepository.findById(id);
        if(car.isEmpty()){
            throw new ResourceNotFound("Carro by id Not found!");
        }

        log.info("Deletando carro...");
        carroRepository.deleteById(id);
    }

    public CarroDTO update(CarroDTO carroDTO, Long id){
        //carroDTO.setId(id);
        delete(id);

        log.info("Atualizando carro...");
        return add(carroDTO);
    }
}
