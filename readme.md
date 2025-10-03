# Onboarding Assistant Bot 🤖

Un asistente conversacional simple, implementado en Java con Spring Boot, diseñado para resolver las preguntas frecuentes durante el proceso de onboarding de nuevos empleados.

Este proyecto ha sido desarrollado como prueba técnica, poniendo el foco en una arquitectura limpia, un código mantenible y, sobre todo, una experiencia de usuario natural y fluida.

---

## 📸 Demo en Acción

El bot es capaz de entender la intención del usuario incluso cuando la pregunta contiene "ruido" o un lenguaje coloquial, gracias a su algoritmo de búsqueda por relevancia.

![Demostración del Bot en la Consola](https://i.imgur.com/G5gC3jB.png)

---

## 📋 Requisitos Previos

Antes de empezar, asegúrate de tener instalado en tu sistema:

* **Java**: JDK 17 o superior.
* **Maven**: Versión 3.8 o superior.
* **Git**.

---

## 🚀 Instalación y Ejecución

Sigue estos pasos para clonar y ejecutar el proyecto en tu máquina local.

1.  **Clona el repositorio:**
    ```bash
    git clone [URL_DE_TU_REPOSITORIO]
    cd [NOMBRE_DE_LA_CARPETA_DEL_PROYECTO]
    ```

2.  **Compila y empaqueta el proyecto:**
    Este comando compilará el código, ejecutará los tests y generará un fichero `.jar` ejecutable en el directorio `target/`.
    ```bash
    mvn clean package
    ```

3.  **Ejecuta el asistente conversacional:**
    Una vez compilado, lanza la aplicación. La consola se quedará esperando tus preguntas.
    ```bash
    java -jar target/onboarding-assistant-0.0.1-SNAPSHOT.jar
    ```

---

## 💬 Ejemplos de Uso

Una vez la aplicación está en marcha, puedes interactuar con el bot directamente desde la consola.

**Consulta con una respuesta conocida:**
> **> ¿Cómo pido las vacaciones?**
> **Bot:** Debes enviar un correo a rrhh@empresa.com con las fechas propuestas.

**Consulta con una sola palabra clave:**
> **> nómina**
> **Bot:** La nómina se ingresa el último día hábil de cada mes.

**Consulta sin respuesta conocida:**
> **> ¿Dónde está la máquina de café?**
> **Bot:** Lo siento, no tengo una respuesta para tu pregunta

**Para finalizar la sesión:**
> **> salir**
> **Bot:** Cerrando sesión del asistente...

---

## ✅ Pruebas de la Aplicación

El proyecto incluye una suite de tests unitarios y de integración para garantizar la calidad del código y el correcto funcionamiento de los endpoints de la API. Para ejecutarlos, utiliza el siguiente comando desde la raíz del proyecto:

```bash
mvn test
```

---

## 📖 Documentación de la API REST

La gestión de la base de conocimiento se puede realizar a través de una API REST. La documentación completa de los endpoints, generada con **SpringDoc (OpenAPI)**, está disponible en la siguiente URL una vez que la aplicación está en marcha:

[**http://localhost:8080/swagger-ui/index.html**](http://localhost:8080/swagger-ui/index.html)

### Endpoints Principales
* `GET /api/knowledge`: Devuelve todas las preguntas y respuestas almacenadas.
* `POST /api/knowledge`: Añade una nueva entrada a la base de conocimiento.

---

## 👨‍💻 Autor

* **[Jose Miguel Arbizu]** - [https://www.linkedin.com/in/jose-arbizu-rendon-ab9501354/]