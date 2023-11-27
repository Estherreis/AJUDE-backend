insert into estado(sigla, descricao) values ('TO', 'Tocantins');
insert into estado(sigla, descricao) values ('GO', 'Goiás');

insert into municipio(descricao, id_estado) values ('Palmas', 1);
insert into municipio(descricao, id_estado) values ('Araguaína', 1);
insert into municipio(descricao, id_estado) values ('Goiania', 2);
insert into municipio(descricao, id_estado) values ('Anápolis', 2);

insert into orgao(nome, sigla, id_municipio, ativo) values ('Tribunal de Justiça', 'TJTO', 1, true);
insert into orgao(nome, sigla, id_municipio, ativo) values ('Turma Recursal', 'TR', 1, true);
insert into orgao(nome, sigla, id_municipio, ativo) values ('Juízo da 1ª Vara Cível de Palmas', 'JVCP', 1, true);
insert into orgao(nome, sigla, id_municipio, ativo) values ('uízo da 1ª Vara Cível de Araguaína', 'JVCA', 2, true);

INSERT INTO endereco (id_municipio, id_estado, bairro, logradouro, numero)
VALUES (1, 1, 'Plano Dir. Sul', '1003 Sul', 1);
INSERT INTO endereco (id_municipio, id_estado, bairro, logradouro, numero)
VALUES (1, 1, 'Centro', 'Rua Principal', 10);
INSERT INTO endereco (id_municipio, id_estado, bairro, logradouro, numero)
VALUES (2, 1, 'Bela Vista', 'Avenida Secundária', 25);
INSERT INTO endereco (id_municipio, id_estado, bairro, logradouro, numero)
VALUES (1, 1, 'Jardim das Flores', 'Rua das Flores', 7);

INSERT INTO beneficiario (nome, cpf, rg, cpfPai, nis, dataNascimento, id_endereco)
VALUES ('Esther', '11111111111', '1111111', '22222222222', '111111', '2004-08-23', 1);
INSERT INTO beneficiario (nome, cpf, rg, cpfPai, nis, dataNascimento, id_endereco)
VALUES ('Isac', '22222222222', '2222222', '33333333333', '222222', '1990-05-15', 2);
INSERT INTO beneficiario (nome, cpf, rg, cpfPai, nis, dataNascimento, id_endereco)
VALUES ('Iad', '33333333333', '3333333', '44444444444', '333333', '1985-12-10', 3);
INSERT INTO beneficiario (nome, cpf, rg, cpfPai, nis, dataNascimento, id_endereco)
VALUES ('Tayse', '44444444444', '4444444', '55555555555', '444444', '2000-02-28', 4);