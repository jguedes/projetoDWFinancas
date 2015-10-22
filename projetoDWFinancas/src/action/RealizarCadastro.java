package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FabricaConexao;
import dao.JDBCUsuarioDao;
import model.Usuario;

/**
 * Servlet implementation class RealizarCadastro
 */
@WebServlet("/RealizarCadastro")
public class RealizarCadastro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RealizarCadastro() {
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
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		Usuario u = new Usuario();
		u.setNome(nome);
		u.setEmail(email);
		u.setSenha(senha);

		FabricaConexao fabrica = new FabricaConexao();

		Connection conn = null;

		try {
			conn = fabrica.getConnection();

			JDBCUsuarioDao dao = new JDBCUsuarioDao(conn);

			if(dao.createUsuario(u)){

			request.getSession().setAttribute("theUsuario", u);

			System.out.println("Usuário: [ " + u.getNome().toUpperCase()
					+ ", ID: " + u.getId() + " ] cadastrado com sucesso!");

			response.sendRedirect("PaginaPrincipal.jsp");
			
			}

			fabrica.closeConnection(conn);


		} catch (SQLException e) {

			response.sendRedirect("PaginaDeCadastro.jsp");

		}
	}

}
