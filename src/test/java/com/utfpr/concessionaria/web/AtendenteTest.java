package com.utfpr.concessionaria.web;

import com.utfpr.concessionaria.repositores.AtendenteRepository;
import com.utfpr.concessionaria.view.controllers.AtendenteController;
import com.utfpr.concessionaria.view.entities.Atendente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static com.utfpr.concessionaria.enums.PermissoesAtendente.SEMDESCONTO;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AtendenteController.class)
public class AtendenteTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AtendenteRepository atendenteRepository;

    @Test
    public void find_all_attendants() throws Exception {
        Atendente atendente = Atendente.builder()
                    .nomeAtendente("Valcir")
                    .senha("12345")
                    .permissao(SEMDESCONTO)
                    .build();

        List<Atendente> listAtendentes = List.of(atendente);
        when(atendenteRepository.findAll()).thenReturn(listAtendentes);
        this.mockMvc.perform(get("/api/atendentes"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("Valcir")));
        }

}
