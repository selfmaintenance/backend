package br.com.selfmaintenance.presentation.http.controllers;

import br.com.selfmaintenance.app.services.procedimento.ProcedimentoService;
import br.com.selfmaintenance.domain.entities.procedimento.Procedimento;
import br.com.selfmaintenance.domain.entities.recurso.Recurso;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ProcedimentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcedimentoService procedimentoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Procedimento procedimento;

    @BeforeEach
    public void setUp() {
        procedimento = new Procedimento();
        procedimento.setNome("Troca de Óleo");
        procedimento.setId(1L);
    }

    // Teste para criar um novo Procedimento
    @Test
    @WithMockUser
    public void testCriarProcedimento() throws Exception {
        when(procedimentoService.criarProcedimento(any(Procedimento.class))).thenReturn(procedimento);

        mockMvc.perform(post("/api/procedimentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(procedimento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Troca de Óleo"))
                .andExpect(jsonPath("$.id").value(1L));

        verify(procedimentoService, times(1)).criarProcedimento(any(Procedimento.class));
    }

    // Teste para buscar todos os Procedimentos
    @Test
    public void testListarProcedimentos() throws Exception {
        when(procedimentoService.listarProcedimentos()).thenReturn(Arrays.asList(procedimento));

        mockMvc.perform(get("/api/procedimentos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Troca de Óleo"));

        verify(procedimentoService, times(1)).listarProcedimentos();
    }

    // Teste para buscar um Procedimento por ID
    @Test
    public void testBuscarProcedimentoPorId() throws Exception {
        when(procedimentoService.buscarProcedimentoPorId(1L)).thenReturn(Optional.of(procedimento));

        mockMvc.perform(get("/api/procedimentos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Troca de Óleo"));

        verify(procedimentoService, times(1)).buscarProcedimentoPorId(1L);
    }

    // Teste para atualizar um Procedimento
    @Test
    public void testAtualizarProcedimento() throws Exception {
        when(procedimentoService.atualizarProcedimento(anyLong(), any(Procedimento.class)))
                .thenReturn(Optional.of(procedimento));

        mockMvc.perform(put("/api/procedimentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(procedimento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Troca de Óleo"));

        verify(procedimentoService, times(1)).atualizarProcedimento(anyLong(), any(Procedimento.class));
    }

    // Teste para deletar um Procedimento
    @Test
    public void testDeletarProcedimento() throws Exception {
        when(procedimentoService.deletarProcedimento(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/procedimentos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(procedimentoService, times(1)).deletarProcedimento(1L);
    }

    // Teste para adicionar um recurso a um Procedimento
    @Test
    public void testAdicionarRecurso() throws Exception {
        Recurso recurso = new Recurso();
        recurso.setNome("Óleo de Motor");
        when(procedimentoService.adicionarRecurso(anyLong(), any(Recurso.class)))
                .thenReturn(Optional.of(procedimento));

        mockMvc.perform(post("/api/procedimentos/1/recursos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recurso)))
                .andExpect(status().isOk());

        verify(procedimentoService, times(1)).adicionarRecurso(anyLong(), any(Recurso.class));
    }

}
