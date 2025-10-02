package com.hackathon.onboarding.assistant.controller;


import com.hackathon.onboarding.assistant.model.QuestionAnswer;
import com.hackathon.onboarding.assistant.service.QuestionAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
@Tag(name = "Knowledge Base API", description = "Endpoints para gestionar la base de conocimiento del asistente")
public class QuestionAnswerController {

    private final QuestionAnswerService service;

    public QuestionAnswerController(QuestionAnswerService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obtener toda la base de conocimiento", description = "Devuelve una lista con todas las preguntas y respuestas almacenadas.")
    public ResponseEntity<List<QuestionAnswer>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @Operation(summary = "Añadir una nueva entrada", description = "Añade una nueva pareja de pregunta/respuesta a la base de conocimiento.")
    public ResponseEntity<QuestionAnswer> add(@RequestBody QuestionAnswer newQuestionAnswer) {
        QuestionAnswer saved = service.add(newQuestionAnswer);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}