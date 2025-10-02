# Onboarding Assistant

## Descripción

**Onboarding Assistant** es un asistente conversacional simple, diseñado para responder a las preguntas frecuentes (FAQ) de los nuevos empleados durante su proceso de incorporación (onboarding).

Este proyecto ha sido desarrollado como parte de la prueba técnica de acceso para la hackathon de Octubre de 2025. La aplicación consiste en un servicio backend con una API REST para su gestión y una interfaz de consola para la interacción del usuario.

---

## Requisitos Previos

Para compilar y ejecutar el proyecto, necesitarás:
* **Java JDK 21** o superior.
* **Apache Maven** 3.8 o superior.
* **Git** para clonar el repositorio.

---

## Instalación y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://www.youtube.com/watch?v=44ziZ12rJwU](https://www.youtube.com/watch?v=44ziZ12rJwU)
    cd onboarding-assistant
    ```

2.  **Compilar el proyecto:**
    ```bash
    mvn clean install
    ```

3.  **Ejecutar la aplicación:**
    Puedes ejecutar la aplicación de dos maneras:

    * **A través del plugin de Maven (recomendado):**
        ```bash
        mvn spring-boot:run
        ```
    * **Ejecutando el fichero JAR compilado:**
        ```bash
        java -jar target/onboarding-assistant-0.0.1-SNAPSHOT.jar
        ```
    Una vez ejecutada, la aplicación estará escuchando en el puerto `8090` y la interfaz de consola se activará en el terminal.

---

## Ejemplos de Uso (Consola)

Al arrancar la aplicación, puedes interactuar directamente con el bot. Introduce una pregunta y pulsa Enter. Para salir, escribe `salir`.

**Ejemplo de búsqueda exitosa:**


Com demano les vacances?
Bot: Has d’enviar un correu a rrhh@empresa.com amb les dates proposades.


**Ejemplo de búsqueda parcial:**
parlem de l'horari
Bot: L'horari es de 9:00 a 18:00, de dilluns a divendres.


**Ejemplo de búsqueda sin resultados:**
On es la maquina de cafe?
Bot: Ho sento, no he trobat una resposta per a la teva pregunta.


---

## Pruebas de la Aplicación

El proyecto incluye una suite de tests unitarios y de integración para garantizar la calidad y el correcto funcionamiento del código.

Para ejecutar todos los tests, utiliza el siguiente comando de Maven:
```bash
mvn test

Documentación REST (Swagger UI)
La API REST está completamente documentada usando OpenAPI 3. Una vez que la aplicación está en ejecución, puedes acceder a la interfaz interactiva de Swagger UI en la siguiente URL:

http://localhost:8090/swagger-ui.html

Adicionalmente, se ha incluido Spring Boot Actuator. Puedes consultar el estado de salud de la aplicación (incluyendo la conexión a la base de datos) aquí:

http://localhost:8090/actuator/health