package com.hackathon.onboarding.assistant.service;

import com.hackathon.onboarding.assistant.repository.QuestionAnswerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio principal de la aplicación.
 * Contiene la lógica de negocio para encontrar respuestas a las preguntas de los usuarios.
 */
@Service
public class QuestionAnswerService {

    private final QuestionAnswerRepository repository;

    /**
     * Inyecta el repositorio de preguntas y respuestas a través del constructor.
     * Esta es la práctica recomendada para la inyección de dependencias en Spring.
     * @param repository El repositorio para acceder a los datos.
     */
    public QuestionAnswerService(QuestionAnswerRepository repository) {
        this.repository = repository;
    }

    /**
     * Busca la respuesta más relevante para una pregunta dada por el usuario.
     *
     * @param userQuestion La pregunta formulada por el usuario.
     * @return Un Optional que contiene la respuesta si se encuentra una coincidencia,
     * o un Optional vacío si no se encuentra ninguna respuesta adecuada.
     */
    public Optional<String> findAnswerFor(String userQuestion) {
        // --- TODO: Implementar la lógica de búsqueda inteligente aquí ---
        // 1. Obtener todas las QuestionAnswer de la base de datos con repository.findAll().
        // 2. Normalizar la pregunta del usuario.
        // 3. Iterar sobre las preguntas de la BBDD, calcular una puntuación de similitud.
        // 4. Encontrar la que tenga la puntuación más alta.
        // 5. Si la puntuación supera un umbral, devolver su 'answer'.
        // 6. Si no, devolver Optional.empty().

        System.out.println("Buscando respuesta para: " + userQuestion);
        System.out.println("Datos del repositorio: " + repository.findAll());

        return Optional.empty(); // De momento, devolvemos vacío.
    }
}