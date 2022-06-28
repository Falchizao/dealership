package com.utfpr.concessionaria.services;

import static org.assertj.core.api.Assertions.assertThat;
import com.utfpr.concessionaria.dto.AtendenteDTO;
import com.utfpr.concessionaria.enums.PermissoesAtendente;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.AtendenteRepository;
import com.utfpr.concessionaria.services.CRUD.AtendentesCRUDservice;
import com.utfpr.concessionaria.utils.Utils;
import com.utfpr.concessionaria.view.entities.Atendente;
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
public class AtendenteServiceTest {

    private Utils utils;

    @Mock
    private AtendenteRepository atendenteRepositoryRepository;

    @InjectMocks
    private AtendentesCRUDservice undetest;

    private Atendente atendente;

    @BeforeEach
    public void setup(){
        atendente = Atendente.builder()
                .nomeAtendente("Valcir")
                .senha("123")
                .permissao(PermissoesAtendente.SEMDESCONTO)
                .build();
    }

    // JUnit test for save attendant method
    @DisplayName("JUNIT for Save Attendant")
    @Test
    public void givenAttendantObject_whenSaveAttendant_thenReturnAttendantObject(){
        // given - precondition or setup
        given(atendenteRepositoryRepository.findById(atendente.getId()))
                .willReturn(Optional.empty());

        given(atendenteRepositoryRepository.save(atendente)).willReturn(atendente);

        System.out.println(atendenteRepositoryRepository);
        System.out.println(undetest);

        // when -  action or the behaviour that we are going test
        Atendente savedAttendant = atendenteRepositoryRepository.save(atendente);

        System.out.println(savedAttendant);
        // then - verify the output
        assertThat(savedAttendant).isNotNull();
    }


    @DisplayName("JUnit test for saveAttendant method which throws exception")
    @Test
    public void givenExistingId_whenSaveAttendant_thenThrowsException(){
        // given - precondition or setup
        given(atendenteRepositoryRepository.findById(atendente.getId()))
                .willReturn(Optional.of(atendente));

        System.out.println(atendenteRepositoryRepository);
        System.out.println(undetest);

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFound.class, () -> {
            atendenteRepositoryRepository.save(atendente);
        });

        // then
        verify(atendenteRepositoryRepository, never()).save(any(Atendente.class));
    }

    // JUnit test for getAllAttendant method
    @DisplayName("JUnit test for getAllAttendants method")
    @Test
    public void givenAttendantList_whenGetAllAttendant_thenReturnAttendantList(){
        // given - precondition or setup
        Atendente atendente1 = Atendente.builder()
                        .nomeAtendente("Ronaldinho")
                        .senha("213")
                        .build();

        given(atendenteRepositoryRepository.findAll()).willReturn(List.of(atendente,atendente1));

        // when -  action or the behaviour that we are going test
        List<AtendenteDTO> atendentes = undetest.getAll();

        // then - verify the output
        assertThat(atendentes).isNotNull();
        assertThat(atendentes.size()).isEqualTo(2);
    }

    // JUnit test for getAllAttendant method
    @DisplayName("JUnit test for getAllAttendants method (negative scenario)")
    @Test
    public void givenEmptySaleList_whenGetAllSales_thenReturnEmptySalesList(){
        // given - precondition or setup
        given(atendenteRepositoryRepository.findAll()).willReturn(Collections.emptyList());

        // when -  action or the behaviour that we are going test
        List<AtendenteDTO> atendentesList = undetest.getAll();

        // then - verify the output
        assertThat(atendentesList).isEmpty();
        assertThat(atendentesList.size()).isEqualTo(0);
    }

    // JUnit test for getAttendantById method
    @DisplayName("JUnit test for getAttendantById method")
    @Test
    public void givenSaleId_whenGetSaleById_thenReturnSaleObject(){
        // given
        given(atendenteRepositoryRepository.findById(1L)).willReturn(Optional.of(atendente));

        // when
        AtendenteDTO atendenteDTOsalvo = undetest.getById(atendente.getId()).get();

        // then
        assertThat(atendenteDTOsalvo).isNotNull();

    }

    // JUnit test for updateAttendant method
    @DisplayName("JUnit test for updateAttendant method")
    @Test
    public void givenSalesObject_whenUpdateSale_thenReturnUpdatedSale(){
        // given - precondition or setup
        given(atendenteRepositoryRepository.save(atendente)).willReturn(atendente);
        atendente.setNomeAtendente("FALCHI");
        atendente.setSenha("pass");

        // when -  action or the behaviour that we are going test
        AtendenteDTO updatedAttendant = undetest.update(utils.mapAtendenteToAtendenteDTO(atendente) , atendente.getId());

        // then - verify the output
        assertThat(updatedAttendant.getNomeAtendente()).isEqualTo("FALCHI");
    }

    // JUnit test for deleteAttendant method
    @DisplayName("JUnit test for deleteAttendant method")
    @Test
    public void givenSaleId_whenDeleteSale_thenNothing(){
        // given - precondition or setup
        long atendenteId = 1L;

        willDoNothing().given(atendenteRepositoryRepository).deleteById(atendenteId);

        // when -  action or the behaviour that we are going test
        undetest.delete(atendenteId);

        // then - verify the output
        verify(atendenteRepositoryRepository, times(1)).deleteById(atendenteId);
    }

}
