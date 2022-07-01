package com.utfpr.concessionaria.services;

import static org.assertj.core.api.Assertions.assertThat;
import com.utfpr.concessionaria.dto.CarroDTO;
import com.utfpr.concessionaria.modelException.exception.ResourceNotFound;
import com.utfpr.concessionaria.repositores.CarroRepository;
import com.utfpr.concessionaria.services.CRUD.CarrosCRUDservice;
import com.utfpr.concessionaria.utils.Utils;
import com.utfpr.concessionaria.view.entities.Carro;
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
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class CarroServiceTest {

    @Mock
    private Utils utils;

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private CarrosCRUDservice undetest;

    private Carro carro;

    @BeforeEach
    public void setup(){
        carro = Carro.builder()
                .modelo("Sedan")
                .marca("Ford")
                .cor("Cinza")
                .ano(new Date())
                .placa("ABC1234")
                .chassi("123456789012345")
                .valor(30000.00)
                .build();
    }

    // JUnit test for saveVenda method
    @DisplayName("JUNIT for Save Car")
    @Test
    public void givenCarObject_whenSaveCar_thenReturnCarObject(){
        //Arrange --Preparation
        given(carroRepository.findById(carro.getId()))
                .willReturn(Optional.empty());

        given(carroRepository.save(carro)).willReturn(carro);

        System.out.println(carroRepository);
        System.out.println(undetest);

        //Act --Action
        Carro savedCar = carroRepository.save(carro);

        System.out.println(savedCar);

        //Assert -- Confirmation
        assertThat(savedCar).isNotNull();
    }


    @DisplayName("JUnit test for saveCar method which throws exception")
    @Test
    public void givenExistingId_whenSaveCar_thenThrowsException(){
        //Arrange --Preparation
        given(carroRepository.findById(carro.getId()))
                .willReturn(Optional.of(carro));

        System.out.println(carroRepository);
        System.out.println(undetest);

        //Act --Action
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFound.class, () -> {
            carroRepository.save(carro);
        });

        //Assert -- Confirmation()).save(any(Carro.class));
    }

    // JUnit test for getAllCars method
    @DisplayName("JUnit test for getAllCars method")
    @Test
    public void givenCarsList_whenGetAllCars_thenReturnCarsList(){
        //Arrange --Preparation
        Carro carro1 = Carro.builder()
                .modelo("Eletrico")
                .marca("Tesla")
                .cor("Preto")
                .ano(new Date())
                .placa("ABC1434")
                .chassi("123456784022345")
                .valor(40000.00)
                .build();

        given(carroRepository.findAll()).willReturn(List.of(carro,carro1));

        //Act --Action
        List<CarroDTO> carros = undetest.getAll();

        //Assert -- Confirmation
        assertThat(carros).isNotNull();
        assertThat(carros.size()).isEqualTo(2);
    }

    // JUnit test for getAllCars method
    @DisplayName("JUnit test for getAllCars method (negative scenario)")
    @Test
    public void givenEmptyCarList_whenGetAllCars_thenReturnEmptyCarsList(){
        //Arrange --Preparation

        given(carroRepository.findAll()).willReturn(Collections.emptyList());

        //Act --Action
        List<CarroDTO> carrosList = undetest.getAll();

        //Assert -- Confirmation
        assertThat(carrosList).isEmpty();
        assertThat(carrosList.size()).isEqualTo(0);
    }

    // JUnit test for getCarById method
    @DisplayName("JUnit test for getCarById method")
    @Test
    public void givenCarId_whenGetCarById_thenReturnCarObject(){
        //Arrange --Preparation
        given(carroRepository.findById(1L)).willReturn(Optional.of(carro));

        // when
        CarroDTO savedSale = undetest.getById(carro.getId()).get();

        //Assert -- Confirmation

    }

    // JUnit test for updateCar method
    @DisplayName("JUnit test for updateCars method")
    @Test
    public void givenCarsObject_whenUpdateCar_thenReturnUpdatedCar(){
        //Arrange --Preparation
        given(carroRepository.save(carro)).willReturn(carro);
        carro.setCor("Laranja");

        //Act --Action
        CarroDTO updatedCar = undetest.update((utils.mapCarToCarDTO(carro)), 2 , carro.getId());

        //Assert -- Confirmation
        assertThat(updatedCar.getCor()).isEqualTo("Laranja");
    }

    // JUnit test for deleteCar method
    @DisplayName("JUnit test for deleteCar method")
    @Test
    public void givenCarId_whenDeleteCar_thenNothing(){
        //Arrange --Preparation
        long carroId = 1L;

        willDoNothing().given(carroRepository).deleteById(carroId);

        //Act --Action
        undetest.delete(carroId);

        //Assert -- Confirmation
        verify(carroRepository, times(1)).deleteById(carroId);
    }

}
