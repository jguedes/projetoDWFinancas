<%@page import="java.text.DateFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>
<%@page import="model.Usuario"%>
<%@page import="model.Receita"%>
<%@page import="model.Despesa"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="estilos.css">
<title>Controle Financeiro Pessoal</title>
</head>
<body>
	<%!Usuario user;
	int quantReceita = 0;
	int quantDespesa = 0;
	double totalReceita = 0.0;
	double totalDespesa = 0.0;
	NumberFormat moeda = NumberFormat.getCurrencyInstance();
	DateFormat data = DateFormat.getDateInstance();%>

	<%
		quantReceita = 0;
			quantDespesa = 0;
			totalReceita = 0.0;
			totalDespesa = 0.0;

		user = (Usuario) session.getAttribute("theUsuario");

		if (user == null) {
			response.sendRedirect("PaginaDeLogin.jsp");
			return;
		}

		if (session.getAttribute("cadastrarReceita") == null) {
			session.setAttribute("cadastrarReceita", false);
		}

		for (Receita r : user.getReceitas()) {
			quantReceita++;
			totalReceita += r.getValor();
		}

		for (Despesa d : user.getDespesas()) {
			quantDespesa++;
			totalDespesa += d.getValor();
		}
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
		<h2>Situação atual</h2>
	</div>
	<div class="div_Conteudo">
		<div>
			<h2>
				Saldo:
				<%=moeda.format(totalReceita - totalDespesa)%></h2>
		</div>
		<div class="div_Central">

			<div class="div_Receita">
				<div class="div_menu_receita">
					<form action="irParaCadastroDeReceita.do" method="post">
						<input type="submit" value="Inserir Receita">
					</form>
				</div>
				<div class="div_table_receita">
					<table border="1">
						<tr>
							<td colspan="2"><b>Receitas</b><br>Quantidade: <%=quantReceita%>
								| Total: <%=moeda.format(totalReceita)%></td>
						</tr>
						<tr>
							<th>Informações gerais</th>
							<th>Valor</th>
						</tr>
						<%
							for (Receita r : user.getReceitas()) {
						%>
						<tr>
							<td class="td_infGerais"><b>Código: </b><%=r.getId()%><br>
								<b>Conta: </b><%=r.getConta().getTitulo()%><br> <b>Descrição:
							</b><%=r.getDescricao().getDescricao()%><br> <b>Data de
									ocorrência: </b><%=data.format(r.getDtDeOcorrencia())%><br> <b>Data
									de cadastro no sistema: </b><%=data.format(r.getDtCadastro())%></td>
							<td class="td_valor"><%=moeda.format(r.getValor())%></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
			</div>
			<div class="div_Despesa">
				<div class="div_menu_despesa">
					<form action="irParaCadastroDeDespesa.do" method="post">
						<input type="submit" value="Inserir Despesa">
					</form>
				</div>
				<div class="div_table_despesa">
					<table border="1">
						<tr>
							<td colspan="2"><b>Despesas</b><br>Quantidade: <%=quantDespesa%>
								| Total: <%=moeda.format(totalDespesa)%></td>
						</tr>
						<tr>
							<th>Informações gerais</th>
							<th>Valor</th>
						</tr>
						<%
							for (Despesa r : user.getDespesas()) {
						%>
						<tr>
							<td class="td_infGerais"><b>Código: </b><%=r.getId()%><br>
								<b>Conta: </b><%=r.getConta().getTitulo()%><br> <b>Descrição:
							</b><%=r.getDescricao().getDescricao()%><br> <b>Doc.
									Provante/Nº: </b><%=r.getTipoDeDocumentoProvante().getNome()%>/<%=r.getNumeroDoDocumentoProvante()%><br>
								<b>Data de ocorrência: </b><%=data.format(r.getDtDeOcorrencia())%><br>
								<b>Data de cadastro no sistema: </b><%=data.format(r.getDtCadastro())%></td>
							<td class="td_valor"><%=moeda.format(r.getValor())%></td>
						</tr>
						<%
							}
						%>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>