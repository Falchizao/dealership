package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.generic.IService;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.AtendenteRepository;
import com.utfpr.concessionaria.repositores.CarroRepository;
import com.utfpr.concessionaria.repositores.ClienteRepository;
import com.utfpr.concessionaria.view.entities.Atendente;
import com.utfpr.concessionaria.view.entities.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j
public class ClientesCRUDservice extends IService<ClienteDTO> {

    private final ClienteRepository clienteRepository;

    public ClientesCRUDservice(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<ClienteDTO> getAll(){
        List<Cliente> clientes = clienteRepository.findAll();

        log.info("Consultando clientes...");
        return clientes.stream()
                .map(person -> new ModelMapper().map(person, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClienteDTO> getById(Long id){
        log.info("Consultando cliente desejado...");
        Optional<Cliente> person = clienteRepository.findById(id);
        if(person.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Cliente by id Not found in service!");
        }
        ClienteDTO dto = new ModelMapper().map(person.get(), ClienteDTO.class);
        return Optional.of(dto);
    }

    @Override
    public ClienteDTO add(ClienteDTO clienteDTO){
        ModelMapper map = new ModelMapper();
        Cliente cliente = map.map(clienteDTO, Cliente.class);
        log.info("Salvando cliente...");
        clienteRepository.save(cliente);
        //clienteDTO.setId(cliente.getId());

        return clienteDTO;
    }

    @Override
    public void delete(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isEmpty()){
            throw new ResourceNotFound("Cliente by id Not found!");
        }

        log.info("Deletando cliente...");
        clienteRepository.deleteById(id);
    }

    @Override
    public ClienteDTO update(ClienteDTO clienteDTO, Long id){
        //clienteDTO.setId(id);
        delete(id);
        log.info("Atualizando cliente...");
        return add(clienteDTO);
    }
}
