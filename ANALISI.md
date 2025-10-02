
Autor: Jose Arbizu Rendon


---

### Borrador del `ANALISI.md`

```markdown
# Análisis Técnico: Onboarding Assistant

Este documento detalla la arquitectura, las decisiones de diseño y las consideraciones técnicas del proyecto "Onboarding Assistant".

## 1. Reglas y Comportamiento del Bot

El comportamiento principal del asistente se basa en un sistema de búsqueda por relevancia, diseñado para ser más flexible que una simple coincidencia de texto exacta.

### Algoritmo de Búsqueda

Cuando un usuario introduce una pregunta, el sistema sigue estos pasos:
1.  **Normalización:** La pregunta del usuario y todas las preguntas de la base de conocimiento se "limpian" (se pasan a minúsculas, se eliminan acentos y puntuación).
2.  **Tokenización:** Cada pregunta normalizada se descompone en un conjunto (`Set`) de palabras únicas (tokens).
3.  **Puntuación (Scoring):** Se calcula una puntuación de similitud para cada pregunta de la base de datos contando el número de tokens en común con la pregunta del usuario.
4.  **Selección:** Se selecciona la pregunta con la puntuación más alta. Si esta puntuación no supera un umbral mínimo de confianza (actualmente `2`), se considera que no se ha encontrado una respuesta válida.
5.  **Respuesta:** Si se encuentra un candidato válido, se devuelve su respuesta. Si no, se devuelve un mensaje genérico indicando que no se ha encontrado una solución.

## 2. Estructura de la Base de Conocimiento

La base de conocimiento se persiste en una base de datos en memoria **H2**. Al arrancar la aplicación, se inicializa con un conjunto de datos predefinido a través de un script `data.sql`.

La unidad de conocimiento se modela con la entidad `QuestionAnswer`, que contiene los siguientes campos:
* `id` (Long): Clave primaria autogenerada.
* `question` (String): La pregunta canónica.
* `answer` (String): La respuesta oficial.

## 3. Arquitectura y Decisiones Técnicas

### Arquitectura General

El proyecto sigue una **arquitectura por capas** clásica y robusta, promoviendo una alta cohesión y un bajo acoplamiento:

* **Capa de Presentación (`controller`, `console`):** Responsable de la interacción con el exterior. Incluye el `Controller` para la API REST y el `CommandLineRunner` para la interfaz de terminal.
* **Capa de Servicio (`service`):** Contiene la lógica de negocio principal. El `QuestionAnswerService` implementa el algoritmo de búsqueda y orquesta la comunicación con la capa de persistencia.
* **Capa de Persistencia (`repository`):** Abstrae el acceso a datos. Se utiliza una interfaz de Spring Data JPA (`QuestionAnswerRepository`) para comunicarse con la base de datos H2.

### Stack Tecnológico
* **Lenguaje:** Java 21 (LTS)
* **Framework:** Spring Boot 3
* **Persistencia:** Spring Data JPA con Hibernate y base de datos en memoria H2.
* **Build Tool:** Apache Maven
* **Documentación API:** SpringDoc OpenAPI (Swagger UI)
* **Testing:** JUnit 5, Mockito, Spring Test (MockMvc).

### Decisiones de Diseño Clave

Durante el desarrollo, se tomaron varias decisiones importantes:

1.  **`record` vs. `class` para la Entidad:** Inicialmente se optó por un `record` de Java para la entidad `QuestionAnswer` por su concisión e inmutabilidad. Sin embargo, nos encontramos con un problema de compatibilidad persistente entre la versión de Hibernate, la configuración del proyecto y la instanciación de `records` como entidades JPA. Tras una depuración metódica, se tomó la **decisión pragmática** de refactorizar la entidad a una `clase` tradicional anotada con Lombok. Esto garantizó la estabilidad y compatibilidad con el framework, desbloqueando el desarrollo y demostrando una toma de decisiones orientada a cumplir objetivos bajo una fecha límite.

2.  **Uso de `Optional<String>` en el Servicio:** El servicio de búsqueda devuelve un `Optional` en lugar de un `String` o `null`. Esta decisión refuerza la **separación de responsabilidades**. El servicio informa si ha encontrado un resultado o no, y deja que la capa de presentación (`consola` o `controller`) decida cómo traducir esa ausencia al usuario final (un texto amigable, un código HTTP 404, etc.).

3.  **Estrategia de Testing Dual:** Se implementó una estrategia de dos niveles:
    * **Tests Unitarios:** Para el `QuestionAnswerService`, usando Mockito para aislar la lógica de negocio.
    * **Tests de Integración:** Para el `QuestionAnswerController`, usando `@SpringBootTest` y `MockMvc` para validar el flujo completo de la aplicación, desde la petición HTTP hasta la base de datos.

4.  **Logging Semántico:** Se diseñó un patrón de logging (`Loggable` + `enum`) para centralizar la lógica y definir los eventos de log de forma estructurada, mejorando la mantenibilidad y la legibilidad del código.

## 4. Consideraciones para Mantenimiento Futuro

La arquitectura actual permite futuras expansiones de forma limpia:

* **Nuevos Canales:** Se podría añadir un bot de Slack o Microsoft Teams creando una nueva clase en la capa de presentación que simplemente llame al `QuestionAnswerService` existente.
* **Personalización:** El servicio podría evolucionar para conectarse a otras APIs internas (ej. un servicio de RRHH) y ofrecer respuestas personalizadas (ej. "¿Cuántos días de vacaciones me quedan?").
* **Motor de Búsqueda:** El algoritmo de puntuación actual está encapsulado. Podría ser reemplazado por un motor de búsqueda más potente (como Elasticsearch o una librería de NLP) sin que los clientes del servicio se vean afectados.
* **Internacionalización (i18n):** Los mensajes de respuesta (como el de "respuesta no encontrada")


## Comportamiento del Sistema con Inputs Reales

El sistema demuestra robustez ante:
- **Expresiones emocionales**: `>=(` (filtrado exitoso)
- **Lenguaje natural coloquial**: "quiero X ahora mismo"
- **Stop words abundantes**: "y mis vacaciones"
- **Plurales**: "vacaciones" → matching con entradas en singular

Umbral=1 + STOP_WORDS = Combinación perfecta para UX natural