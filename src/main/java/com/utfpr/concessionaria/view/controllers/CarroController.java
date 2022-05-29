package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.generic.IController;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteResponse;
import com.utfpr.concessionaria.view.entities.reqresDomain.CarroRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.CarroResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new ResponseEntity<>(carsDTOs.stream().map(carDTO -> new ModelMapper().map(carDTO, CarroResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Optional<CarroResponse>> getById(@PathVariable Long id){
        Optional<CarroDTO> dto = carrosCRUDservice.getById(id);
        ModelMapper map = new ModelMapper();
        CarroResponse carroResp = map.map(dto, CarroResponse.class);
        return new ResponseEntity<>(Optional.of(carroResp), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CarroResponse> add(@RequestBody CarroRequest carroRequest){
        ModelMapper mapper = new ModelMapper();
        CarroDTO dto = mapper.map(carroRequest, CarroDTO.class);
        dto = carrosCRUDservice.add(dto);
        return new ResponseEntity<>(mapper.map(dto, CarroResponse.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        carrosCRUDservice.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CarroResponse> update(@RequestBody CarroRequest carReq, @PathVariable Long id){
        ModelMapper mapper = new ModelMapper();
        CarroDTO dto = mapper.map(carReq, CarroDTO.class);
        dto = carrosCRUDservice.update(dto, id);
        return new ResponseEntity<>(mapper.map(dto, CarroResponse.class), HttpStatus.OK);
    }

}