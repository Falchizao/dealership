package com.utfpr.concessionaria.services;

import static org.assertj.core.api.Assertions.assertThat;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.services.REGRAS.VendaService;
import com.utfpr.concessionaria.utils.Utils;
import org.junit.jupiter.api.Assertions;
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
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.view.entities.Venda;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class VendaServiceTest {

        @Mock
        private Utils utils;

        @Mock
        private VendaRepository vendaRepository;

        @InjectMocks
        private VendaService undetest;

        private Venda venda;

        @BeforeEach
        public void setup(){
            venda = Venda.builder()
                    .idCliente(1L)
                    .idPagamento(1L)
                    .atendente(1L)
                    .carro(1L)
                    .carro(1L)
                    .emailCliente("valcir@gmail.com")
                    .data_venda(new Date())
                    .build();
        }

        // JUnit test for saveSale method
        @DisplayName("JUNIT for Save Sale")
        @Test
        public void givenSaleObject_whenSaveSale_thenReturnSaleObject(){
            //Arrange --Preparation
            given(vendaRepository.findById(venda.getId()))
                    .willReturn(Optional.empty());

            given(vendaRepository.save(venda)).willReturn(venda);

            System.out.println(vendaRepository);
            System.out.println(undetest);

            //Act --Action
            Venda savedSale = vendaRepository.save(venda);

            System.out.println(savedSale);
            //Assert -- Confirmation
            Assertions.assertNotNull(savedSale);
        }


        @DisplayName("JUnit test for saveSale method which throws exception")
        @Test
        public void givenExistingId_whenSaveSale_thenThrowsException(){
            //Arrange --Preparation

            given(vendaRepository.findById(venda.getId()))
                    .willReturn(Optional.of(venda));

            System.out.println(vendaRepository);
            System.out.println(undetest.getAll());

            //Act --Action
            org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class,
                    ()->{ vendaRepository.save(venda);
            });

            //Assert -- Confirmation
            verify(vendaRepository, never()).save(any(Venda.class));
        }

        // JUnit test for getAllSales method
        @DisplayName("JUnit test for getAllSales method")
        @Test
        public void givenSalesList_whenGetAllSales_thenReturnSalesList(){
            //Arrange --Preparation
            Venda venda2 = Venda.builder()
                    .idCliente(1L)
                    .idPagamento(1L)
                    .atendente(1L)
                    .carro(1L)
                    .carro(1L)
                    .emailCliente("ronaldo@gmail.com")
                    .data_venda(new Date())
                    .build();

            given(vendaRepository.findAll()).willReturn(List.of(venda,venda2));

            //Act --Action
            List<VendaDTO> vendas = undetest.getAll();

            //Assert -- Confirmation
            assertThat(vendas).isNotNull();
            assertThat(vendas.size()).isEqualTo(2);
        }

        // JUnit test for getAllSales method
        @DisplayName("JUnit test for getAllSales method (negative scenario)")
        @Test
        public void givenEmptySaleList_whenGetAllSales_thenReturnEmptySalesList(){
            //Arrange --Preparation
            given(vendaRepository.findAll()).willReturn(Collections.emptyList());

            //Act --Action
            List<VendaDTO> vendaList = undetest.getAll();

            //Assert -- Confirmation
            assertThat(vendaList).isEmpty();
            assertThat(vendaList.size()).isEqualTo(0);
        }

        // JUnit test for getSaleById method
        @DisplayName("JUnit test for getSaleById method")
        @Test
        public void givenSaleId_whenGetSaleById_thenReturnSaleObject(){
            //Arrange --Preparation
            Venda venda1 = Venda.builder()
                    .idCliente(1L)
                    .idPagamento(1L)
                    .atendente(1L)
                    .carro(1L)
                    .carro(1L)
                    .emailCliente("ronaldo@gmail.com")
                    .data_venda(new Date())
                    .build();

            venda1 = vendaRepository.save(venda1);
            given(vendaRepository.findById(venda1.getId())).willReturn(Optional.of(venda));

            //Act --Action
            VendaDTO savedSale = undetest.getById(venda.getId()).get();

            //Assert -- Confirmation
            assertThat(savedSale).isNotNull();

        }

        // JUnit test for updateSale method
        @DisplayName("JUnit test for updateSales method")
        @Test
        public void givenSalesObject_whenUpdateSale_thenReturnUpdatedSale(){
            //Arrange --Preparation
            given(vendaRepository.save(venda)).willReturn(venda);
            venda.setData_venda(new Date());
            venda.setEmailCliente("ronaldo@gmail.com");

            //Act --Action
            VendaDTO updatedSale = undetest.update(utils.mapVendaToVendaDTO(venda) , venda.getId());

            //Assert -- Confirmation
            assertThat(updatedSale.getEmailCliente()).isEqualTo("ronaldo@gmail.com");
        }

        // JUnit test for deleteSale method
        @DisplayName("JUnit test for deleteSale method")
        @Test
        public void givenSaleId_whenDeleteSale_thenNothing(){
            //Arrange --Preparation
            long vendaId = 1L;

            willDoNothing().given(vendaRepository).deleteById(vendaId);

            //Act --Action
            undetest.delete(vendaId);

            //Assert -- Confirmation
            verify(vendaRepository, times(1)).deleteById(vendaId);
        }

}
