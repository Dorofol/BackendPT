SET foreign_key_checks = 0;

TRUNCATE TABLE votos_seq;
TRUNCATE TABLE votos;
TRUNCATE TABLE votaciones_seq;
TRUNCATE TABLE usuarios_votacion;
TRUNCATE TABLE usuarios;
TRUNCATE TABLE usuario_organizacion_rol;
TRUNCATE TABLE organizaciones;
TRUNCATE TABLE candidatos_seq;
TRUNCATE TABLE candidatos;
TRUNCATE TABLE votaciones;
INSERT INTO candidatos_seq (next_val) VALUES (1);
INSERT INTO votaciones_seq (next_val) VALUES (1);
INSERT INTO votos_seq (next_val) VALUES (1);
SET foreign_key_checks = 1;
