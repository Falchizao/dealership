package com.utfpr.concessionaria.services;

import static org.assertj.core.api.Assertions.assertThat;
import com.utfpr.concessionaria.dto.VendaDTO;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.services.REGRAS.VendaService;
import com.utfpr.concessionaria.utils.Utils;
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
            // given - precondition or setup
            given(vendaRepository.findById(venda.getId()))
                    .willReturn(Optional.empty());

            given(vendaRepository.save(venda)).willReturn(venda);

            System.out.println(vendaRepository);
            System.out.println(undetest);

            // when -  action or the behaviour that we are going test
            Venda savedSale = vendaRepository.save(venda);

            System.out.println(savedSale);
            // then - verify the output
            assertThat(savedSale).isNotNull();
        }


        @DisplayName("JUnit test for saveSale method which throws exception")
        @Test
        public void givenExistingId_whenSaveSale_thenThrowsException(){
            // given - precondition or setup
            given(vendaRepository.findById(venda.getId()))
                    .willReturn(Optional.of(venda));

            System.out.println(vendaRepository);
            System.out.println(undetest);

            // when -  action or the behaviour that we are going test
            org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFound.class, () -> {
                vendaRepository.save(venda);
            });

            // then
            verify(vendaRepository, never()).save(any(Venda.class));
        }

        // JUnit test for getAllSales method
        @DisplayName("JUnit test for getAllSales method")
        @Test
        public void givenSalesList_whenGetAllSales_thenReturnSalesList(){
            // given - precondition or setup
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

            // when -  action or the behaviour that we are going test
            List<VendaDTO> vendas = undetest.getAll();

            // then - verify the output
            assertThat(vendas).isNotNull();
            assertThat(vendas.size()).isEqualTo(2);
        }

        // JUnit test for getAllSales method
        @DisplayName("JUnit test for getAllSales method (negative scenario)")
        @Test
        public void givenEmptySaleList_whenGetAllSales_thenReturnEmptySalesList(){
            // given - precondition or setup
            Venda venda1 = Venda.builder()
                    .idCliente(1L)
                    .idPagamento(1L)
                    .atendente(1L)
                    .carro(1L)
                    .carro(1L)
                    .emailCliente("ronaldo@gmail.com")
                    .data_venda(new Date())
                    .build();

            given(vendaRepository.findAll()).willReturn(Collections.emptyList());

            // when -  action or the behaviour that we are going test
            List<VendaDTO> vendaList = undetest.getAll();

            // then - verify the output
            assertThat(vendaList).isEmpty();
            assertThat(vendaList.size()).isEqualTo(0);
        }

        // JUnit test for getSaleById method
        @DisplayName("JUnit test for getSaleById method")
        @Test
        public void givenSaleId_whenGetSaleById_thenReturnSaleObject(){
            // given
            given(vendaRepository.findById(1L)).willReturn(Optional.of(venda));

            // when
            VendaDTO savedSale = undetest.getById(venda.getId()).get();

            // then
            assertThat(savedSale).isNotNull();

        }

        // JUnit test for updateSale method
        @DisplayName("JUnit test for updateSales method")
        @Test
        public void givenSalesObject_whenUpdateSale_thenReturnUpdatedSale(){
            // given - precondition or setup
            given(vendaRepository.save(venda)).willReturn(venda);
            venda.setData_venda(new Date());
            venda.setEmailCliente("ronaldo@gmail.com");

            // when -  action or the behaviour that we are going test
            VendaDTO updatedSale = undetest.update(utils.mapVendaToVendaDTO(venda) , venda.getId());

            // then - verify the output
            assertThat(updatedSale.getEmailCliente()).isEqualTo("ronaldo@gmail.com");
        }

        // JUnit test for deleteSale method
        @DisplayName("JUnit test for deleteSale method")
        @Test
        public void givenSaleId_whenDeleteSale_thenNothing(){
            // given - precondition or setup
            long vendaId = 1L;

            willDoNothing().given(vendaRepository).deleteById(vendaId);

            // when -  action or the behaviour that we are going test
            undetest.delete(vendaId);

            // then - verify the output
            verify(vendaRepository, times(1)).deleteById(vendaId);
        }

}
