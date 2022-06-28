package com.utfpr.concessionaria.web;

import com.utfpr.concessionaria.repositores.CarroRepository;
import com.utfpr.concessionaria.repositores.VendaRepository;
import com.utfpr.concessionaria.view.controllers.CarroController;
import com.utfpr.concessionaria.view.entities.Carro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CarroController.class)
public class VendaTest {
        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private VendaRepository vendaRepository;

        @BeforeEach
        public void setUp() {
                vendaRepository.deleteAll();
        }

        @Test
        @DisplayName("Should return all sales")
        public void find_all_sales() throws Exception {
                Carro carroTeste = Carro.builder()
                        .modelo("Sedan")
                        .marca("Ford")
                        .cor("Cinza")
                        .placa("ABC1234")
                        .chassi("123456789012345")
                        .valor(30000.00)
                        .build();

                List<Carro> listCarros = List.of(carroTeste);
//                when(carroRepository.findAll()).thenReturn(listCarros);
                this.mockMvc.perform(get("/api/carros"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("Sedan")));
        }

}