package com.mercadolibre.challenge.controller;

import com.mercadolibre.challenge.entities.DnaHistory;
import com.mercadolibre.challenge.repositories.DnaHistoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MutantValidationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DnaHistoryRepository repository;

    @Test
    public void validateHumanDNAVO() throws Exception {
        when(repository.findByDna(anyString())).thenReturn(null);
        when(repository.save(any())).thenReturn(null);

        String request = "{\n"+
                "\"dna\": " +
                "        [\"AAGTT\",\n" +
                "         \"AAGGG\",\n" +
                "         \"ATGTA\",\n" +
                "         \"GGAAT\",\n" +
                "         \"GTCCT\"" +
                "        ]\n" +
                "}";

        mvc.perform(post("/mutant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request.getBytes()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void validateHumanDNAHO() throws Exception {
        when(repository.findByDna(anyString())).thenReturn(null);
        when(repository.save(any())).thenReturn(null);

        String request = "{\n"+
                "\"dna\": " +
                "        [\"AAAAT\",\n" +
                "         \"TAGGG\",\n" +
                "         \"TTGTA\",\n" +
                "         \"GGTAT\",\n" +
                "         \"GTCTT\"" +
                "        ]\n" +
                "}";

        mvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    public void validateMutantDNAVO() throws Exception {
        when(repository.findByDna(anyString())).thenReturn(null);
        when(repository.save(any())).thenReturn(null);

        String request = "{\n"+
                "\"dna\": " +
                "        [\"AAGTT\",\n" +
                "         \"AAGGG\",\n" +
                "         \"ATGTA\",\n" +
                "         \"AGAAT\",\n" +
                "         \"GTCCT\"" +
                "        ]\n" +
                "}";

        mvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    public void validateExistsMutantDNA() throws Exception {
        when(repository.findByDna(anyString())).thenReturn(new DnaHistory(1L, "AAGTT-AAGGG", true));
        when(repository.save(any())).thenReturn(null);

        String request = "{\n"+
                "\"dna\": " +
                "        [\"AAGTT\",\n" +
                "         \"AAGGG\",\n" +
                "         \"ATGTA\",\n" +
                "         \"AGAAT\",\n" +
                "         \"GTCCT\"" +
                "        ]\n" +
                "}";

        mvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    public void validateDNAInvalidStructure() throws Exception {
        when(repository.findByDna(anyString())).thenReturn(null);
        when(repository.save(any())).thenReturn(null);

        String request = "{\n"+
                "\"dna\": " +
                "        [\"AAGTT\",\n" +
                "           null,\n" +
                "         \"ATGTA\",\n" +
                "         \"AGAAT\",\n" +
                "         \"GTCCT\"" +
                "        ]\n" +
                "}";

        mvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getBytes()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Formato invalido, no se permiten objetos nulos en la información del adn"));
    }

    @Test
    public void validateDNAInvalidStructureTest2() throws Exception {
        when(repository.findByDna(anyString())).thenReturn(null);
        when(repository.save(any())).thenReturn(null);

        String request = "{\n"+
                "\"dna\": []" +
                "}";

        mvc.perform(post("/mutant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request.getBytes()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("dna is not empty"));
    }

    @Test
    public void validateStats() throws Exception {
        when(repository.getCountByState(true)).thenReturn(40.0);
        when(repository.getCountByState(false)).thenReturn(100.0);

        mvc.perform(
                get("/stats/")
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.40));
    }

}
