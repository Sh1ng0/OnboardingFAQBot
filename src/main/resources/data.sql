-- ===================================================================
-- DATASET v1.0 PARA ONBOARDING ASSISTANT
-- ===================================================================

-- TEMA: Vacaciones
INSERT INTO question_answer (question, answer) VALUES ('¿Cómo solicito las vacaciones?', 'Debes enviar un correo a rrhh@empresa.com con las fechas propuestas.');
INSERT INTO question_answer (question, answer) VALUES ('¿Cuál es el procedimiento para pedir días libres?', 'Debes enviar un correo a rrhh@empresa.com con las fechas propuestas.');

-- TEMA: Horario
INSERT INTO question_answer (question, answer) VALUES ('¿Cuál es el horario de la empresa?', 'El horario es de 9:00 a 18:00, de lunes a viernes, con una hora para comer.');
INSERT INTO question_answer (question, answer) VALUES ('¿A qué hora se entra y se sale de trabajar?', 'El horario es de 9:00 a 18:00, de lunes a viernes, con una hora para comer.');
INSERT INTO question_answer (question, answer) VALUES ('¿Tenemos horario flexible?', 'Sí, tenemos un margen de entrada flexible entre las 8:30 y las 9:30, ajustando la hora de salida correspondientemente.');

-- TEMA: Documentación y Recursos
INSERT INTO question_answer (question, answer) VALUES ('¿Dónde puedo encontrar la documentación de bienvenida?', 'Puedes encontrar toda la documentación en nuestro portal interno, en la sección ''Onboarding''.');
INSERT INTO question_answer (question, answer) VALUES ('¿Hay algún manual para nuevos empleados?', 'Sí, el manual de bienvenida está en el portal interno, en la sección ''Onboarding''.');

-- TEMA: Bajas Médicas
INSERT INTO question_answer (question, answer) VALUES ('¿Cómo funcionan las bajas médicas?', 'Debes avisar a tu mánager directamente y enviar el comunicado de baja a RRHH en un plazo de 48h.');
INSERT INTO question_answer (question, answer) VALUES ('¿Qué hago si me pongo enfermo?', 'Lo primero es avisar a tu mánager. Después, tienes 48h para hacer llegar el comunicado de baja a RRHH.');
INSERT INTO question_answer (question, answer) VALUES ('¿Qué hago si me pongo enferma?', 'Lo primero es avisar a tu mánager. Después, tienes 48h para hacer llegar el comunicado de baja a RRHH.');

-- TEMA: Teletrabajo
INSERT INTO question_answer (question, answer) VALUES ('¿Cuál es la política de teletrabajo?', 'Nuestra política es un modelo híbrido flexible. Consulta los detalles en el portal del empleado.');
INSERT INTO question_answer (question, answer) VALUES ('teletrabajo', 'Nuestra política es un modelo híbrido flexible. Consulta los detalles en el portal del empleado.');
INSERT INTO question_answer (question, answer) VALUES ('¿Cuántos días se puede trabajar desde casa?', 'El número de días depende de tu equipo y rol. Habla con tu mánager para definirlo. La política general está en el portal del empleado.');

-- TEMA: Nóminas (NUEVO)
INSERT INTO question_answer (question, answer) VALUES ('¿Cuándo se cobra la nómina?', 'La nómina se ingresa el último día hábil de cada mes.');
INSERT INTO question_answer (question, answer) VALUES ('¿Cuándo cobramos la nómina?', 'La nómina se ingresa el último día hábil de cada mes.');
INSERT INTO question_answer (question, answer) VALUES ('¿Qué día del mes se paga el salario?', 'El salario se abona en tu cuenta el último día laborable del mes.');

-- TEMA: Gastos y Dietas (NUEVO)
INSERT INTO question_answer (question, answer) VALUES ('¿Cómo puedo reportar los gastos de un viaje?', 'Debes rellenar el formulario de gastos en el Portal del Empleado y adjuntar las facturas o tickets correspondientes.');

-- TEMA: Material y Equipo (NUEVO)
INSERT INTO question_answer (question, answer) VALUES ('Necesito un monitor nuevo, ¿cómo lo pido?', 'Abre un ticket en el portal de IT (ServiceDesk) especificando el material que necesitas y la justificación.');