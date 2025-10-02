package com.hackathon.onboarding.assistant.logger;



public enum LogEvent implements Loggable {

    // --- Eventos de Log para nuestro Servicio ---
    SEARCH_STARTED(LogLevel.INFO, "Iniciando búsqueda de respuesta para la pregunta: ''{0}''."),
    CANDIDATES_FOUND(LogLevel.DEBUG, "Encontrados {0} candidatos en la base de datos."),
    SEARCH_SUCCESSFUL(LogLevel.INFO, "Búsqueda finalizada. Mejor candidato encontrado con ID {0} y puntuación {1}."),
    SEARCH_FAILED(LogLevel.INFO, "Búsqueda finalizada. No se encontró ningún candidato adecuado para la pregunta: ''{0}''.");

    private final LogLevel level;
    private final String messageTemplate;



    LogEvent(LogLevel level, String messageTemplate) {
        this.level = level;
        this.messageTemplate = messageTemplate;
    }

    @Override
    public LogLevel getLevel() {
        return this.level;
    }

    @Override
    public String getMessageTemplate() {
        return this.messageTemplate;
    }


}
