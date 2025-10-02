package com.hackathon.onboarding.assistant.logger;



import org.slf4j.Logger;

import java.text.MessageFormat;

/**
 * Un contrato para crear eventos de log estructurados y basados en enums.
 * <p>
 * Esta interfaz centraliza la lógica de logging en un método por defecto {@code log},
 * asegurando que todos los enums de eventos de log en la aplicación se comporten
 * de forma consistente sin duplicar código. Los enums que implementen esta interfaz
 * son responsables únicamente de definir los eventos de log, sus niveles de
 * severidad y sus plantillas de mensaje.
 * <p>
 * Este enfoque promueve una alta cohesión (cada servicio puede tener sus propios
 * eventos de log) mientras se adhiere al principio DRY (Don't Repeat Yourself)
 * para el mecanismo de logging en sí.
 */
public interface Loggable {

    /**
     * Define el nivel de severidad de un evento de log, mapeando directamente a los niveles de SLF4J.
     * Este enum anidado es parte de la interfaz para que sea accesible por todos los enums implementadores.
     */
    enum LogLevel {
        DEBUG, INFO, WARN, ERROR
    }


    /**
     * Devuelve el nivel de severidad del evento de log específico.
     *
     * @return El {@link LogLevel} del evento.
     */
    LogLevel getLevel();

    /**
     * Devuelve la plantilla de mensaje en crudo y sin formatear para el evento de log.
     *
     * @return El String del mensaje, típicamente con placeholders como {0}, {1}, etc.
     */
    String getMessageTemplate();

    /**
     * Escribe este evento en el logger proporcionado si el nivel de log correspondiente está habilitado.
     * <p>
     * Este método por defecto contiene la lógica de logging principal. Primero realiza
     * una comprobación "guard clause" de bajo coste (ej., {@code logger.isInfoEnabled()}) para evitar
     * el trabajo costoso de formatear el String del mensaje si el nivel de log está
     * deshabilitado en la configuración de logging.
     *
     * @param logger La instancia del logger SLF4J de la clase que llama.
     * @param params Los datos contextuales que se insertarán en la plantilla del mensaje.
     */
    default void log(Logger logger, Object... params) {
        switch (this.getLevel()) {
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
            case INFO:
                if (logger.isInfoEnabled()) {
                    // Versión corregida: solo un "getMessageTemplate()"
                    logger.info(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
            case WARN:
                if (logger.isWarnEnabled()) {
                    logger.warn(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
            case ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(MessageFormat.format(this.getMessageTemplate(), params));
                }
                break;
        }
}

}