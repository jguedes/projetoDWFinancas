system clear;-- LIMPAR A TELA
/*
Criado em 6-11-2013.
Por João Guedes, durante o 5º período em Bac. Ciência da Computação. FATEC-PE.
Para obtençao de nota parcial na disciplina Des. Web. Prof.: Walter Travassos.
SGBD utilizado: mysql.
Referências: Disponível em:<http://dev.mysql.com/doc/index.html>. Acessado em: 6 nov.2013.
*/
/******************************************************************************

EXCLUIR TUDO DO BANCO

*******************************************************************************
USE dbfinancasfacil;
******************************************************************************
DELETAR NESTA ORDEM TUPLAS DAS RELAÇÕES POR CAUSA DOS RELACIONAMENTOS (foreign keys)
*******************************************************************************
DELETE FROM receita;
DELETE FROM despesa;
DELETE FROM fatofinanceiro;
DELETE FROM descricao;
DELETE FROM tipodocumento;
DELETE FROM conta;
DELETE FROM usuario;
DELETE FROM logdecadastro;
*******************************************************************************/
/******************************************************************************

CONFIGURAR BANCO

*******************************************************************************/
DROP DATABASE IF EXISTS dbfinancasfacil;
CREATE DATABASE dbfinancasfacil;
USE dbfinancasfacil;
/******************************************************************************

DROP TABLES

*******************************************************************************/
DROP TABLE IF EXISTS despesa;
DROP TABLE IF EXISTS receita;
DROP TABLE IF EXISTS fatofinanceiro;
DROP TABLE IF EXISTS tipodocumento;
DROP TABLE IF EXISTS descricao;
DROP TABLE IF EXISTS conta;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS logdecadastro;
/******************************************************************************

CREATE TABLES

*******************************************************************************/
CREATE TABLE IF NOT EXISTS logdecadastro(
logtexto VARCHAR(255)
);
/*******************************************************************************
*******************************************************************************/
CREATE TABLE usuario(
id INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(255) NOT NULL UNIQUE,
email VARCHAR(255) UNIQUE,
senha VARCHAR(255) NOT NULL,
dtcadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
dead BOOL DEFAULT FALSE,
PRIMARY KEY(id)
);
/*******************************************************************************
*******************************************************************************/
CREATE TABLE conta(
id INT NOT NULL AUTO_INCREMENT,
usuario_id INT NOT NULL,
titulo VARCHAR(255) NOT NULL,
dtcadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
dead BOOL DEFAULT FALSE,
PRIMARY KEY(id),
FOREIGN KEY(usuario_id) REFERENCES usuario(id)
);
/*******************************************************************************
*******************************************************************************/
CREATE TABLE descricao(
id INT NOT NULL AUTO_INCREMENT,
usuario_id INT NOT NULL,
descricao VARCHAR(255) NOT NULL,
dtcadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
dead BOOL DEFAULT FALSE,
PRIMARY KEY(id),
FOREIGN KEY(usuario_id) REFERENCES usuario(id)
);
/*******************************************************************************
*******************************************************************************/
CREATE TABLE tipodocumento(
id INT NOT NULL AUTO_INCREMENT,
usuario_id INT NOT NULL,
nome VARCHAR(255) NOT NULL,
dtcadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
dead BOOL DEFAULT FALSE,
PRIMARY KEY(id),
FOREIGN KEY(usuario_id) REFERENCES usuario(id)
);
/*******************************************************************************
*******************************************************************************/
CREATE TABLE fatofinanceiro(
id INT NOT NULL AUTO_INCREMENT,
conta_id INT NOT NULL,
descricao_id INT NOT NULL,
valor DOUBLE NOT NULL,
dtocorrencia TIMESTAMP NULL DEFAULT NULL,
dtcadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
dead BOOL DEFAULT FALSE,
PRIMARY KEY(id),
FOREIGN KEY(conta_id) REFERENCES conta(id),
FOREIGN KEY(descricao_id) REFERENCES descricao(id)
);
/*******************************************************************************
*******************************************************************************/
CREATE TABLE receita(
fatofinanceiro_id INT NOT NULL,
PRIMARY KEY(fatofinanceiro_id),
FOREIGN KEY(fatofinanceiro_id) REFERENCES fatofinanceiro(id)
);
/*******************************************************************************
*******************************************************************************/
CREATE TABLE despesa(
fatofinanceiro_id INT NOT NULL,
tipodocumento_id INT,
numerodocumento VARCHAR(21),
PRIMARY KEY(fatofinanceiro_id),
FOREIGN KEY(fatofinanceiro_id) REFERENCES fatofinanceiro(id),
FOREIGN KEY(tipodocumento_id) REFERENCES tipodocumento(id)
);
/******************************************************************************

ROTINAS DE INSERÇÕES

*******************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS mensagem;
CREATE PROCEDURE mensagem(mensagem VARCHAR(255))
BEGIN
SELECT mensagem AS msg;
END;//
/*******************************************************************************
*******************************************************************************/
DROP PROCEDURE IF EXISTS inserir_usuario;
CREATE PROCEDURE inserir_usuario(nome VARCHAR(255),senha VARCHAR(255),email VARCHAR(255),out insert_id INT)
BEGIN
INSERT INTO usuario (nome,senha,email)
VALUES (nome,senha,email);
SET insert_id = LAST_INSERT_ID();
END;//
/*******************************************************************************
*******************************************************************************/
DROP PROCEDURE IF EXISTS inserir_conta;
CREATE PROCEDURE inserir_conta(usuario_id INT,titulo VARCHAR(255),out insert_id INT)
BEGIN
CALL mensagem(CONCAT('inserindo conta: ',titulo));
INSERT INTO conta (usuario_id,titulo)
VALUES (usuario_id,titulo);
SET insert_id = LAST_INSERT_ID();
CALL mensagem(CONCAT('titulo: ',titulo,' inserido com sucesso!'));
END;//
/*******************************************************************************
*******************************************************************************/
DROP PROCEDURE IF EXISTS inserir_descricao;
CREATE PROCEDURE inserir_descricao(usuario_id INT,descricao VARCHAR(255),OUT insert_id INT)
BEGIN
INSERT INTO descricao (usuario_id,descricao)
VALUES(usuario_id,descricao);
SET insert_id = LAST_INSERT_ID();
END;//
/*******************************************************************************
*******************************************************************************/
DROP PROCEDURE IF EXISTS inserir_tipodocumento;
CREATE PROCEDURE inserir_tipodocumento(usuario_id INT,nome VARCHAR(255),out insert_id INT)
BEGIN
CALL mensagem(CONCAT('inserindo tipodocumento: ',nome));
INSERT INTO tipodocumento (usuario_id,nome)
VALUES (usuario_id,nome);
SET insert_id = LAST_INSERT_ID();
CALL mensagem(CONCAT('tipodocumento: ',nome,' inserido com sucesso!'));
END;//
/*******************************************************************************
*******************************************************************************/
DROP PROCEDURE IF EXISTS inserir_fatofinanceiro;
CREATE PROCEDURE inserir_fatofinanceiro(conta_id INT,descricao_id INT,valor DOUBLE,dtocorrencia timestamp,out insert_id INT)
BEGIN
CALL mensagem(CONCAT('inserindo fatofinanceiro',''));
INSERT INTO fatofinanceiro (conta_id,descricao_id,valor,dtocorrencia)
VALUES (conta_id,descricao_id,valor,dtocorrencia);
SET insert_id = LAST_INSERT_ID();
CALL mensagem(CONCAT('fatofinanceiro','',' inserido com sucesso!'));
END;//
/*******************************************************************************
*******************************************************************************/
DROP PROCEDURE IF EXISTS inserir_receita;
CREATE PROCEDURE inserir_receita(conta_id INT,descricao_id INT,valor DOUBLE,dtocorrencia timestamp)
BEGIN
DECLARE fatofinanceiro_id INT;
CALL inserir_fatofinanceiro(conta_id,descricao_id,valor,dtocorrencia,fatofinanceiro_id);
INSERT INTO receita (fatofinanceiro_id)
VALUES (fatofinanceiro_id);
END;//
/*******************************************************************************
*******************************************************************************/
DROP PROCEDURE IF EXISTS inserir_despesa;
CREATE PROCEDURE inserir_despesa(conta_id INT,descricao_id INT,valor DOUBLE,n_documento VARCHAR(21),tipodocumento_id INT,dtocorrencia timestamp)
BEGIN
DECLARE fatofinanceiro_id INT;
CALL inserir_fatofinanceiro(conta_id,descricao_id,valor,dtocorrencia,fatofinanceiro_id);
INSERT INTO despesa (fatofinanceiro_id,numerodocumento,tipodocumento_id)
VALUES (fatofinanceiro_id,n_documento,tipodocumento_id);
END;//
/******************************************************************************

CREATE TRIGGERS

*******************************************************************************/
DROP TRIGGER IF EXISTS logdecadastro;
CREATE TRIGGER logdecadastro
AFTER INSERT ON usuario
FOR EACH ROW
BEGIN
INSERT INTO logdecadastro VALUES (CONCAT('usuario cadastrado: ',NEW.nome,' em ', NEW.dtcadastro));
END;//
DELIMITER ;
/*******************************************************************************

ROTINAS DE SELECTS

********************************************************************************/
/*******************************************************************************

RECEITAS

********************************************************************************/
/*******************************************************************************
PROCEDURE listar_receitas_por_usuario(usuario_id INT)
********************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS listar_receitas_por_usuario;
CREATE PROCEDURE listar_receitas_por_usuario(usuario_id INT)
BEGIN
SELECT
UPPER(u.nome) as 'USUÁRIO',
c.titulo AS 'CONTA',
d.descricao AS 'DESCRIÇÃO',
LPAD(FORMAT(f.valor,2,'pt_BR'),14,' ') AS 'VALOR R$',
DATE_FORMAT(f.dtcadastro,'%d de %b de %Y às %H:%i') As 'DATA DE CADASTRO'
FROM
receita r INNER JOIN (
fatofinanceiro f INNER JOIN
descricao d INNER JOIN
conta c INNER JOIN
usuario u
) ON (
f.id = r.fatofinanceiro_id AND
d.id = f.descricao_id AND
c.id = f.conta_id AND
u.id = c.usuario_id
) WHERE
c.usuario_id = usuario_id AND
f.dead = FALSE;
END;//
/****************************************************************************************
PROCEDURE calcular_somatorio_do_valor_de_todas_receitas_do_usuario(usuario_id INT)
*****************************************************************************************/
DROP PROCEDURE IF EXISTS calcular_somatorio_do_valor_de_todas_receitas_do_usuario;
CREATE PROCEDURE calcular_somatorio_do_valor_de_todas_receitas_do_usuario(usuario_id INT,OUT valor_total DOUBLE)
BEGIN
DECLARE p,x VARCHAR(255);
SET p = 'a partir do cadastro do usuário';
SELECT
u.nome,
sum(f.valor)
INTO x,valor_total
FROM
receita r INNER JOIN (
fatofinanceiro f INNER JOIN
descricao d INNER JOIN
conta c INNER JOIN
usuario u
) ON (
f.id = r.fatofinanceiro_id AND
d.id = f.descricao_id AND
c.id = f.conta_id AND
u.id = c.usuario_id
) WHERE
c.usuario_id = usuario_id AND
f.dead = FALSE;
SELECT x as 'USUÁRIO',valor_total AS 'VALOR TOTAL DE RECEITAS R$', p AS 'PERÍODO';
END;//
/****************************************************************************************
PROCEDURE relatorio_de_todas_receitas_do_usuario(usuario_id INT)
*****************************************************************************************/
DROP PROCEDURE IF EXISTS relatorio_de_todas_receitas_do_usuario;//
CREATE PROCEDURE relatorio_de_todas_receitas_do_usuario(usuario_id INT,OUT total_receita DOUBLE)
BEGIN
CALL listar_receitas_por_usuario(usuario_id);
CALL calcular_somatorio_do_valor_de_todas_receitas_do_usuario(usuario_id,total_receita);
END;//
DELIMITER ;
/*******************************************************************************

DESPESAS

********************************************************************************/
/*******************************************************************************
PROCEDURE listar_todas_despesas_do_usuario(usuario_id INT)
********************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS listar_todas_despesas_do_usuario;
CREATE PROCEDURE listar_todas_despesas_do_usuario(usuario_id INT)
BEGIN
SELECT
UPPER(u.nome) as 'USUÁRIO',
c.titulo AS 'CONTA',
d.descricao AS 'DESCRIÇÃO',
LPAD(FORMAT(f.valor,2,'pt_BR'),14,' ') AS 'VALOR R$',
DATE_FORMAT(f.dtcadastro,'%d de %b de %Y às %H:%i') As 'DATA DE CADASTRO'
FROM
despesa r INNER JOIN (
fatofinanceiro f INNER JOIN
descricao d INNER JOIN
conta c INNER JOIN
usuario u
) ON (
f.id = r.fatofinanceiro_id AND
d.id = f.descricao_id AND
c.id = f.conta_id AND
u.id = c.usuario_id
) WHERE
c.usuario_id = usuario_id AND
f.dead = FALSE;
END;//
/****************************************************************************************
PROCEDURE calcular_somatorio_do_valor_de_todas_despesas_do_usuario(usuario_id INT)
*****************************************************************************************/
DROP PROCEDURE IF EXISTS calcular_somatorio_do_valor_de_todas_despesas_do_usuario;
CREATE PROCEDURE calcular_somatorio_do_valor_de_todas_despesas_do_usuario(usuario_id INT,OUT valor_total DOUBLE)
BEGIN
DECLARE p,x VARCHAR(255);
SET p = 'a partir do cadastro do usuário';
SELECT
u.nome,
sum(f.valor)
INTO x,valor_total
FROM
despesa r INNER JOIN (
fatofinanceiro f INNER JOIN
descricao d INNER JOIN
conta c INNER JOIN
usuario u
) ON (
f.id = r.fatofinanceiro_id AND
d.id = f.descricao_id AND
c.id = f.conta_id AND
u.id = c.usuario_id
) WHERE
c.usuario_id = usuario_id AND
f.dead = FALSE;
SELECT x as 'USUÁRIO',valor_total AS 'VALOR TOTAL DE DESPESAS R$', p AS 'PERÍODO';
END;//
/****************************************************************************************
PROCEDURE relatorio_de_todas_despesas_do_usuario(usuario_id INT)
*****************************************************************************************/
DROP PsROCEDURE IF EXISTS relatorio_de_todas_despesas_do_usuario;//
CREATE PROCEDURE relatorio_de_todas_despesas_do_usuario(usuario_id INT,OUT total_despesa DOUBLE)
BEGIN
CALL listar_todas_despesas_do_usuario(usuario_id);
CALL calcular_somatorio_do_valor_de_todas_despesas_do_usuario(usuario_id,total_despesa);
END;//
DELIMITER ;
/*******************************************************************************

ROTINAS DE RELATÓRIOS DE RECEITAS E DESPESAS

********************************************************************************/
/****************************************************************************************
PROCEDURE relatorio_de_todas_receitas_e_despesas_do_usuario(usuario_id INT)
*****************************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS relatorio_de_todas_receitas_e_despesas_do_usuario;//
CREATE PROCEDURE relatorio_de_todas_receitas_e_despesas_do_usuario(usuario_id INT)
BEGIN
DECLARE total_receita, total_despesa DOUBLE;
CALL relatorio_de_todas_receitas_do_usuario(usuario_id,total_receita);
CALL relatorio_de_todas_despesas_do_usuario(usuario_id,total_despesa);
SELECT (SELECT UPPER(nome) FROM usuario WHERE id = usuario_id) AS 'USUÁRIO',FORMAT((total_receita),2,'pt_BR') AS 'RECEITAS DO PERÍODO',FORMAT((total_despesa),2,'pt_BR') AS 'DEPESAS DO PERÍODO',FORMAT((total_receita - total_despesa),2,'pt_BR') AS 'SALDO DO PERÍODO','a partir do cadastro do usuário' AS 'PERÍODO';
END;//
DELIMITER ;
/****************************************************************************************

ROTINAS DE ATUALIZAÇÃO

*****************************************************************************************/
/*****************************************************************************************
MODIFICAR CONTA
*****************************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS modificar_titulo_de_conta;
CREATE PROCEDURE modificar_titulo_de_conta(conta_id INT,titulo VARCHAR(255))
BEGIN
UPDATE conta SET titulo = titulo WHERE id = conta_id;
END;//
DELIMITER ;
/*****************************************************************************************
MODIFICAR FATOFINANCEIRO(RECEITA OU DESPESA)
*****************************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS modificar_valor_de_receita_ou_despesa;
CREATE PROCEDURE modificar_valor_de_receita_ou_despesa(fatofinanceiro_id INT,valor DOUBLE)
BEGIN
UPDATE fatofinanceiro SET valor = valor WHERE id = fatofinanceiro_id;
END;//
DROP PROCEDURE IF EXISTS modificar_descricao_de_receita_ou_despesa;
CREATE PROCEDURE modificar_descricao_de_receita_ou_despesa(fatofinanceiro_id INT,descricao_id INT)
BEGIN
UPDATE fatofinanceiro SET descricao_id = descricao_id WHERE id = fatofinanceiro_id;
END;//
DELIMITER ;
/*****************************************************************************************
MODIFICAR DESCRICAO(RECEITA OU DESPESA)
*****************************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS modificar_nome_de_descricao;
CREATE PROCEDURE modificar_nome_de_descricao(descricao_id INT, descricao VARCHAR(255))
BEGIN
UPDATE descricao SET descricao = descricao WHERE id = descricao_id;
END;//
DELIMITER ;
/******************************************************************************************

ROTINAS DE EXCLUSÃO

******************************************************************************************/
/******************************************************************************************
DELETAR CONTA
******************************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS excluir_conta;
CREATE PROCEDURE excluir_conta(conta_id int)
BEGIN
UPDATE fatofinanceiro SET dead = true WHERE conta_id = conta_id;
UPDATE conta SET dead = true WHERE id = conta_id;
END;//
DELIMITER ;
/******************************************************************************************
DELETAR FATOFINANCEIRO(RECEITA OU DESPESA)
******************************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS excluir_receita_ou_despesa;
CREATE PROCEDURE excluir_receita_ou_despesa(fatofinanceiro_id int)
BEGIN
UPDATE fatofinanceiro SET dead = true WHERE id = fatofinanceiro_id;
END;//
DELIMITER ;
/******************************************************************************************
DELETAR DESCRICAO(RECEITA OU DESPESA)
******************************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS excluir_descricao;
CREATE PROCEDURE excluir_descricao(descricao_id int)
BEGIN
UPDATE fatofinanceiro SET dead = true WHERE descricao_id = descricao_id;
UPDATE descricao SET dead = true WHERE id = descricao_id;
END;//
DELIMITER ;
/******************************************************************************

POPULAR BANCO

*******************************************************************************/
DELIMITER //
DROP PROCEDURE IF EXISTS novousuario;
CREATE PROCEDURE novousuario(nome VARCHAR(255),senha VARCHAR(255),email VARCHAR(255))
BEGIN
DECLARE usuario_id INT;
DECLARE conta1, conta2, conta3 INT;
DECLARE descricao_receita1, descricao_receita2, descricao_receita3 INT;
DECLARE descricao_despesa1, descricao_despesa2, descricao_despesa3 INT;
DECLARE tipodocumento1, tipodocumento2, tipodocumento3 INT;
CALL mensagem('__________________________INICIO INSERSÃO DE USUÁRIOS________________________');
CALL inserir_usuario(nome,senha,email,usuario_id);
CALL mensagem('__________________________FIM INSERSÃO DE USUÁRIOS________________________');
CALL mensagem('__________________________INICIO INSERSÃO DE CONTAS________________________');
CALL inserir_conta(usuario_id,'conta1',conta1);
CALL inserir_conta(usuario_id,'conta2',conta2);
CALL inserir_conta(usuario_id,'conta3',conta3);
CALL mensagem('__________________________FIM INSERSÃO DE CONTAS________________________');
CALL mensagem('__________________________INICIO INSERSÃO DE DESCRIÇÕES________________________');
CALL inserir_descricao(usuario_id,'descricao1_receita',descricao_receita1);
CALL inserir_descricao(usuario_id,'descricao2_receita',descricao_receita2);
CALL inserir_descricao(usuario_id,'descricao3_receita',descricao_receita3);
CALL inserir_descricao(usuario_id,'descricao1_despesa',descricao_despesa1);
CALL inserir_descricao(usuario_id,'descricao2_despesa',descricao_despesa2);
CALL inserir_descricao(usuario_id,'descricao3_despesa',descricao_despesa3);
CALL mensagem('__________________________FIM INSERSÃO DE DESCRIÇÕES________________________');
CALL mensagem('__________________________INICIO INSERSÃO DE TIPOS DE DOCUMENTOS________________________');
CALL inserir_tipodocumento(usuario_id,'nota fiscal',tipodocumento1);
CALL inserir_tipodocumento(usuario_id,'recibo',tipodocumento2);
CALL inserir_tipodocumento(usuario_id,'comprovante de depósito',tipodocumento3);
CALL mensagem('__________________________FIM INSERSÃO DE TIPOS DE DOCUMENTOS________________________');
CALL mensagem('__________________________INICIO INSERSÃO DE RECEITAS________________________');
CALL inserir_receita(conta1,descricao_receita1,10000.0,NOW());
CALL inserir_receita(conta1,descricao_receita1,200.0,NOW());
CALL inserir_receita(conta2,descricao_receita2,50.0,NOW());
CALL inserir_receita(conta3,descricao_receita3,3.0,NOW());
CALL mensagem('__________________________FIM INSERSÃO DE RECEITAS________________________');
CALL mensagem('__________________________INICIO INSERSÃO DE DESPESAS________________________');
CALL inserir_despesa(conta1,descricao_despesa1,637.11,'98765',tipodocumento1,NOW());
CALL inserir_despesa(conta1,descricao_despesa2,0.11,NULL,tipodocumento1,NOW());
CALL inserir_despesa(conta2,descricao_despesa3,32.10,NULL,tipodocumento2,NOW());
CALL mensagem('__________________________FIM INSERSÃO DE DESPESAS________________________');
END;//
/********************************************************************************************

INSERINDO USUÁRIOS NOVOS

*********************************************************************************************/
DELIMITER ;
CALL novousuario('bertrano','123','bertrano@email');
CALL novousuario('cicrano','123','cicrano@email');
CALL novousuario('fulano','123','fulano@email');
CALL novousuario('zeroberto','123','zeroberto@email');
CALL novousuario('umberto','123','umberto@email');
CALL novousuario('doisberto','123','doisberto@email');
CALL novousuario('tresberto','123','tresberto@email');
/********************************************************************************************

GERANDO RELATÓRIOS PARA O USUÁRIO 'BERTRANO'

*********************************************************************************************/
CALL relatorio_de_todas_receitas_e_despesas_do_usuario(1); 

