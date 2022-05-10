package com.utfpr.concessionaria.services.CRUD;

import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.ClienteRepository;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.view.entities.Cliente;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VendaCRUDservice {

    public VendaRepository vendaRepository;

    @Autowired
    public VendaCRUDservice(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public List<VendaDTO> getVendas(){
        List<Venda> vendas = vendaRepository.findAll();

        log.info("Consultando vendas...");
        return vendas.stream()
                .map(venda -> new ModelMapper().map(venda, VendaDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<VendaDTO> getVendaById(Long id){
        log.info("Consultando venda desejada...");
        Optional<Venda> venda = vendaRepository.findById(id);
        VendaDTO dto = new ModelMapper().map(venda.get(), VendaDTO.class);
        return Optional.of(dto);
    }

    public void deleteVenda(Long id){
        Optional<Venda> venda = vendaRepository.findById(id);

        if(venda.isEmpty()){
            throw new ResourceNotFound("Venda by id Not found!");
        }

        log.info("Deletando venda...");
        vendaRepository.deleteById(id);
    }

    public VendaDTO updateVenda(VendaDTO vendaDTO, Long id){
        vendaDTO.setIdVenda(id);
        deleteVenda(id);
        log.info("Atualizando venda...");
        return addVenda(vendaDTO);
    }

    public VendaDTO addVenda(VendaDTO vendaDTO){
        ModelMapper map = new ModelMapper();
        Venda venda = map.map(vendaDTO, Venda.class);
        log.info("Salvando venda...");
        vendaRepository.save(venda);
        vendaDTO.setIdVenda(venda.getIdVenda());

        return vendaDTO;
    }

}
