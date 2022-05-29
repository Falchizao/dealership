package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.generic.IController;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.services.CRUD.ClientesCRUDservice;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteResponse;
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
public class ClienteController extends IController<ClienteResponse, ResponseEntity<?>, ClienteRequest> {

    private final ClientesCRUDservice clientesCRUDservice;

    public ClienteController(ClientesCRUDservice clientesCRUDservice) {
        this.clientesCRUDservice = clientesCRUDservice;
    }

    @Override
    public ResponseEntity<List<ClienteResponse>> getAll(){
        List<ClienteDTO> personDTOs = clientesCRUDservice.getAll();
        return new ResponseEntity<>(personDTOs.stream().map(personDTO -> new ModelMapper().map(personDTO, ClienteResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Optional<ClienteResponse>> getById(@PathVariable Long id){
        Optional<ClienteDTO> dto = clientesCRUDservice.getById(id);
        ModelMapper map = new ModelMapper();
        ClienteResponse person = map.map(dto, ClienteResponse.class);
        return new ResponseEntity<>(Optional.of(person), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteResponse> add(@RequestBody ClienteRequest personReq){
        ModelMapper mapper = new ModelMapper();
        ClienteDTO dto = mapper.map(personReq, ClienteDTO.class);
        dto = clientesCRUDservice.add(dto);
        return new ResponseEntity<>(mapper.map(dto, ClienteResponse.class), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        clientesCRUDservice.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ClienteResponse> update(@RequestBody ClienteRequest person, @PathVariable Long id){
        ModelMapper mapper = new ModelMapper();
        ClienteDTO dto = mapper.map(person, ClienteDTO.class);
        dto = clientesCRUDservice.update(dto, id);
        return new ResponseEntity<>(mapper.map(dto, ClienteResponse.class), HttpStatus.OK);
    }
}
