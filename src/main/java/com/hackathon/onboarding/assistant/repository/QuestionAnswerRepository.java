package com.hackathon.onboarding.assistant.repository;

import com.hackathon.onboarding.assistant.model.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder a los datos de QuestionAnswer.
 * Al extender JpaRepository, Spring Data JPA nos proporciona automáticamente
 * la implementación de los métodos CRUD básicos.
 */
@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

}
