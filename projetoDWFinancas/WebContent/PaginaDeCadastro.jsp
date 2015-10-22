<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="estilos.css">
<title>Controle Financeiro Pessoal - Cadastro de Usuário</title>
</head>
<body>
	<div class="div_Cabecalho">
		<div class="div_Cabecalho_titulo">
			<h1>Controle Financeiro Pessoal</h1>
		</div>
	</div>
	<div class="div_linha">
		<hr>
	</div>
	<div class="div_Funcionalidade">
		<h2>Cadastrar usuário</h2>
	</div>
	<div class="div_Conteudo">
		<form action="cadastrar.do" method="post">
			<div>
				<div class="div_campo_nome">
					<div class="div_campo_nome_label">
						<label for="nome">Digite seu nome:</label>
					</div>
					<div class="div_campo_nome_text">
						<input type="text" name="nome">
					</div>
				</div>
				<div class="div_campo_email">
					<div class="div_campo_email_label">
						<label for="email">Digite seu e-mail:</label>
					</div>
					<div class="div_campo_email_text">
						<input type="text" name="email">
					</div>
				</div>
				<div class="div_campo_senha">
					<div class="div_campo_senha_label">
						<label for="senha">Digite sua senha:</label>
					</div>
					<div class="div_campo_senha_text">
						<input type="password" name="senha">
					</div>
				</div>
				<div>
					<div>
						<input type="button" value="Voltar" onclick="back()"> <input
							type="submit" value="Cadastrar">
					</div>
				</div>


			</div>
		</form>
	</div>
</body>
</html>