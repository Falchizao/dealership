package com.utfpr.concessionaria.view.controllers;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.generic.IController;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/atendentes")
public class AtendenteController extends IController<AtendenteResponse, ResponseEntity<?>, AtendenteRequest> {

    private final AtendentesCRUDservice atendenteService;

    public AtendenteController(AtendentesCRUDservice atendenteService) {
        this.atendenteService = atendenteService;
    }

    @Override
    public ResponseEntity<List<AtendenteResponse>> getAll(){
        List<AtendenteDTO> atendentesDTOs = atendenteService.getAll();
        return new ResponseEntity<>(atendentesDTOs.stream().map(atendenteDTO -> AtendenteResponse.builder()
                .nomeAtendente(atendenteDTO.getNomeAtendente())
                .permissoesAtendente(atendenteDTO.getPermissao())
                .build()).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Optional<AtendenteResponse>> getById(@PathVariable Long id){
        Optional<AtendenteDTO> dto = atendenteService.getById(id);

        if(!dto.isPresent()){
            return null;
        }

        AtendenteResponse atendente =  AtendenteResponse.builder()
                .nomeAtendente(dto.get().getNomeAtendente())
                .permissoesAtendente(dto.get().getPermissao())
                .build();


        return new ResponseEntity<>(Optional.of(atendente), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AtendenteResponse> add(@RequestBody AtendenteRequest atendenteReq){

        AtendenteDTO dto = AtendenteDTO.builder()
                .nomeAtendente(atendenteReq.getNomeAtendente())
                .permissao(atendenteReq.getPermissao())
                .build();

        dto = atendenteService.add(dto);

        AtendenteResponse at =  AtendenteResponse.builder()
                .nomeAtendente(dto.getNomeAtendente())
                .permissoesAtendente(dto.getPermissao())
                .build();

//        switch(dto.getPermissao()){
//            case 0 -> at.setPermissoesAtendente(SEMDESCONTO);
//            case 1 -> at.setPermissoesAtendente(PERMITEDESCONTOCOMPLETO);
//            case 2 -> at.setPermissoesAtendente(PERMITEDESCONTOPARCIAL);
//        }

        return new ResponseEntity<>(at, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable Long id){
        atendenteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<AtendenteResponse> update(@RequestBody AtendenteRequest atendente, @PathVariable Long id){

        AtendenteDTO dto = AtendenteDTO.builder()
                .nomeAtendente(atendente.getNomeAtendente())
                .permissao(atendente.getPermissao())
                .build();

        dto = atendenteService.update(dto, id);

        return new ResponseEntity<>(AtendenteResponse.builder()
                .nomeAtendente(dto.getNomeAtendente())
                .permissoesAtendente(dto.getPermissao())
                .build(), HttpStatus.OK);
    }
}