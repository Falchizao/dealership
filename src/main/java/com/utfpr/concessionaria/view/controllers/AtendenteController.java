package com.utfpr.concessionaria.view.controllers;



import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.services.AtendentesCRUDservice;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteRequest;
import com.utfpr.concessionaria.view.entities.reqresDomain.AtendenteResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/atendentes")
public class AtendenteController {

//Here goes the service to get the atendentes
    @Autowired
    public AtendentesCRUDservice atendenteService;

    @GetMapping
    public ResponseEntity<List<AtendenteResponse>> getAtendentes(){
        List<AtendenteDTO> atendentesDTOs = atendenteService.getAtendentes();
        return new ResponseEntity<>(atendentesDTOs.stream().map(atendenteDTO -> new ModelMapper().map(atendenteDTO, AtendenteResponse.class)).collect(Collectors.toList()), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Optional<AtendenteResponse>> getAtendente(@PathVariable Long id){

        Optional<AtendenteDTO> dto = atendenteService.getAtendenteById(id);
        ModelMapper map = new ModelMapper();
        AtendenteResponse atendente = map.map(dto, AtendenteResponse.class);

        return new ResponseEntity<>(Optional.of(atendente), HttpStatus.OK);
    }


    @PostMapping("/registrar")
    public ResponseEntity<AtendenteResponse> addAtendente(@RequestBody AtendenteRequest atendenteReq){
        ModelMapper mapper = new ModelMapper();
        AtendenteDTO dto = mapper.map(atendenteReq, AtendenteDTO.class);
        dto = atendenteService.addAtendente(dto);

        return new ResponseEntity<>(mapper.map(dto, AtendenteResponse.class), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAtendente(@PathVariable Long id){
        atendenteService.deleteAtendente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AtendenteResponse> uptadeAtendente(@RequestBody AtendenteRequest atendente, @PathVariable Long id){

        ModelMapper mapper = new ModelMapper();
        AtendenteDTO dto = mapper.map(atendente, AtendenteDTO.class);
        dto = atendenteService.uptadeAtendente(dto, id);

        return new ResponseEntity<>(mapper.map(dto, AtendenteResponse.class), HttpStatus.OK);
    }

}