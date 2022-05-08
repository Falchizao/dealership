package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.services.ClientesCRUDservice;
import com.utfpr.concessionaria.view.entities.reqresDomain.ClienteRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.ClienteResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

//     Here goes the service that will be used to get the data from the database about the clients
    @Autowired
    public ClientesCRUDservice clientesCRUDservice;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> getPersons(){
        List<ClienteDTO> personDTOs = clientesCRUDservice.getPersons();
        return new ResponseEntity<>(personDTOs.stream().map(personDTO -> new ModelMapper().map(personDTO, ClienteResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<ClienteResponse>> getPersonById(@PathVariable Long id){

        Optional<ClienteDTO> dto = clientesCRUDservice.getPersonById(id);
        ModelMapper map = new ModelMapper();
        ClienteResponse person = map.map(dto, ClienteResponse.class);

        return new ResponseEntity<>(Optional.of(person), HttpStatus.OK);
    }


    @PostMapping("/registrar")
    public ResponseEntity<ClienteResponse> addPerson(@RequestBody ClienteRequest personReq){
        ModelMapper mapper = new ModelMapper();
        ClienteDTO dto = mapper.map(personReq, ClienteDTO.class);
        dto = clientesCRUDservice.addPerson(dto);

        return new ResponseEntity<>(mapper.map(dto, ClienteResponse.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id){
        clientesCRUDservice.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> uptadePerson(@RequestBody ClienteRequest person, @PathVariable Long id){

        ModelMapper mapper = new ModelMapper();
        ClienteDTO dto = mapper.map(person, ClienteDTO.class);
        dto = clientesCRUDservice.uptadePerson(dto, id);

        return new ResponseEntity<>(mapper.map(dto, ClienteResponse.class), HttpStatus.OK);
    }

}
