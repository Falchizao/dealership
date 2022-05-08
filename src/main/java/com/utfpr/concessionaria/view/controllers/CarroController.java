package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.services.CarrrosCRUDservice;
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
public class CarroController {

//     Here goes the Service to be used to get the cars
    @Autowired
    public CarrrosCRUDservice carrosCRUDservice;


    @GetMapping
    public ResponseEntity<List<CarroResponse>> getCars(){
        List<CarroDTO> carsDTOs = carrosCRUDservice.getCars();
        return new ResponseEntity<>(carsDTOs.stream().map(carDTO -> new ModelMapper().map(carDTO, CarroResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<CarroResponse>> getCar(@PathVariable Integer id){

        Optional<CarroDTO> dto = carrosCRUDservice.getCarById(id); //Here we need to attribute the personDTO to another variable
        ModelMapper map = new ModelMapper(); //then we cast into Response class
        CarroResponse carroResp = map.map(dto, CarroResponse.class);

        return new ResponseEntity<>(Optional.of(carroResp), HttpStatus.OK);  //Don't forget it's optional, since its looking for its id
    }

    @PostMapping("/registrar")
    public ResponseEntity<CarroResponse> addCar(@RequestBody CarroRequest carroRequest){
        ModelMapper mapper = new ModelMapper();
        CarroDTO dto = mapper.map(carroRequest, CarroDTO.class);
        dto = carrosCRUDservice.addCar(dto);

        return new ResponseEntity<>(mapper.map(dto, CarroResponse.class), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id){
        carrosCRUDservice.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CarroResponse> uptadePerson(@RequestBody CarroRequest carReq, @PathVariable Integer id){

        ModelMapper mapper = new ModelMapper();
        CarroDTO dto = mapper.map(carReq, CarroDTO.class);
        dto = carrosCRUDservice.uptadeCar(dto, id);

        return new ResponseEntity<>(mapper.map(dto, CarroResponse.class), HttpStatus.OK);
    }

}