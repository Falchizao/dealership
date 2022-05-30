package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.generic.IService;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.AtendenteRepository;
import com.utfpr.concessionaria.view.entities.Atendente;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.utfpr.concessionaria.enums.PermissoesAtendente.*;

@Service @Slf4j
public class AtendentesCRUDservice extends IService<AtendenteDTO> {

    private final AtendenteRepository atendenteRepository;

    public AtendentesCRUDservice(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }

    @Override
    public List<AtendenteDTO> getAll(){
        List<Atendente> atendentes = atendenteRepository.findAll();

        log.info("Consultando atendentes...");

        return atendentes.stream()
                .map(person -> new ModelMapper().map(person, AtendenteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AtendenteDTO> getById(Long id){
        log.info("Consultando atendente desejado...");
        Optional<Atendente> person = atendenteRepository.findById(id);
        if(person.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Atendente by id Not found in service!");
        }
        AtendenteDTO dto = new ModelMapper().map(person.get(), AtendenteDTO.class);
        return Optional.of(dto);
    }

    @Override
    public AtendenteDTO add(AtendenteDTO atendenteDTO){
        ModelMapper map = new ModelMapper();
        Atendente atendente = map.map(atendenteDTO, Atendente.class);

        switch(atendenteDTO.getPermissao()){
            case 0 -> atendente.setPermissao(SEMDESCONTO);
            case 1 -> atendente.setPermissao(PERMITEDESCONTOCOMPLETO);
            case 2 -> atendente.setPermissao(PERMITEDESCONTOPARCIAL);
        }

        log.info("Adicionando atendente...");
        atendenteRepository.save(atendente);
        //atendenteDTO.setId(atendente.getId());

        return atendenteDTO;
    }

    @Override
    public void delete(Long id){
        Optional<Atendente> atendente = atendenteRepository.findById(id);

        if(atendente.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Atendente by id Not found!");
        }

        log.info("Deletando atendente...");
        atendenteRepository.deleteById(id);
    }

    @Override
    public AtendenteDTO update(AtendenteDTO atendenteDTO, Long id){
        //Passar o id pro Banco, depois deletar o objeto da DB, e adicionar a nova com o body att
        //atendenteDTO.setId(id); //Se o spring recebe um objeto com id, significa que é para att, caso não, é pra cadastrar
        delete(id);

        log.info("Atualizando atendente...");
        return add(atendenteDTO);
    }

    public PermissoesAtendente getPermissao(Long id){
        Optional<Atendente> person = atendenteRepository.findById(id);
        return person.get().getPermissao();
    }

}
