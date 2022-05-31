package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.generic.IController;
import com.utfpr.concessionaria.services.CRUD.ClientesCRUDservice;
import com.utfpr.concessionaria.view.entities.reqresDomain.ClienteRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.ClienteResponse;
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
        return new ResponseEntity<>(personDTOs.stream().map(personDTO -> ClienteResponse.builder()
                .nomeCliente(personDTO.getNomeCliente())
                .emailCliente(personDTO.getEmailCliente())
                .build()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Optional<ClienteResponse>> getById(@PathVariable Long id){
        Optional<ClienteDTO> dto = clientesCRUDservice.getById(id);

        ClienteResponse person = ClienteResponse.builder()
                .nomeCliente(dto.get().getNomeCliente())
                .emailCliente(dto.get().getEmailCliente())
                .build();

        return new ResponseEntity<>(Optional.of(person), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ClienteResponse> add(@RequestBody ClienteRequest personReq){

        ClienteDTO dto = ClienteDTO.builder()
                .nomeCliente(personReq.getNomeCliente())
                .cpfCliente(personReq.getCpfCliente())
                .rgCliente(personReq.getRgCliente())
                .enderecoCliente(personReq.getEnderecoCliente())
                .telefoneCliente(personReq.getTelefoneCliente())
                .emailCliente(personReq.getEmailCliente())
                .build();

        dto = clientesCRUDservice.add(dto);
        return new ResponseEntity<>(ClienteResponse.builder()
                .nomeCliente(dto.getNomeCliente())
                .emailCliente(dto.getEmailCliente())
                .build(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        clientesCRUDservice.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ClienteResponse> update(@RequestBody ClienteRequest person, @PathVariable Long id){

        ClienteDTO dto = ClienteDTO.builder()
                .nomeCliente(person.getNomeCliente())
                .cpfCliente(person.getCpfCliente())
                .rgCliente(person.getRgCliente())
                .enderecoCliente(person.getEnderecoCliente())
                .telefoneCliente(person.getTelefoneCliente())
                .emailCliente(person.getEmailCliente())
                .build();

        dto = clientesCRUDservice.update(dto, id);
        return new ResponseEntity<>(ClienteResponse.builder()
                .nomeCliente(dto.getNomeCliente())
                .emailCliente(dto.getEmailCliente())
                .build(), HttpStatus.OK);
    }
}
