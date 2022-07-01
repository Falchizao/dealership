package com.utfpr.concessionaria.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.utfpr.concessionaria.dto.ClienteDTO;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.ClienteRepository;
import com.utfpr.concessionaria.services.CRUD.ClientesCRUDservice;
import com.utfpr.concessionaria.utils.Utils;
import com.utfpr.concessionaria.view.entities.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private Utils utils;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClientesCRUDservice undetest;

    private Cliente cliente;

    @BeforeEach
    public void setup(){
        cliente = Cliente.builder()
                .emailCliente("valcir@gmail.com")
                .cpfCliente("34532345432")
                .nomeCliente("valcir")
                .enderecoCliente("Teste")
                .build();
    }

    // JUnit test for saveClient method
    @DisplayName("JUNIT for Save Client")
    @Test
    public void givenClientObject_whenClientSale_thenReturnClientObject(){
        //Arrange --Preparation
        given(clienteRepository.findById(cliente.getId()))
                .willReturn(Optional.empty());

        given(clienteRepository.save(cliente)).willReturn(cliente);

        System.out.println(clienteRepository);
        System.out.println(undetest);

        //Act --Action
        Cliente savedClient = clienteRepository.save(cliente);

        System.out.println(savedClient);
        //Assert -- Confirmation
        assertThat(savedClient).isNotNull();
    }


    @DisplayName("JUnit test for saveClient method which throws exception")
    @Test
    public void givenExistingId_whenSaveClient_thenThrowsException(){
        //Arrange --Preparation
        given(clienteRepository.findById(cliente.getId()))
                .willReturn(Optional.of(cliente));

        System.out.println(clienteRepository);
        System.out.println(undetest);

        //Act --Action
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFound.class, () -> {
            clienteRepository.save(cliente);
        });

        //Assert -- Confirmation
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    // JUnit test for getAllClients method
    @DisplayName("JUnit test for getAllClients method")
    @Test
    public void givenClientsList_whenGetAllClients_thenReturnClientsList(){
        //Arrange --Preparation
        Cliente cliente2 = Cliente.builder()
                .emailCliente("falchi@gmail.com")
                .cpfCliente("24532345432")
                .nomeCliente("marcelo")
                .enderecoCliente("Teste")
                .build();

        given(clienteRepository.findAll()).willReturn(List.of(cliente,cliente2));

        //Act --Action
        List<ClienteDTO> clientes = undetest.getAll();

        //Assert -- Confirmation
        assertThat(clientes).isNotNull();
        assertThat(clientes.size()).isEqualTo(2);
    }

    // JUnit test for getAllClients method
    @DisplayName("JUnit test for getAllClients method (negative scenario)")
    @Test
    public void givenEmptyClientList_whenGetAllClients_thenReturnEmptyClientsList(){
        //Arrange --Preparation
        given(clienteRepository.findAll()).willReturn(Collections.emptyList());

        //Act --Action
        List<ClienteDTO> clientsList = undetest.getAll();

        //Assert -- Confirmation
        assertThat(clientsList).isEmpty();
        assertThat(clientsList.size()).isEqualTo(0);
    }

    // JUnit test for getClientById method
    @DisplayName("JUnit test for getClientById method")
    @Test
    public void givenClientId_whenGetClientById_thenReturnClientObject(){
        //Arrange --Preparation
        given(clienteRepository.findById(1L)).willReturn(Optional.of(cliente));

        //Act --Action
        ClienteDTO savedClient = undetest.getById(cliente.getId()).get();

        //Assert -- Confirmation
        assertThat(savedClient).isNotNull();

    }

    // JUnit test for updateClient method
    @DisplayName("JUnit test for updateClients method")
    @Test
    public void givenClientsObject_whenUpdateClient_thenReturnUpdatedClient(){
        //Arrange --Preparation
        given(clienteRepository.save(cliente)).willReturn(cliente);

        cliente.setEmailCliente("ronaldo@gmail.com");

        //Act --Action
        ClienteDTO updatedClient = undetest.update(utils.mapClienteToClienteDTO(cliente) , cliente.getId());

        //Assert -- Confirmation
        assertThat(updatedClient.getEmailCliente()).isEqualTo("ronaldo@gmail.com");
    }

    // JUnit test for deleteClient method
    @DisplayName("JUnit test for deleteClient method")
    @Test
    public void givenClientId_whenDeleteClient_thenNothing(){
        //Arrange --Preparation
        long clienteId = 1L;

        willDoNothing().given(clienteRepository).deleteById(clienteId);

        //Act --Action
        undetest.delete(clienteId);

        //Assert -- Confirmation
        verify(clienteRepository, times(1)).deleteById(clienteId);
    }

}
