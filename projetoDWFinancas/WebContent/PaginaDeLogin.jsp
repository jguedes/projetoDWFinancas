<%@page import="model.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="estilos.css">
<title>Controle Financeiro Pessoal - Login</title>
</head>

<body>
	<%!String mensagem = null, email = "";%>
	<!-- ROTINAS PARA MOSTRAR MENSAGENS AO USUARIO EM CASO DE EVENTOS INESPERADOS -->
	<%
		if (request.getSession().getAttribute("Erro") == "campovazio") {
			mensagem = "Campo vazio";
		} else if (request.getSession().getAttribute("Erro") == "contanaoexiste") {
			mensagem = "E-mail de usuário não cadastrado!";
			email = ((Usuario) request.getSession().getAttribute(
					"theUsuario")).getEmail();
		}

		request.getSession().setAttribute("Erro", null);
	%>
	<div class="div_Cabecalho">
		<div class="div_Cabecalho_titulo">
			<h1>Controle Financeiro Pessoal</h1>
		</div>
	</div>
	<div class="div_linha">
		<hr>
	</div>
	<div class="div_Funcionalidade">
		<h2>Fazer login</h2>
	</div>
	<div class="div_Conteudo">
		<div>
			<div>
				<form action="irParaCadastro.do" method="post">
					<input type="submit"
						value="Sou novo por aqui. Quero CADASTRAR - ME!"
						title="Clique para se cadastrar.">
				</form>
			</div>
			<br>
			<form action="realizarlogin" method="post">
				<%
					//ROTINA PARA MOSTRAR MENSAGEM AO USÁRIO SOBRE DADOS INVÁLIDOS
					if (mensagem != null) {
				%>
				<p class="p_ErrorField"><%=mensagem%></p>
				<%
					mensagem = null;
					}
				%>
				<div class="div_campo_email">
					<div class="div_campo_email_label">
						<label for="email">E-mail: </label>
					</div>
					<div class="div_campo_email_text">
						<!-- A VARIVAEL email É POSTA PARA CARREGAR O ULTIMO EMAIL DIGITADO PELO USUARIO -->
						<input type="text" name="email" value=<%=email%>>
					</div>
				</div>
				<div class="div_campo_senha">
					<div class="div_campo_senha_label">
						<label for="senha">Senha: </label>
					</div>
					<div class="div_campo_senha_text">
						<input type="password" name="senha">
					</div>
				</div>
				<div class="div_campo_botao">
					<input type="submit" value="Entrar">
				</div>
			</form>
		</div>
	</div>
</body>
</html>