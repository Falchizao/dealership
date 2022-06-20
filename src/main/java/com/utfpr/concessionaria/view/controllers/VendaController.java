package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.generic.IController;
import com.utfpr.concessionaria.services.REGRAS.VendaService;
import com.utfpr.concessionaria.view.entities.reqresDomain.VendaRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.VendaResponse;
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
        return new ResponseEntity<>(vendasDTOs.stream().map(vendaDTO -> VendaResponse.builder()
                .idCliente(vendaDTO.getIdCliente())
                .idCarro(vendaDTO.getIdCarro())
                .atendente(vendaDTO.getAtendente())
                .idPagamento(vendaDTO.getIdPagamento())
                .emailCliente(vendaDTO.getEmailCliente())
                .statusVenda(vendaDTO.getStatusVenda())
                .build()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Optional<VendaResponse>> getById(@PathVariable Long id){
        Optional<VendaDTO> dto = vendaCRUDService.getById(id);

        VendaResponse venda = VendaResponse.builder()
                .idCliente(dto.get().getIdCliente())
                .idCarro(dto.get().getIdCarro())
                .atendente(dto.get().getAtendente())
                .idPagamento(dto.get().getIdPagamento())
                .emailCliente(dto.get().getEmailCliente())
                .statusVenda(dto.get().getStatusVenda())
                .build();

        return new ResponseEntity<>(Optional.of(venda), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VendaResponse> add(@RequestBody VendaRequest venda){

        VendaDTO dto = VendaDTO.builder()
                .idCliente(venda.getIdCliente())
                .idCarro(venda.getIdCarro())
                .atendente(venda.getAtendente())
                .formaPagamento(venda.getFormaPagamento())
                .build();

        dto = vendaCRUDService.add(dto);

        VendaResponse vendaRes = VendaResponse.builder()
                .idCliente(dto.getIdCliente())
                .idCarro(dto.getIdCarro())
                .atendente(dto.getAtendente())
                .idPagamento(dto.getIdPagamento())
                .emailCliente(dto.getEmailCliente())
                .statusVenda(dto.getStatusVenda())
                .dtaVenda(dto.getData_venda())
                .build();

        return new ResponseEntity<>(vendaRes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        vendaCRUDService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<VendaResponse> update(@RequestBody VendaRequest venda, @PathVariable Long id){

        VendaDTO dto = VendaDTO.builder()
                .idCliente(venda.getIdCliente())
                .idCarro(venda.getIdCarro())
                .atendente(venda.getAtendente())
                .formaPagamento(venda.getFormaPagamento())
                .build();

        dto = vendaCRUDService.update(dto, id);
        return new ResponseEntity<>(VendaResponse.builder()
                .idCliente(dto.getIdCliente())
                .idCarro(dto.getIdCarro())
                .atendente(dto.getAtendente())
                .idPagamento(dto.getIdPagamento())
                .emailCliente(dto.getEmailCliente())
                .statusVenda(dto.getStatusVenda())
                .build(), HttpStatus.OK);
    }
}
