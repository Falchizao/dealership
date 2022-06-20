package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.generic.IService;
import com.utfpr.concessionaria.modelException.error.ErrorMessage;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.ItemCatalogoRepository;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.services.CRUD.ClientesCRUDservice;
import com.utfpr.concessionaria.view.entities.Carro;
import com.utfpr.concessionaria.view.entities.ItemCatalogo;
import com.utfpr.concessionaria.view.entities.Venda;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ItemCatalogoRepository itemCatalogoRepository;

    @Autowired
    public VendaService(ItemCatalogoRepository itemCatalogoRepository, DescontoService descontoService, VendaRepository vendaRepository, ClientesCRUDservice clienteservice, CarrosCRUDservice carroService) {
        this.descontoService = descontoService;
        this.vendaRepository = vendaRepository;
        this.clienteservice = clienteservice;
        this.carroService = carroService;
        this.itemCatalogoRepository = itemCatalogoRepository;
    }

    @Override
    public List<VendaDTO> getAll(){
        List<Venda> vendas = vendaRepository.findAll();

        log.info("Consultando vendas...");
        return vendas.stream()
                .map(venda ->  VendaDTO.builder() //Entitie builder for return
                        .idCliente(venda.getIdCliente())
                        .idCarro(venda.getCarro())
                        .atendente(venda.getAtendente())
                        .data_venda(venda.getData_venda())
                        .statusVenda(venda.getStatusVenda())
                        .emailCliente(venda.getEmailCliente())
                        .idPagamento(venda.getIdPagamento())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<VendaDTO> getById(Long id){
        log.info("Consultando venda desejada...");

        Optional<Venda> venda = vendaRepository.findById(id);
        if(venda.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Venda by id Not found in service!");
        }

        VendaDTO dto = VendaDTO.builder()
                .idCliente(venda.get().getIdCliente())
                .idCarro(venda.get().getCarro())
                .atendente(venda.get().getAtendente())
                .data_venda(venda.get().getData_venda())
                .statusVenda(venda.get().getStatusVenda())
                .emailCliente(venda.get().getEmailCliente())
                .idPagamento(venda.get().getIdPagamento())
                .build();

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
        //First we need to check if the car is still available
        //So we call our item repository

        Optional<CarroDTO> carro = carroService.getById(vendaDTO.getIdCarro());
        if(carro.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Car by id Not found in venda service!");
        }

        ItemCatalogo itemEmCatalogo = itemCatalogoRepository.findByCarro(Carro.builder()
                .ano(carro.get().getAno())
                .cor(carro.get().getCor())
                .ano(carro.get().getAno())
                .chassi(carro.get().getChassi())
                .marca(carro.get().getMarca())
                .placa(carro.get().getPlaca())
                .valor(carro.get().getValor()).build());

        if(itemEmCatalogo.getQuantity() < 1){
            throw new ErrorMessage("Carro selecionado está indisponível!");
        }

        itemEmCatalogo.setQuantity(itemEmCatalogo.getQuantity() + 1);

        itemCatalogoRepository.save(itemEmCatalogo);

        Optional<ClienteDTO> cliente = clienteservice.getById(vendaDTO.getIdCliente());

        if(cliente.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Cliente by id Not found in venda service!");
        }

        log.info("Carro selecionado: {}",  carro.get().getMarca());

        Venda venda = Venda.builder() //Entitie builder for save
                .idCliente(vendaDTO.getIdCliente())
                .carro(vendaDTO.getIdCarro())
                .atendente(vendaDTO.getAtendente())
                .data_venda(new Date())
                .statusVenda(PENDENTE)
                .emailCliente(cliente.get().getEmailCliente())
                .idPagamento(vendaDTO.getIdPagamento())
                .build();

        //Return DTO
        vendaDTO.setEmailCliente(cliente.get().getEmailCliente());
        vendaDTO.setStatusVenda(venda.getStatusVenda());
        vendaDTO.setData_venda(venda.getData_venda());

        venda = getPaymentInfo(venda,  vendaDTO.getFormaPagamento());

        vendaDTO.setIdPagamento(venda.getIdPagamento());        //Set Payment Method for return

        log.info("Salvando venda...");
        vendaRepository.save(venda);

        return vendaDTO;
    }

    public Venda getPaymentInfo(Venda venda, Integer paymentMethod){ //Informações sobre o pagamento da venda
        log.info("Enviando venda para calculo de desconto...");
        return descontoService.manipulaValorDescontoVenda(venda, paymentMethod);
    }


}
