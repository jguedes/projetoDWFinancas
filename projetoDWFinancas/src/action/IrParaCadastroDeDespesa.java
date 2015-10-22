package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Conta;
import model.Descricao;
import model.TipoDeDocumentoProvante;
import model.Usuario;
import dao.FabricaConexao;
import dao.JDBCContaDao;
import dao.JDBCDescricaoDao;
import dao.JDBCTipoDeDocumentoProvanteDao;

/**
 * Servlet implementation class IrParaCadastroDeDespesa
 */
@WebServlet("/IrParaCadastroDeDespesa")
public class IrParaCadastroDeDespesa extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IrParaCadastroDeDespesa() {
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

		// pegar o usuário na variável de sessão

		Usuario user = (Usuario) request.getSession()
				.getAttribute("theUsuario");

		/*
		 * Listar as contas, descrições e tipos de documentos provantes do
		 * usuário
		 */
		List<Conta> contasDoUsuario;
		List<Descricao> descricoesDoUsuario;
		List<TipoDeDocumentoProvante> tiposDeDocumentosProvantes;

		FabricaConexao fabrica = new FabricaConexao();

		Connection conn = null;

		try {

			conn = fabrica.getConnection();

			JDBCContaDao daoConta = new JDBCContaDao(conn);

			contasDoUsuario = daoConta.listarContasPorUsuario(user.getId());

			// passar a lista de contas do usuário para uma varáivel de sessão

			request.getSession().setAttribute("contasDoUsuario",
					contasDoUsuario);

			JDBCDescricaoDao daoDescricao = new JDBCDescricaoDao(conn);

			descricoesDoUsuario = daoDescricao.listarDescricaoPorUsuario(user
					.getId());

			// passar a lista de descrições do usuário para uma varáivel de
			// sessão

			request.getSession().setAttribute("descricoesDoUsuario",
					descricoesDoUsuario);

			JDBCTipoDeDocumentoProvanteDao daoTipoDeDocumentoProvante = new JDBCTipoDeDocumentoProvanteDao(
					conn);

			tiposDeDocumentosProvantes = daoTipoDeDocumentoProvante
					.listarTipoDeDecumentoProvantePorUsuario(user.getId());

			// passar a lista de tipos de documentos provantes do usuário para uma varáivel de
			// sessão

			request.getSession().setAttribute("tiposDeDocumentosProvantes",
					tiposDeDocumentosProvantes);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fabrica.closeConnection(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		response.sendRedirect("PaginaDeCadastroDeDespesa.jsp");

	}

}
