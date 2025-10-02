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
        // Creamos una nueva instancia del servicio antes de cada test,
        // inyectando nuestro repositorio mockeado.
        service = new QuestionAnswerService(mockRepository);
    }

    @Test
    @DisplayName("Debe devolver la respuesta correcta cuando existe una coincidencia clara")
    void shouldReturnCorrectAnswer_whenClearMatchExists() {

        // ARRANGE
        String userQuestion = "com demano les meves vacances";
        List<QuestionAnswer> candidates = List.of(
                new QuestionAnswer(1L, "Com solicito vacances?", "Enviando un correo a RRHH."),
                new QuestionAnswer(2L, "Quin es l'horari de l'empresa?", "El horario es de 9 a 18h.")
        );
        // Configuramos el mock para que devuelva nuestros candidatos
        when(mockRepository.findAll()).thenReturn(candidates);

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        assertThat(result)
                .isPresent()
                .contains("Enviando un correo a RRHH.");
    }


    @Test
    @DisplayName("Debe devolver un Optional vacío si ninguna pregunta coincide")
    void shouldReturnEmpty_whenNoMatchFound() {
        // ARRANGE
        List<QuestionAnswer> candidates = List.of(
                new QuestionAnswer(1L, "Com solicito vacances?", "Enviando un correo a RRHH."),
                new QuestionAnswer(2L, "Quin es l'horari de l'empresa?", "El horario es de 9 a 18h.")
        );
        String userQuestion = "hablame del tiempo que hace fuera"; // Una pregunta sin palabras en común


        when(mockRepository.findAll()).thenReturn(candidates);

        // ACT
        Optional<String> result = service.findAnswerFor(userQuestion);

        // ASSERT
        assertThat(result).isEmpty();
    }


    @Test
    @DisplayName("Debe devolver un Optional vacío si la puntuación no supera el umbral")
    void shouldReturnEmpty_whenMatchScoreIsBelowThreshold() {
        // ARRANGE
        List<QuestionAnswer> candidates = List.of(
                new QuestionAnswer(1L, "Com solicito vacances?", "Enviando un correo a RRHH.")
        );
        String userQuestion = "hablemos de las vacances";
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