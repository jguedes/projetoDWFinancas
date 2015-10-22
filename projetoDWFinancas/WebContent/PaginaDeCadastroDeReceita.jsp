<%@page import="model.Usuario"%>
<%@page import="model.Descricao"%>
<%@page import="model.Conta"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="estilos.css">
<title>Controle Financeiro Pessoal - Cadastro de receita</title>
</head>
<!-- **************************************************************************************************************** -->
<!-- *************************************************** ATENÇÃO **************************************************** -->
<!-- O form contém uma <input list> com um <datalist> segundo o site <http://www.w3schools.com/tags/tag_datalist.asp> -->
<!-- **************************************************************************************************************** -->
<body>
	<%
		Usuario user = (Usuario) session.getAttribute("theUsuario");

			List<Conta> contas = (List<Conta>) session
					.getAttribute("contasDoUsuario");

			List<Descricao> descricoes = (List<Descricao>) session
					.getAttribute("descricoesDoUsuario");
	%>
	<div class="div_Cabecalho">
		<div class="div_Cabecalho_titulo">
			<h1>Controle Financeiro Pessoal</h1>
		</div>
		<div class="div_mensgReceptiva">
			<form action="logoff.do" method="post">
				<p>
					Seja Bem-vindo
					<%=user.getNome()%>!
				</p>
				<input type="submit" value="Sair" name="btnLogoff">
			</form>
		</div>
	</div>
	<div class="div_linha">
		<hr>
	</div>
	<div class="div_Funcionalidade">
		<h2>Cadastro de receita</h2>
	</div>

	<div class="div_Conteudo">
		<form action="inserirReceita.do" method="post">
			<div>
				<div class="campo">
					<div class="floatLeft">
						<label for="contas">Informe a conta</label>
					</div>
					<div class="floatRigth">
						<input list="contas" name="contas">
						<datalist id="contas">
							<%
								for (Conta c : contas) {
							%>
							<option value=<%=c.getTitulo()%>>
								<%
									}
								%>
							
						</datalist>
					</div>
				</div>
				<div class="campo">
					<div class="floatLeft">
						<label for="valor">Digite o valor:</label>
					</div>
					<div class="floatRigth">
						<input type="text" name="valor">
					</div>
				</div>
				<div class="campo">
					<div class="floatLeft">
						<label for="descricoes">Informe a descrição:</label>
					</div>
					<div class="floatRigth">
						<input list="descricoes" name="descricoes">
						<datalist id="descricoes">
							<%
								for (Descricao d : descricoes) {
							%>
							<option value=<%=d.getDescricao()%>>
								<%
									}
								%>
							
						</datalist>
					</div>
				</div>
				<div class="campo">
					<div class="floatLeft">
						<label for="dtocorrencia">Digite a data de ocorrência:</label>
					</div>
					<div class="floatRigth">
						<input type="text" name="dtocorrencia">
					</div>
				</div>
				<div class="campo">
					<input type="button" value="Voltar" onclick="back()"> <input
						type="submit" value="Cadastrar">
				</div>
			</div>
		</form>
	</div>
</body>
</html>