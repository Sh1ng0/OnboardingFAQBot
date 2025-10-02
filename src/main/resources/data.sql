-- Este fichero se ejecuta automáticamente al arrancar la aplicación con H2.

-- Borramos la tabla si existe para asegurar un estado limpio cada vez que arrancamos (opcional pero bueno para desarrollo)
DROP TABLE IF EXISTS question_answer;

-- Creamos la tabla (aunque JPA también puede hacerlo, es bueno tenerlo explícito)
CREATE TABLE question_answer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(1000) NOT NULL
);

-- Insertamos los datos iniciales
INSERT INTO question_answer (question, answer) VALUES ('Com solicito vacances?', 'Has d’enviar un correu a rrhh@empresa.com amb les dates proposades.');
INSERT INTO question_answer (question, answer) VALUES ('Quin es l''horari de l''empresa?', 'L''horari es de 9:00 a 18:00, de dilluns a divendres.');
INSERT INTO question_answer (question, answer) VALUES ('On puc trobar la documentacio de benvinguda?', 'Pots trobar tota la documentacio al nostre portal intern a la seccio ''Onboarding''.');
INSERT INTO question_answer (question, answer) VALUES ('Com funcionen les baixes mediques?', 'Has d''avisar al teu manager directament i enviar el comunicat de baixa a RRHH en un termini de 48h.');
INSERT INTO question_answer (question, answer) VALUES ('Quina es la politica de teletreball?', 'La nostra politica es un model híbrid flexible. Consulta els detalls al portal de l''empleat.');
