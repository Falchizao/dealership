package com.utfpr.concessionaria.web;

import com.utfpr.concessionaria.repositores.ClienteRepository;
import com.utfpr.concessionaria.view.controllers.ClienteController;
import com.utfpr.concessionaria.view.entities.Cliente;
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
@WebMvcTest(controllers = ClienteController.class)
public class ClienteTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return all clients")
    public void find_all_clients() throws Exception {
        Cliente cliente = Cliente.builder()
                .nomeCliente("Valcir")
                .emailCliente("Valcir@gmail.com")
                .telefoneCliente("99999-9999")
                .cpfCliente("34534231405")
                .enderecoCliente("TesteEndereco")
                .build();

        List<Cliente> listClientes = List.of(cliente);
        when(clienteRepository.findAll()).thenReturn(listClientes);
        this.mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Valcir@gmail.com")));
    }
}