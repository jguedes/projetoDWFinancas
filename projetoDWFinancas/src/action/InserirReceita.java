package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Conta;
import model.Descricao;
import model.Receita;
import model.Usuario;
import dao.FabricaConexao;
import dao.JDBCContaDao;
import dao.JDBCDescricaoDao;
import dao.JDBCReceitaDao;

/**
 * Servlet implementation class InserirReceita
 */
@WebServlet("/InserirReceita")
public class InserirReceita extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario user;
	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InserirReceita() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		user = (Usuario) request.getSession().getAttribute("theUsuario");

		// instanciando a receita para inserir
		Receita r = new Receita();

		// pegando os dados da receita

		String tituloDeContaInformado = request.getParameter("contas");

		String descricaoInformada = request.getParameter("descricoes");

		double valorInformado = Double.valueOf(request.getParameter("valor"));

		Timestamp dtOcorreciaInformada = Timestamp.valueOf(request
				.getParameter("dtocorrencia"));// yyyy-mm-dd hh:mm:ss

		FabricaConexao fabrica = new FabricaConexao();

		conn = null;

		try {

			conn = fabrica.getConnection();

			// carregar dados da receita

			r.setConta(contaInformada(tituloDeContaInformado));

			r.setDescricao(descricaoInformada(descricaoInformada));

			r.setValor(valorInformado);

			r.setDtDeOcorrencia(dtOcorreciaInformada);

			// inserir a receita

			JDBCReceitaDao daoReceita = new JDBCReceitaDao(conn);

			daoReceita.cadastrarReceita(r);

			// atualizar lista de receitas do usuário

			user.setReceitas(daoReceita.getListaDeReceita(user.getId()));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			fabrica.closeConnection(conn);

			// voltar a página principal

			response.sendRedirect("PaginaPrincipal.jsp");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * averiguar se a descrição informada existe. se não existir, cadastrar
	 * automaticamente nova descrição.
	 * 
	 * @param descricaoInformada
	 * @param conn
	 * @return
	 */
	private Descricao descricaoInformada(String descricaoInformada) {

		JDBCDescricaoDao daoDescricao = new JDBCDescricaoDao(conn);

		Descricao descricao = daoDescricao.buscarDescricaoDeUsuarioPorNome(
				user.getId(), descricaoInformada);

		if (descricao == null) {

			descricao = new Descricao();

			descricao.setDescricao(descricaoInformada);

			descricao.setUsuario(user);

			daoDescricao.cadastrarDescricaoDeUsuario(descricao);

		}

		return descricao;
	}

	/**
	 * averiguar se a conta informada existe. se não existir, cadastrar
	 * automaticamente nova conta
	 * 
	 * @param tituloDeContaInformado
	 * @param conn
	 * @return
	 */
	private Conta contaInformada(String tituloDeContaInformado) {

		JDBCContaDao daoConta = new JDBCContaDao(conn);

		Conta conta = daoConta.buscarContaDeUsuarioPorNome(user.getId(),
				tituloDeContaInformado);

		if (conta == null) {

			conta = new Conta();

			conta.setTitulo(tituloDeContaInformado);

			conta.setUsuario(user);

			daoConta.cadastrarContaDeUsuario(conta);

		}

		return conta;
	}

}