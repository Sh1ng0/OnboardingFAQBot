package com.hackathon.onboarding.assistant.service;

import com.hackathon.onboarding.assistant.model.QuestionAnswer;
import com.hackathon.onboarding.assistant.repository.QuestionAnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Tests unitarios para QuestionAnswerService.
 * Estas pruebas verifican la lógica de negocio en aislamiento, utilizando un mock
 * para la capa de repositorio y sin levantar el contexto de Spring.
 */
@ExtendWith(MockitoExtension.class)
class QuestionAnswerServiceTest {

    @Mock
    private QuestionAnswerRepository mockRepository;

    private QuestionAnswerService service;

    @BeforeEach
    void setUp() {
        service = new QuestionAnswerService(mockRepository);
    }

    @Test
    @DisplayName("Debe devolver la respuesta correcta cuando la coincidencia es clara")
    void shouldReturnCorrectAnswer_whenClearMatchExists() {

        // ARRANGE
        QuestionAnswer qa1 = new QuestionAnswer("¿Cómo solicito las vacaciones?", "Respuesta sobre vacaciones.");
        qa1.setId(1L);
        List<QuestionAnswer> candidates = List.of(qa1);
        when(mockRepository.findAll()).thenReturn(candidates);
        String userQuestion = "cómo solicito vacaciones";

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        assertThat(result).isPresent().contains("Respuesta sobre vacaciones.");
    }


    @Test
    @DisplayName("Debe encontrar una respuesta ignorando las 'stop words'")
    void shouldFindAnswer_whenQueryContainsStopWords() {

        // ARRANGE
        QuestionAnswer qa1 = new QuestionAnswer("¿Cuál es la política de teletrabajo?", "Respuesta de teletrabajo.");
        qa1.setId(1L);
        List<QuestionAnswer> candidates = List.of(qa1);
        when(mockRepository.findAll()).thenReturn(candidates);

        String userQuestion = "¿cuál es la política que tenéis de teletrabajo?";

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        // El test debe pasar porque las palabras clave "política" y "teletrabajo" coinciden (score = 2)
        assertThat(result).isPresent().contains("Respuesta de teletrabajo.");
    }

    @Test
    @DisplayName("Debe encontrar una respuesta aunque se use singular/plural distinto (Stemming)")
    void shouldFindAnswer_whenQueryUsesSingularAndDbHasPlural() {

        // ARRANGE
        QuestionAnswer qa1 = new QuestionAnswer("¿Cómo funcionan las bajas médicas?", "Respuesta sobre bajas.");
        qa1.setId(1L);
        List<QuestionAnswer> candidates = List.of(qa1);
        when(mockRepository.findAll()).thenReturn(candidates);
        // El usuario pregunta en singular ("baja", "médica") y la BBDD está en plural ("bajas", "médicas")
        String userQuestion = "Información sobre la baja médica";

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        // El test debe pasar porque el stemming reduce "bajas" a "baja" y "médicas" a "médica" (score = 2)
        assertThat(result).isPresent().contains("Respuesta sobre bajas.");
    }

    @Test
    @DisplayName("Debe devolver que 1 es un Optional lleno, por que hemos cambiado el treshold a 1 (perfecto para el umbral)")
    void shouldReturnEmpty_whenMatchScoreIsBelowThreshold() {

        // ARRANGE
        QuestionAnswer qa1 = new QuestionAnswer("¿Cómo solicito las vacaciones?", "Respuesta sobre vacaciones.");
        qa1.setId(1L);
        List<QuestionAnswer> candidates = List.of(qa1);
        when(mockRepository.findAll()).thenReturn(candidates);
        // La única palabra clave en común es "vacaciones", score = 1, umbral = 2
        String userQuestion = "info sobre vacaciones";

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        assertThat(result).isPresent().contains("Respuesta sobre vacaciones.");
    }

    @Test
    @DisplayName("Debe devolver un Optional vacío si ninguna palabra clave coincide")
    void shouldReturnEmpty_whenNoMatchFound() {

        // ARRANGE
        QuestionAnswer qa1 = new QuestionAnswer("¿Cómo solicito las vacaciones?", "Respuesta sobre vacaciones.");
        qa1.setId(1L);
        List<QuestionAnswer> candidates = List.of(qa1);
        String userQuestion = "dónde está la impresora";
        when(mockRepository.findAll()).thenReturn(candidates);

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debe devolver un Optional vacío cuando la BBDD está vacía")
    void shouldReturnEmpty_whenDatabaseIsEmpty() {
        // ARRANGE
        String userQuestion = "cualquier pregunta";
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Debe devolver un Optional vacío cuando el input del usuario es nulo")
    void shouldReturnEmpty_whenUserInputIsNull() {
        // ARRANGE
        String userQuestion = null;

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        assertThat(result).isEmpty();
    }



}