package com.hackathon.onboarding.assistant.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = "id") // Importante: Basar el equals y hashCode solo en el ID
@Entity
public final class QuestionAnswer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

//        @NotBlank(message = "La pregunta no puede estar vacía.")
        private String question;

//        @NotBlank(message = "La respuesta no puede estar vacía.")
        private String answer;

        /**
         * Constructor de conveniencia para crear nuevas instancias sin ID.
         * Lombok generará el constructor con todos los argumentos y el vacío.
         */
        public QuestionAnswer(String question, String answer) {
                this.question = question;
                this.answer = answer;
        }
}