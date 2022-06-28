package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.generic.IService;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.ClienteRepository;
import com.utfpr.concessionaria.view.entities.Cliente;
import lombok.extern.slf4j.Slf4j;
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
        log.info("Consultando clientes...");
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(person -> ClienteDTO.builder()
                        .nomeCliente(person.getNomeCliente())
                        .cpfCliente(person.getCpfCliente())
                        .rgCliente(person.getRgCliente())
                        .enderecoCliente(person.getEnderecoCliente())
                        .telefoneCliente(person.getTelefoneCliente())
                        .emailCliente(person.getEmailCliente())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO add(ClienteDTO clienteDTO){
        Cliente cliente = Cliente.builder()
                .nomeCliente(clienteDTO.getNomeCliente())
                .cpfCliente(clienteDTO.getCpfCliente())
                .rgCliente(clienteDTO.getRgCliente())
                .enderecoCliente(clienteDTO.getEnderecoCliente())
                .telefoneCliente(clienteDTO.getTelefoneCliente())
                .emailCliente(clienteDTO.getEmailCliente())
                .build();

        log.info("Salvando cliente...");
        clienteRepository.save(cliente);

        return clienteDTO;
    }

    @Override
    public Optional<ClienteDTO> getById(Long id){
        Optional<Cliente> person = findCliente(id);

        ClienteDTO dto = ClienteDTO.builder() //Entitie builder for return
                .nomeCliente(person.get().getNomeCliente())
                .cpfCliente(person.get().getCpfCliente())
                .rgCliente(person.get().getRgCliente())
                .enderecoCliente(person.get().getEnderecoCliente())
                .telefoneCliente(person.get().getTelefoneCliente())
                .emailCliente(person.get().getEmailCliente())
                .build();

        return Optional.of(dto);
    }

    @Override
    public void delete(Long id){
        clienteRepository.deleteById(verifyCliente(id));
    }

    @Override
    public ClienteDTO update(ClienteDTO clienteDTO, Long id){
        log.info("Atualizando cliente...");
        delete(id);
        return add(clienteDTO);
    }

    private Long verifyCliente(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()){
            throw new ResourceNotFound("Cliente Not found!");
        }

        log.info("Deletando cliente...");
        return id;
    }

    public Optional<Cliente> findCliente(Long id){
        log.info("Consultando cliente desejado...");
        Optional<Cliente> person = clienteRepository.findById(id);

        if(person.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Cliente by id Not found in service!");
        }

        return person;
    }
}
