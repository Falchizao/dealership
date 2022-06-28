package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.generic.IService;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.AtendenteRepository;
import com.utfpr.concessionaria.view.entities.Atendente;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Consultando atendentes...");
        List<Atendente> atendentes = atendenteRepository.findAll();

        return atendentes.stream()
                .map(person -> AtendenteDTO.builder()
                        .nomeAtendente(person.getNomeAtendente())
                        .permissao(switch (person.getPermissao()) {
                            case SEMDESCONTO -> 0;
                            case PERMITEDESCONTOCOMPLETO -> 1;
                            case PERMITEDESCONTOPARCIAL -> 2;
                        }).build())
                .collect(Collectors.toList());
    }

    @Override
    public AtendenteDTO add(AtendenteDTO atendenteDTO){
        Atendente atendente =  Atendente.builder()
                .nomeAtendente(atendenteDTO.getNomeAtendente())
                .permissao(switch (atendenteDTO.getPermissao()) {
                    case 1 -> PERMITEDESCONTOCOMPLETO;
                    case 2 -> PERMITEDESCONTOPARCIAL;
                    default -> SEMDESCONTO;
                }).build();

        log.info("Adicionando atendente...");
        atendenteRepository.save(atendente);

        return atendenteDTO;
    }

    @Override
    public Optional<AtendenteDTO> getById(Long id){
        Optional<Atendente> person = findAtendente(id);

        AtendenteDTO dto =  AtendenteDTO.builder()
                .nomeAtendente(person.get().getNomeAtendente())
                .permissao(switch (person.get().getPermissao()) {
                    case SEMDESCONTO -> 0;
                    case PERMITEDESCONTOCOMPLETO -> 1;
                    case PERMITEDESCONTOPARCIAL -> 2;
                }).build();
        return Optional.of(dto);
    }

    @Override
    public void delete(Long id){
        atendenteRepository.deleteById(verifyAtendente(id));
    }

    @Override
    public AtendenteDTO update(AtendenteDTO atendenteDTO, Long id){
        log.info("Atualizando atendente...");
        delete(id);
        return add(atendenteDTO);
    }

    public PermissoesAtendente getPermissao(Long id){
        Optional<Atendente> person = atendenteRepository.findById(id);
        return person.get().getPermissao();
    }

    private Long verifyAtendente(Long id){
        log.info("Procurando atendente...");
        Optional<Atendente> atendente = atendenteRepository.findById(id);
        if(atendente.isEmpty()){
            throw new ResourceNotFound("Atendente Not found!");
        }

        log.info("Deletando atendente...");
        return id;
    }

    private Optional<Atendente> findAtendente(Long id){
        log.info("Consultando atendente desejado...");
        Optional<Atendente> person = atendenteRepository.findById(id);

        if(person.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Atendente by id Not found in service!");
        }

        return person;
    }

}
