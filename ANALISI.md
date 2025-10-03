# Anàlisi Tècnica 2.0: Onboarding Assistant

Este documento detalla la filosofía de diseño, la arquitectura y las decisiones técnicas clave detrás del proyecto "Onboarding Assistant".

---

### 1. Filosofía de Diseño y Comportamiento del Bot

El núcleo del asistente no es una simple consulta a una base de datos, sino un **parser de intenciones** inspirado en los sistemas de diálogo de las aventuras gráficas clásicas de Sierra, como **King's Quest**. El objetivo no es forzar al usuario a adivinar la pregunta exacta, sino interpretar su lenguaje natural para encontrar la respuesta más relevante.

Este enfoque permite al sistema manejar con robustez entradas del mundo real, demostrando una gran flexibilidad ante:
* **Lenguaje coloquial y emocional:** Frases como "quiero saber X ahora mismo" o el uso de emoticonos como `>=(` son procesadas correctamente.
* **Abundancia de *stop words*:** El sistema aísla los términos importantes en frases como "y entonces quiero saber lo de mis vacaciones".
* **Singulares y Plurales:** Una búsqueda por "vacaciones" encontrará correctamente la entrada "vacación", gracias a un *stemming* simple.

#### **El Algoritmo de Búsqueda**
1.  **Normalización:** La pregunta del usuario y las candidatas se estandarizan (minúsculas, sin acentos ni puntuación).
2.  **Tokenización y Filtrado:** El texto se descompone en *tokens* (palabras), eliminando las *stop words* para aislar la esencia de la consulta.
3.  **Puntuación (*Scoring*):** La relevancia se calcula por la intersección de *tokens* entre la pregunta del usuario y cada candidata.
4.  **Selección y Umbral:** Se elige el candidato con la puntuación más alta.

#### **Decisión Clave: Umbral=1 + *Stop Words* = UX Natural**
Se tomó la decisión estratégica de usar un **umbral de confianza mínimo de 1**, permitiendo que una sola palabra clave bien identificada sea suficiente para encontrar una respuesta. Esta aparente pérdida de precisión se compensa con una lista rica y bien definida de *stop words*. El resultado es una **experiencia de usuario (UX) mucho más fluida y natural**, donde consultas directas como "nómina" o "teletrabajo" funcionan a la perfección.

---

### 2. Arquitectura y Decisiones Técnicas

El proyecto se sustenta en una arquitectura por capas clásica, garantizando la separación de responsabilidades y la mantenibilidad.

* **Stack Tecnológico:**
    * **Lenguaje:** Java 21
    * **Framework:** Spring Boot 3
    * **Persistencia:** Spring Data JPA, Hibernate, H2 Database
    * **Build/Test:** Maven, JUnit 5, Mockito
    * **API Docs:** SpringDoc OpenAPI (Swagger)

#### **Decisiones de Diseño Clave**

1.  **`record` vs. `class`: Una Decisión Pragmática bajo Presión**
    Inicialmente, se modeló la entidad `QuestionAnswer` como un **`record` de Java** por su concisión e inmutabilidad. Sin embargo, durante la integración, surgieron problemas de compatibilidad persistentes entre la versión de Hibernate y la instanciación de `records` como entidades JPA. En lugar de perder un tiempo valioso en una depuración incierta, se tomó la **decisión pragmática de refactorizar la entidad a una `clase` tradicional** con Lombok. Esta acción, aunque se alejaba de la solución teóricamente más "elegante", garantizó la estabilidad, desbloqueó el desarrollo y demostró una capacidad clave: **resolver problemas y cumplir objetivos dentro de una fecha límite**.

2.  **Uso de `Optional<String>` para Separar Responsabilidades**
    El servicio de búsqueda devuelve un `Optional`. Esta no es una decisión trivial; refuerza una **clara separación de responsabilidades**. El servicio se limita a informar si ha encontrado (o no) una respuesta. Es la capa de presentación (`consola` o `controller`) la que decide cómo comunicar esa ausencia al usuario final (con un mensaje amigable, un código HTTP 404, etc.).

3.  **Logging Semántico y Estructurado**
    Se diseñó un patrón de logging a medida (`Loggable` + `enum`) para definir eventos de forma estructurada. Esto centraliza los mensajes, sigue el principio DRY (*Don't Repeat Yourself*) y hace el código del servicio mucho más limpio y fácil de mantener.

4.  **Estrategia de Testing Dual**
    Se implementaron tanto tests unitarios con **Mockito** para aislar y verificar la lógica de negocio del servicio, como tests de integración con **`@SpringBootTest` y `MockMvc`** para validar el flujo completo de la API REST, desde la petición HTTP hasta la base de datos.

---

### 3. Consideraciones para Mantenimiento Futuro

La arquitectura está preparada para crecer de forma ordenada:

* **Evolución del Motor de Búsqueda:** El algoritmo de *scoring* está encapsulado. Podría ser sustituido por un motor más avanzado (ej. Elasticsearch, o librerías de NLP como Apache OpenNLP) sin afectar al resto de la aplicación.
* **Extensibilidad a Nuevos Canales:** La lógica de negocio es agnóstica al canal. Añadir un bot para Slack o una interfaz web solo requeriría crear un nuevo componente en la capa de presentación que consuma el `QuestionAnswerService` existente.
* **Internacionalización (i18n):** Los mensajes de respuesta del bot están centralizados, lo que facilitaría su adaptación a múltiples idiomas.