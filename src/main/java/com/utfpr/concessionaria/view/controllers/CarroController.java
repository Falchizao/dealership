package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.generic.IController;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.view.entities.reqresDomain.CarroRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.CarroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carros")
public class CarroController extends IController<CarroResponse, ResponseEntity<?>, CarroRequest> {

    private final CarrosCRUDservice carrosCRUDservice;

    public CarroController(CarrosCRUDservice carrosCRUDservice) {
        this.carrosCRUDservice = carrosCRUDservice;
    }

    @Override
    public ResponseEntity<List<CarroResponse>> getAll(){
        List<CarroDTO> carsDTOs = carrosCRUDservice.getAll();
        return new ResponseEntity<>(carsDTOs.stream().map(carDTO -> CarroResponse.builder()
                .modelo(carDTO.getModelo())
                .marca(carDTO.getMarca())
                .cor(carDTO.getCor())
                .ano(carDTO.getAno())
                .placa(carDTO.getPlaca())
                .chassi(carDTO.getChassi())
                .valor(carDTO.getValor())
                .build()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Optional<CarroResponse>> getById(@PathVariable Long id){

        Optional<CarroDTO> dto = carrosCRUDservice.getById(id);

        CarroResponse carroResp = CarroResponse.builder()
                .modelo(dto.get().getModelo())
                .marca(dto.get().getMarca())
                .cor(dto.get().getCor())
                .ano(dto.get().getAno())
                .placa(dto.get().getPlaca())
                .chassi(dto.get().getChassi())
                .valor(dto.get().getValor())
                .build();

        return new ResponseEntity<>(Optional.of(carroResp), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarroResponse> add(@RequestBody CarroRequest carroRequest){

        CarroDTO dto = CarroDTO.builder()
                .modelo(carroRequest.getModelo())
                .marca(carroRequest.getMarca())
                .cor(carroRequest.getCor())
                .ano(carroRequest.getAno())
                .placa(carroRequest.getPlaca())
                .chassi(carroRequest.getChassi())
                .valor(carroRequest.getValor())
                .build();

        dto = carrosCRUDservice.add(dto, carroRequest.getQuantity());

        return new ResponseEntity<>(CarroResponse.builder()
                .modelo(dto.getModelo())
                .marca(dto.getMarca())
                .cor(dto.getCor())
                .ano(dto.getAno())
                .placa(dto.getPlaca())
                .chassi(dto.getChassi())
                .valor(dto.getValor())
                .build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        carrosCRUDservice.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CarroResponse> update(@RequestBody CarroRequest carReq, @PathVariable Long id){

        CarroDTO dto = CarroDTO.builder()
                .modelo(carReq.getModelo())
                .marca(carReq.getMarca())
                .cor(carReq.getCor())
                .ano(carReq.getAno())
                .placa(carReq.getPlaca())
                .chassi(carReq.getChassi())
                .valor(carReq.getValor())
                .build();

        dto = carrosCRUDservice.update(dto, carReq.getQuantity(), id);

        return new ResponseEntity<>(CarroResponse.builder() //Entitie builder for return
                .modelo(dto.getModelo())
                .marca(dto.getMarca())
                .cor(dto.getCor())
                .ano(dto.getAno())
                .placa(dto.getPlaca())
                .chassi(dto.getChassi())
                .valor(dto.getValor())
                .build(), HttpStatus.OK);
    }
}