package action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Usuario;
import dao.FabricaConexao;
import dao.JDBCUsuarioDao;

/**
 * Servlet implementation class ReRe
 */
@WebServlet("/RealizarLogin")
public class RealizarLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RealizarLogin() {
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

		Connection conn = null;

		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		if (camposEstaoVazios(email, senha)) {

			mensagemDeErro(request, response, "campovazio");
			System.out.println("Erro - Campo vazio");
			return;
		}

		Usuario user = new Usuario();

		user.setEmail(email);
		user.setSenha(senha);

		FabricaConexao fabricaConexoes = new FabricaConexao();

		JDBCUsuarioDao dao = null;

		try {

			conn = fabricaConexoes.getConnection();

			dao = new JDBCUsuarioDao(conn);

			request.getSession().setAttribute("theUsuario", user);

			if (dao.usuarioExiste(user)) {

				response.sendRedirect("PaginaPrincipal.jsp");

				System.out.println("Login realizado com sucesso!");

			} else {

				mensagemDeErro(request, response, "contanaoexiste");

				System.out.println("Login não realizado! Infelizmente");

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (conn != null) {

				try {
					fabricaConexoes.closeConnection(conn);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	private void mensagemDeErro(HttpServletRequest request,
			HttpServletResponse response, String erro) throws IOException {

		request.getSession().setAttribute("Erro", erro);
		response.sendRedirect("PaginaDeLogin.jsp");

	}

	private boolean camposEstaoVazios(String email, String senha) {

		return ((email == "") || (senha == "") ? true : false);

	}

}
