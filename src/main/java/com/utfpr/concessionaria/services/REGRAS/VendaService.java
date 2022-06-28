package com.utfpr.concessionaria.services.REGRAS;

import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.generic.IService;
import com.utfpr.concessionaria.modelException.error.ErrorMessage;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.ItemCatalogoRepository;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.services.CRUD.ClientesCRUDservice;
import com.utfpr.concessionaria.view.entities.Cliente;
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
    private final ItemCatalogoRepository itemCatalogoRepository;

    @Autowired
    public VendaService(ItemCatalogoRepository itemCatalogoRepository, DescontoService descontoService, VendaRepository vendaRepository, ClientesCRUDservice clienteservice) {
        this.descontoService = descontoService;
        this.vendaRepository = vendaRepository;
        this.clienteservice = clienteservice;
        this.itemCatalogoRepository = itemCatalogoRepository;
    }

    @Override
    public List<VendaDTO> getAll(){
        log.info("Consultando vendas...");
        List<Venda> vendas = vendaRepository.findAll();

        return vendas.stream()
                .map(venda ->  VendaDTO.builder()
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
        Optional<Venda> venda = findVenda(id);

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
        vendaRepository.deleteById(verifyVenda(id));
    }

    @Override
    public VendaDTO update(VendaDTO vendaDTO, Long id){
        log.info("Atualizando venda...");
        delete(id);
        return add(vendaDTO);
    }

    @Override
    public VendaDTO add(VendaDTO vendaDTO){
        Optional<ItemCatalogo> itemEmCatalogo = itemCatalogoRepository.finderByCar(vendaDTO.getIdCarro()); //Check Catalog Quantity
        trataVenda(itemEmCatalogo.get()); //Routine Quantity
        Optional<Cliente> cliente = clienteservice.findCliente(vendaDTO.getIdCliente()); //Get Client for the transactional
        Venda venda = getPaymentInfo(vendaBuilder(vendaDTO, cliente.get()),  vendaDTO.getFormaPagamento()); //Manipulating the parcels and discounts
        saveVenda(venda); //Saving the sale

        return setComprovanteVenda(venda, vendaDTO, cliente.get()); //Sale info return;
    }

    private Optional<Venda> findVenda(Long id){
        log.info("Consultando venda desejada...");
        Optional<Venda> venda = vendaRepository.findById(id);

        if(venda.isEmpty()){ //If not found, we throw a exception
            throw new ResourceNotFound("Venda by id Not found in service!");
        }

        return venda;
    }

    private Long verifyVenda(Long id){
        Optional<Venda> venda = vendaRepository.findById(id);

        if(venda.isEmpty()){
            throw new ResourceNotFound("Venda Not found!");
        }

        log.info("Deletando venda...");
        return id;
    }

    private Venda getPaymentInfo(Venda venda, Integer paymentMethod){ //Informações sobre o pagamento da venda
        log.info("Enviando venda para calculo de desconto...");
        return descontoService.manipulaValorDescontoVenda(venda, paymentMethod);
    }

    private void trataVenda(ItemCatalogo itemEmCatalogo){
        if(itemEmCatalogo.getQuantity() >= 1) //update routine
        {
            itemCatalogoRepository.updateUnit(itemEmCatalogo.getQuantity() - 1, itemEmCatalogo.getId());
        }else //No Stock for this unit
        {
            throw new ErrorMessage("Este Carro não está mais disponível em estoque!");
        }
    }

    private void saveVenda(Venda venda){
        log.info("Salvando venda...");
        vendaRepository.save(venda);
    }

    private Venda vendaBuilder(VendaDTO vendaDTO, Cliente cliente){
        return Venda.builder()
                .idCliente(vendaDTO.getIdCliente())
                .carro(vendaDTO.getIdCarro())
                .atendente(vendaDTO.getAtendente())
                .data_venda(new Date())
                .statusVenda(PENDENTE)
                .emailCliente(cliente.getEmailCliente())
                .idPagamento(vendaDTO.getIdPagamento())
                .build();
    }

    private VendaDTO setComprovanteVenda(Venda venda,VendaDTO vendaDTO,Cliente cliente){
        vendaDTO.setEmailCliente(cliente.getEmailCliente());
        vendaDTO.setStatusVenda(venda.getStatusVenda());
        vendaDTO.setData_venda(venda.getData_venda());
        vendaDTO.setIdPagamento(venda.getIdPagamento());

        return vendaDTO;
    }
}
