# Sistema de Búsqueda por Relevancia (Basado en el sistema de diálogo de la saga King's Quest) y los juego de Sierra en general

El asistente conversacional no se limita a buscar coincidencias exactas de las preguntas, lo que obligaría al usuario a adivinar la formulación correcta. En su lugar, se ha implementado un algoritmo de búsqueda por relevancia para ofrecer una experiencia de usuario más fluida y natural.

El objetivo del algoritmo es, dada una pregunta formulada libremente por el usuario, encontrar la pregunta más similar existente en la base de conocimiento y devolver su respuesta asociada.

---

## Fases del Proceso

### 1. Normalización y Tokenización
Para poder comparar dos textos de manera efectiva, primero deben ser procesados y estandarizados. Tanto la pregunta del usuario como cada una de las preguntas candidatas de la base de datos pasan por este proceso:

- **Paso a minúsculas**: se unifica el texto para que la comparación no distinga entre mayúsculas y minúsculas.  
- **Eliminación de diacríticos**: se eliminan acentos y otros signos diacríticos (ej: `sol·licito` → `sollicito`).  
- **Eliminación de puntuación**: se eliminan todos los signos de puntuación (`?`, `¿`, `!`, `,`, `.`).  
- **Tokenización**: el texto normalizado se descompone en un conjunto (*Set*) de palabras únicas, llamadas *tokens*. El uso de un *Set* simplifica el posterior cálculo de intersección.

---

### 2. Cálculo de Puntuación (Scoring)
El núcleo del algoritmo reside en asignar una *puntuación de similitud* a cada pregunta candidata de la base de datos en relación con la pregunta del usuario.

La puntuación se calcula de la siguiente manera:

```
Puntuación = Número de tokens en común entre la pregunta del usuario y la pregunta candidata
```

Este cálculo se realiza encontrando la **intersección** entre el conjunto de tokens del usuario y el conjunto de tokens de la pregunta candidata.

---

### 3. Selección y Umbral de Confianza
Una vez que todas las preguntas candidatas han sido puntuadas, el sistema:

1. Busca el candidato con la puntuación máxima.  
2. Comprueba si esta puntuación supera un **umbral de confianza mínimo** (actualmente fijado en `2`).  

El umbral es crucial para evitar falsos positivos. Por ejemplo, si la única palabra en común es un artículo o una palabra muy genérica, la puntuación sería baja y el sistema descartaría la coincidencia por considerarla poco fiable.

Si se encuentra un candidato que supera el umbral, se devuelve su respuesta. En caso contrario, el sistema considera que no ha encontrado ninguna respuesta relevante.

---

## Ejemplo Práctico

**Pregunta del usuario**:  
```
Com demano jo les vacances?
```

**Tokens Usuario**:  
```
{ "com", "demano", "jo", "les", "vacances" }
```

---

**Candidata 1 (BBDD)**:  
```
Com sol·licito vacances?
```

**Tokens Candidata 1**:  
```
{ "com", "sollicito", "vacances" }
```

**Intersección**:  
```
{ "com", "vacances" }
```

**Puntuación**: `2`

---

**Candidata 2 (BBDD)**:  
```
Quin es l'horari de l'empresa?
```

**Tokens Candidata 2**:  
```
{ "quin", "es", "lhorari", "de", "lempresa" }
```

**Intersección**:  
```
{}
```

**Puntuación**: `0`

---

### Resultado
La **Candidata 1** es seleccionada como la mejor coincidencia, ya que su puntuación (`2`) es la más alta y cumple el umbral mínimo. El sistema devolverá la respuesta asociada a esta pregunta.


Nota sobre el umbral de tolerancia (Pasando de 2 a 1)

Inicialmente el sistema necesitaba 2 palabras coincidentes para dar una respuesta...

java
// Esto no funcionaba:
Usuario: "vacaciones" ❌
Usuario: "teletrabajo" ❌  
Usuario: "nómina" ❌

// Pero esto sí:
Usuario: "quiero saber sobre vacaciones" ✅
Problema: La teoría decía "2 palabras = más precisión". La realidad: "1 palabra bien filtrada = mejor experiencia".

La Solución
Bajamos el threshold a 1 teniendo en cuenta que hemos creado las stop words qe evitan falsos positivos 

30+ STOP_WORDS que eliminan "quiero", "saber", "sobre", etc.

Con esto la experiencia de usuario mejora enormemente.
(Pese a que desde un punto de vista de consulta a la base de datos 2 sea lo idóneo)



