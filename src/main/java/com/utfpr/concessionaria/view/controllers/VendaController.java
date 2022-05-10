package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.services.CRUD.VendaCRUDservice;
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

@RequestMapping("/api/vendas")
@RestController
public class VendaController {

    public VendaCRUDservice vendaCRUDService;

    @Autowired
    public VendaController(VendaCRUDservice vendaService) {
        this.vendaCRUDService = vendaService;
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> getVendas(){
        List<VendasDTO> vendasDTOs = vendaCRUDService.getVendas();
        return new ResponseEntity<>(vendasDTOs.stream().map(vendaDTO -> new ModelMapper().map(vendaDTO, VendaResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<VendaResponse>> getVendaById(@PathVariable Long id){

        Optional<VendaDTO> dto = vendaCRUDService.getVendaById(id);
        ModelMapper map = new ModelMapper();
        VendaResponse venda = map.map(dto, VendaResponse.class);

        return new ResponseEntity<>(Optional.of(venda), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVenda(@PathVariable Long id){
        vendaCRUDService.deleteVenda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaResponse> uptadeVenda(@RequestBody VendaRequest venda, @PathVariable Long id){

        ModelMapper mapper = new ModelMapper();
        ClienteDTO dto = mapper.map(venda, VendaDTO.class);
        dto = vendaCRUDService.updateVenda(dto, id);

        return new ResponseEntity<>(mapper.map(dto, VendaResponse.class), HttpStatus.OK);
    }
}
