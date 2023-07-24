INSERT INTO pais(id, name) VALUES(1, 'Brasil');
INSERT INTO pais(id, name) VALUES(2, 'Portugal');

INSERT INTO equipe(id_quipe, nome_equipe) VALUES (1, 'Ferrari');
INSERT INTO equipe(id_quipe, nome_equipe) VALUES (2, 'Aston Martin');

INSERT INTO piloto(id_piloto, nome_piloto, country_id, team_id_quipe) VALUES(1, 'JeDai', 1, 1);
INSERT INTO piloto(id_piloto, nome_piloto, country_id, team_id_quipe) VALUES(2, 'Piloto2', 2, 2);

INSERT INTO pista(id_pista, nome_pista, tamanho_pista, country_id) VALUES(1, 'Interlagos', 1000, 1);
INSERT INTO pista(id_pista, nome_pista, tamanho_pista, country_id) VALUES(2, 'Algarve', 2000, 2);

INSERT INTO campeonato(codigo_campeonato, descricao, ano) VALUES(1, 'Blast', 2023);
INSERT INTO campeonato(codigo_campeonato, descricao, ano) VALUES(2, 'Pro League', 2023);

INSERT INTO corrida(id_corrida, data_corrida, speedway_id_pista, championship_codigo_campeonato) VALUES(1, now(), 1, 1);
INSERT INTO corrida(id_corrida, data_corrida, speedway_id_pista, championship_codigo_campeonato) VALUES(2, now(), 2, 2);

INSERT INTO piloto_corrida(id, colocacao, pilot_id_piloto, race_id_corrida) VALUES(1, 1, 1, 1);
INSERT INTO piloto_corrida(id, colocacao, pilot_id_piloto, race_id_corrida) VALUES(2, 2, 2, 2);