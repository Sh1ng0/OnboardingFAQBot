package com.hackathon.onboarding.assistant.console;

import com.hackathon.onboarding.assistant.service.QuestionAnswerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

/**
 * Componente que gestiona la interfaz de usuario por consola.
 * Se ejecuta automáticamente al arrancar la aplicación gracias a la interfaz CommandLineRunner.
 */
@Component
public class ConsoleRunner implements CommandLineRunner {

    private final QuestionAnswerService service;

    public ConsoleRunner(QuestionAnswerService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("======================================================");
        System.out.println("Bienvenido al Asistente de Onboarding.");
        System.out.println("Escribe tu pregunta o 'salir' para terminar.");
        System.out.println("======================================================");

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String userInput = scanner.nextLine();

                if ("salir".equalsIgnoreCase(userInput) || "exit".equalsIgnoreCase(userInput)) {
                    break;
                }


                Optional<String> answerOptional = service.findAnswerFor(userInput);


                answerOptional.ifPresentOrElse(
                        answer -> System.out.println("Bot: " + answer),
                        () -> System.out.println("Bot: Lo siento, no tengo una respuesta para tu pregunta")
                );
            }
        }

        System.out.println("Hasta pronto!");
    }
}