package com.hackathon.onboarding.assistant.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Representa una entrada de la base de conocimiento (una pregunta y su respuesta).
 * Es una entidad de JPA y se modela como un record inmutable.
 */
@Entity
public record QuestionAnswer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id,
        String question,
        String answer
) {
    // For JPA
    public QuestionAnswer() {
        this(null, null, null);
    }
}