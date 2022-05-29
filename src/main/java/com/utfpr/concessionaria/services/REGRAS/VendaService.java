package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.services.CRUD.ClientesCRUDservice;
import com.utfpr.concessionaria.view.entities.Cliente;
import com.utfpr.concessionaria.view.entities.Venda;
import com.utfpr.concessionaria.view.entities.reqresDomain.VendaRequest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.utfpr.concessionaria.enums.StatusVenda.PENDENTE;

@Service
@Slf4j
public record VendaService(DescontoService descontoService, VendaRepository vendaRepository, ClientesCRUDservice clienteservice, CarrosCRUDservice carroService) {

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
        if(venda.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Venda by id Not found in service!");
        }
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
        //vendaDTO.setId(id);
        deleteVenda(id);
        log.info("Atualizando venda...");
        return addVenda(vendaDTO);
    }

    public VendaDTO addVenda(VendaDTO vendaDTO){
        ModelMapper map = new ModelMapper();

        Optional<ClienteDTO> cliente = clienteservice.getPersonById(vendaDTO.getIdCliente());
        Optional<CarroDTO> carro = carroService.getCarById(vendaDTO.getIdCarro());

        log.info("Carro selecionado" + carro.get().getMarca());

        if(cliente.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Cliente by id Not found in venda service!");
        }
        if(carro.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Car by id Not found in venda service!");
        }

        Venda venda = map.map(vendaDTO, Venda.class);
        venda.setEmailCliente(cliente.get().getEmailCliente());
        vendaDTO.setEmailCliente(cliente.get().getEmailCliente());
        venda.setIdCliente(vendaDTO.getIdCliente());
        venda.setCarro(vendaDTO.getIdCarro());
        venda.setValorTotal(carro.get().getValor());
        venda.setData_venda(new Date());
        vendaDTO.setVenda(new Date());
        vendaDTO.setStatusVenda(PENDENTE);

        if(vendaDTO.getFormaPagamento().equals("dinheiro")){
            venda.setFormaPagamento(TipoVenda.DINHEIRO);
        } else if (vendaDTO.getFormaPagamento().equals("debito")){
            venda.setFormaPagamento(TipoVenda.CARTAO_DEBITO);
        } else if(vendaDTO.getFormaPagamento().equals("credito")){
            venda.setFormaPagamento(TipoVenda.CARTAO_CREDITO);
        } else if (vendaDTO.getFormaPagamento().equals("parcelado")){
            venda.setFormaPagamento(TipoVenda.PARCELADO);
        }else{
            venda.setFormaPagamento(null);
        }

        venda.setStatusVenda(PENDENTE);
        vendaDTO.setValorTotal(carro.get().getValor());

        log.info("Salvando venda...");
        vendaRepository.save(inserirDesconto(venda));

        return vendaDTO;
    }

    public Venda inserirDesconto(Venda venda){
        log.info("Enviando venda para calculo de desconto...");
        return descontoService.inspecionaValorVenda(venda);
    }

}
