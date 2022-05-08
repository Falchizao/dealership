package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.AtendenteRepository;
import com.utfpr.concessionaria.view.entities.Atendente;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class AtendentesCRUDservice {

    @Autowired
    private AtendenteRepository atendenteRepository;

    public List<AtendenteDTO> getAtendentes(){
        List<Atendente> atendentes = atendenteRepository.findAll();

        log.info("Consultando atendentes...");

        return atendentes.stream()
                .map(person -> new ModelMapper().map(person, AtendenteDTO.class))
                .collect(Collectors.toList());
    }


    public Optional<AtendenteDTO> getAtendenteById(Long id){
        log.info("Consultando atendente desejado...");
        Optional<Atendente> person = atendenteRepository.findById(id);
        AtendenteDTO dto = new ModelMapper().map(person.get(), AtendenteDTO.class);
        return Optional.of(dto);
    }


    public AtendenteDTO addAtendente(AtendenteDTO atendenteDTO){
        ModelMapper map = new ModelMapper();
        Atendente atendente = map.map(atendenteDTO, Atendente.class);
        log.info("Adicionando atendente...");
        atendenteRepository.save(atendente);
        atendenteDTO.setIdAtendente(atendente.getIdAtendente());

        return atendenteDTO;
    }

    public void deleteAtendente(Long id){
        Optional<Atendente> atendente = atendenteRepository.findById(id);

        if(atendente.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Atendente by id Not found!");
        }

        log.info("Deletando atendente...");
        atendenteRepository.deleteById(id);
    }

    public AtendenteDTO uptadeAtendente(AtendenteDTO atendenteDTO, Long id){
        //Passar o id pro Banco, depois deletar o objeto da DB, e adicionar a nova com o body att
        atendenteDTO.setIdAtendente(id); //Se o spring recebe um objeto com id, significa que é para att, caso não, é pra cadastrar
        deleteAtendente(id);

        log.info("Atualizando atendente...");
        return addAtendente(atendenteDTO);
    }

}
