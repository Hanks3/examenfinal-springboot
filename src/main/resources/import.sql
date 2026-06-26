INSERT INTO tematicas (nombre) VALUES ('Matematicas');
INSERT INTO tematicas (nombre) VALUES ('Lengua');
INSERT INTO tematicas (nombre) VALUES ('Ciencias');
INSERT INTO tematicas (nombre) VALUES ('Historia');
INSERT INTO tematicas (nombre) VALUES ('Ingles');

INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opcion_correcta) VALUES ('2 + 2 = 4?', 'V_F', 1, true);
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opciones, respuesta_correcta) VALUES ('Cual es la capital de Francia?', 'SELECCION_UNICA', 4, 'Madrid||Paris||Londres||Berlin', 'Paris');
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opciones, respuesta_correcta) VALUES ('Cual es el sujeto de la oracion: El perro corre por el parque?', 'SELECCION_UNICA', 2, 'El perro||corre||por el parque||El', 'El perro');
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opciones, respuestas_correctas) VALUES ('Cuales son numeros primos?', 'SELECCION_MULTIPLE', 1, '2||3||4||5', '2||3||5');
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opciones, respuestas_correctas) VALUES ('Cuales son planetas del sistema solar?', 'SELECCION_MULTIPLE', 3, 'Tierra||Sol||Marte||Luna', 'Tierra||Marte');
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opcion_correcta) VALUES ('La Segunda Guerra Mundial empezo en 1939?', 'V_F', 4, true);
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opciones, respuesta_correcta) VALUES ('Como se dice arbol en ingles?', 'SELECCION_UNICA', 5, 'Tree||Three||Trei||True', 'Tree');
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opciones, respuestas_correctas) VALUES ('Cuales son sinonimos de feliz?', 'SELECCION_MULTIPLE', 2, 'Alegre||Triste||Contento||Enojado', 'Alegre||Contento');
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opcion_correcta) VALUES ('El agua hierve a 100°C?', 'V_F', 3, true);
INSERT INTO preguntas (enunciado, tipo_pregunta, tematica_id, opciones, respuesta_correcta) VALUES ('Quien escribio Don Quijote?', 'SELECCION_UNICA', 2, 'Cervantes||Lope de Vega||Quevedo||Gongora', 'Cervantes');
