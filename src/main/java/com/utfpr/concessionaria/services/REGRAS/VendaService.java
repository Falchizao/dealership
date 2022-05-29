package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.enums.TipoVenda;
import com.utfpr.concessionaria.generic.IService;
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
public class VendaService extends IService<VendaDTO>{

    private final DescontoService descontoService;
    private final VendaRepository vendaRepository;
    private final ClientesCRUDservice clienteservice;
    private final CarrosCRUDservice carroService;

    public VendaService(DescontoService descontoService, VendaRepository vendaRepository, ClientesCRUDservice clienteservice, CarrosCRUDservice carroService) {
        this.descontoService = descontoService;
        this.vendaRepository = vendaRepository;
        this.clienteservice = clienteservice;
        this.carroService = carroService;
    }

    @Override
    public List<VendaDTO> getAll(){
        List<Venda> vendas = vendaRepository.findAll();

        log.info("Consultando vendas...");
        return vendas.stream()
                .map(venda -> new ModelMapper().map(venda, VendaDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VendaDTO> getById(Long id){
        log.info("Consultando venda desejada...");
        Optional<Venda> venda = vendaRepository.findById(id);
        if(venda.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Venda by id Not found in service!");
        }
        VendaDTO dto = new ModelMapper().map(venda.get(), VendaDTO.class);
        return Optional.of(dto);
    }

    @Override
    public void delete(Long id){
        Optional<Venda> venda = vendaRepository.findById(id);

        if(venda.isEmpty()){
            throw new ResourceNotFound("Venda by id Not found!");
        }

        log.info("Deletando venda...");
        vendaRepository.deleteById(id);
    }

    @Override
    public VendaDTO update(VendaDTO vendaDTO, Long id){
        //vendaDTO.setId(id);
        delete(id);
        log.info("Atualizando venda...");
        return add(vendaDTO);
    }

    @Override
    public VendaDTO add(VendaDTO vendaDTO){
        ModelMapper map = new ModelMapper();

        Optional<ClienteDTO> cliente = clienteservice.getById(vendaDTO.getIdCliente());
        Optional<CarroDTO> carro = carroService.getById(vendaDTO.getIdCarro());

        log.info("Carro selecionado" + carro.get().getMarca());

        if(cliente.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Cliente by id Not found in venda service!");
        }
        if(carro.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Car by id Not found in venda service!");
        }

        Venda venda = map.map(vendaDTO, Venda.class);

        //Venda Config
        venda.setEmailCliente(cliente.get().getEmailCliente());
        venda.setIdCliente(vendaDTO.getIdCliente());
        venda.setCarro(vendaDTO.getIdCarro());
        venda.setValorTotal(carro.get().getValor());
        venda.setData_venda(new Date());

        //Return DTO
        vendaDTO.setEmailCliente(cliente.get().getEmailCliente());
        vendaDTO.setVenda(new Date());
        vendaDTO.setStatusVenda(PENDENTE);

        //Payment Method
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

        //Set status venda
        venda.setStatusVenda(PENDENTE);

        //Set total value
        vendaDTO.setValorTotal(carro.get().getValor());

        log.info("Salvando venda...");
        vendaRepository.save(inserirDesconto(venda));

        return vendaDTO;
    }

    public Venda inserirDesconto(Venda venda){ //Discount routine
        log.info("Enviando venda para calculo de desconto...");
        return descontoService.inspecionaValorVenda(venda);
    }

}
