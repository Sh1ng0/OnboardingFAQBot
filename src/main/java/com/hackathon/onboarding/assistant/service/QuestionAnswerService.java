package com.hackathon.onboarding.assistant.service;

import com.hackathon.onboarding.assistant.logger.ServiceLogEvent;
import com.hackathon.onboarding.assistant.model.QuestionAnswer;
import com.hackathon.onboarding.assistant.repository.QuestionAnswerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio principal de la aplicación.
 * Contiene la lógica de negocio para encontrar respuestas a las preguntas de los usuarios.
 */
@Service
public class QuestionAnswerService {

    private final QuestionAnswerRepository repository;

    private static final Logger log = LoggerFactory.getLogger(QuestionAnswerService.class);

    // Definimos un umbral mínimo de puntuación para considerar una coincidencia válida.
    // Evita falsos positivos con palabras muy comunes. Puntuación de 2 (dos palabras en común) es un buen comienzo.
    private static final int MINIMUM_SCORE_THRESHOLD = 2;


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
    // Usamos un Optional para evitar null pointers, la cnosola se encargará de gestionar lo que pasa si el Optional está empty()
    public Optional<String> findAnswerFor(String userQuestion) {
        ServiceLogEvent.SEARCH_STARTED.log(log, userQuestion);

        // Obtener de la db
        List<QuestionAnswer> candidates = repository.findAll();
        ServiceLogEvent.CANDIDATES_FOUND.log(log, candidates.size());

        // Normalización del string y tokenización del mismo
        Set<String> userTokens = getTokens(userQuestion);

        // Este record aúna un candidato de respuesta con su puntuación

        record Match(QuestionAnswer candidate, int score) {}

        // Procesamos los candidatos para encontrar el mejor.
        // De nuevo, usamos Set para evitar duplicados "Vull vacances! vacances, sí redimoni!" (Solo guarda "vacances" una vez)
        Optional<Match> bestMatchOptional = candidates.stream()
                .map(candidate -> {
                    Set<String> candidateTokens = getTokens(candidate.getQuestion());
                    Set<String> intersection = new java.util.HashSet<>(candidateTokens);
                    intersection.retainAll(userTokens);
                    return new Match(candidate, intersection.size());
                })
                .filter(match -> match.score() >= MINIMUM_SCORE_THRESHOLD)
                .max(Comparator.comparingInt(Match::score));

        // Damos con una respuesta adecuada a la pregunta, o no?
        if (bestMatchOptional.isPresent()) {
            Match bestMatch = bestMatchOptional.get();
            ServiceLogEvent.SEARCH_SUCCESSFUL.log(log, bestMatch.candidate().getId(), bestMatch.score());
            return Optional.of(bestMatch.candidate().getAnswer());
        } else {
            ServiceLogEvent.SEARCH_FAILED.log(log, userQuestion);
            return Optional.empty();
        }
    }

    /**
     * Normaliza y tokeniza un texto para prepararlo para la comparación.
     * @param text El texto a procesar.
     * @return Un conjunto de palabras (tokens) normalizadas.
     */
    private Set<String> getTokens(String text) {
        if (text == null || text.isBlank()) {
            return Set.of();
        }

        String normalized = text.toLowerCase();

        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        normalized = normalized.replaceAll("\\p{Punct}", "");

        return Arrays.stream(normalized.split("\\s+"))
                .filter(token -> !token.isEmpty())
                .collect(Collectors.toSet());
    }

    // CONTROLLER STUFF

    public List<QuestionAnswer> findAll() {
        return repository.findAll();
    }

    public QuestionAnswer add(QuestionAnswer newQuestionAnswer) {
        // Maybe vaildeish goes here
        return repository.save(newQuestionAnswer);
    }
}