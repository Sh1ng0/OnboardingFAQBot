package com.hackathon.onboarding.assistant.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.onboarding.assistant.model.QuestionAnswer;
import com.hackathon.onboarding.assistant.repository.QuestionAnswerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.notNullValue;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class QuestionAnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QuestionAnswerRepository repository;

    @Test
    @DisplayName("GET /api/knowledge debe devolver todas las entradas y un estado 200 OK")
    void getKnowledge_shouldReturnAllEntries_withStatus200() throws Exception {
        // ARRANGE (No need)

        // ACT & ASSERT

        mockMvc.perform(get("/api/knowledge"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(18)))
                .andExpect(jsonPath("$[0].question", is("¿Cómo solicito las vacaciones?")));
    }

    @Test
    @DisplayName("POST /api/knowledge debe crear una nueva entrada y devolverla con un estado 201 Created")
    void postKnowledge_shouldCreateNewEntry_withStatus201() throws Exception {

        // ARRANGE
        // Creamos el objeto sin id
        QuestionAnswer newQuestionAnswer = new QuestionAnswer("Es una pregunta nueva", "Y esta es su respuesta");

        // Object mapper, para convertir de objeto a JSON
        String jsonPayload = objectMapper.writeValueAsString(newQuestionAnswer);

        // ACT & ASSERT
        mockMvc.perform(post("/api/knowledge")
                        .contentType("application/json") // EL cuerpo es un JSON, as expected
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.question", is("Es una pregunta nueva")))
                .andExpect(jsonPath("$.answer", is("Y esta es su respuesta")));
    }

    // Caso 3: Probar el POST /api/knowledge (con datos inválidos, opcional)


}