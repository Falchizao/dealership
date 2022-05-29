package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.generic.IController;
import com.utfpr.concessionaria.services.REGRAS.VendaService;
import com.utfpr.concessionaria.view.entities.reqresDomain.VendaRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.VendaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/api/vendas")
@RestController
public class VendaController extends IController<VendaResponse, ResponseEntity<?>, VendaRequest> {

    private final VendaService vendaCRUDService;

    public VendaController(VendaService vendaCRUDService) {
        this.vendaCRUDService = vendaCRUDService;
    }

    @Override
    public ResponseEntity<List<VendaResponse>> getAll(){
        List<VendaDTO> vendasDTOs = vendaCRUDService.getAll();
        return new ResponseEntity<>(vendasDTOs.stream().map(vendaDTO -> new ModelMapper().map(vendaDTO, VendaResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Optional<VendaResponse>> getById(@PathVariable Long id){
        Optional<VendaDTO> dto = vendaCRUDService.getById(id);
        ModelMapper map = new ModelMapper();
        VendaResponse venda = map.map(dto, VendaResponse.class);
        return new ResponseEntity<>(Optional.of(venda), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VendaResponse> add(@RequestBody VendaRequest venda){
        ModelMapper mapper = new ModelMapper();
        VendaDTO dto = mapper.map(venda, VendaDTO.class);
        dto = vendaCRUDService.add(dto);
        VendaResponse vendaRes = mapper.map(dto, VendaResponse.class);
        return new ResponseEntity<>(vendaRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        vendaCRUDService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<VendaResponse> update(@RequestBody VendaRequest venda, @PathVariable Long id){
        ModelMapper mapper = new ModelMapper();
        VendaDTO dto = mapper.map(venda, VendaDTO.class);
        dto = vendaCRUDService.update(dto, id);
        return new ResponseEntity<>(mapper.map(dto, VendaResponse.class), HttpStatus.OK);
    }
}
