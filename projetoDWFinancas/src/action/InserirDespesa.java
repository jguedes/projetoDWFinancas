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
import model.Despesa;
import model.TipoDeDocumentoProvante;
import model.Usuario;
import dao.FabricaConexao;
import dao.JDBCContaDao;
import dao.JDBCDescricaoDao;
import dao.JDBCDespesaDao;
import dao.JDBCTipoDeDocumentoProvanteDao;

/**
 * Servlet implementation class InserirDespesa
 */
@WebServlet("/InserirDespesa")
public class InserirDespesa extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Usuario user;
	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InserirDespesa() {
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

		// instanciando a despesa para inserir
		Despesa d = new Despesa();

		// pegando os dados da despesa

		String tituloDeContaInformado = request.getParameter("contas");

		String descricaoInformada = request.getParameter("descricoes");

		double valorInformado = Double.valueOf(request.getParameter("valor"));

		Timestamp dtOcorreciaInformada = Timestamp.valueOf(request
				.getParameter("dtocorrencia"));// yyyy-mm-dd hh:mm:ss

		String tipoDocumentoInformado = request
				.getParameter("tiposdedocumentosprovantes");

		String numeroDocumentoInformado = request
				.getParameter("numerotipodocumentoprovante");

		FabricaConexao fabrica = new FabricaConexao();

		conn = null;

		try {

			conn = fabrica.getConnection();

			//carregar dados da despesa
			
			d.setConta(contaInformada(tituloDeContaInformado));

			d.setDescricao(descricaoInformada(descricaoInformada));

			d.setTipoDeDocumentoProvante(tipoDeDocumentoProvanteInformado(tipoDocumentoInformado));

			d.setValor(valorInformado);
			
			d.setNumeroDoDocumentoProvante(numeroDocumentoInformado);
			
			d.setDtDeOcorrencia(dtOcorreciaInformada);

			// inserir a despesa

			JDBCDespesaDao daoDespesa = new JDBCDespesaDao(conn);

			daoDespesa.cadastrarDespesa(d);

			// atualizar lista de despesas do usuário

			user.setDespesas(daoDespesa.getListaDeDespesa(user.getId()));

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
	 * Averiguar se o tipo de documento comprovante informado existe. se não
	 * existir, cadastrar automaticamente novo tipo de documento comprovante.
	 * 
	 * @param tipoDocumentoInformado
	 * @return
	 */
	private TipoDeDocumentoProvante tipoDeDocumentoProvanteInformado(
			String tipoDocumentoInformado) {

		JDBCTipoDeDocumentoProvanteDao daoTipoDeDocumentoProvante = new JDBCTipoDeDocumentoProvanteDao(
				conn);

		TipoDeDocumentoProvante tipoDeDocumentoProvante = null;

		if ((tipoDeDocumentoProvante = daoTipoDeDocumentoProvante
				.buscarTipoDeDocumentoProvanteDeUsuarioPorNome(user.getId(),
						tipoDocumentoInformado)) == null) {

			tipoDeDocumentoProvante = new TipoDeDocumentoProvante();

			tipoDeDocumentoProvante.setNome(tipoDocumentoInformado);

			tipoDeDocumentoProvante.setUsuario(user);

			daoTipoDeDocumentoProvante
					.cadastrarTipoDeDocumentoProvanteDeUsuario(tipoDeDocumentoProvante);

		}

		return tipoDeDocumentoProvante;
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
