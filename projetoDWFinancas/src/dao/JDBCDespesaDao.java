package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conta;
import model.Descricao;
import model.Despesa;
import model.TipoDeDocumentoProvante;

public class JDBCDespesaDao implements DespesaDao {

	private Connection conn;

	public JDBCDespesaDao(Connection conn) {

		this.conn = conn;

	}

	@Override
	public List<Despesa> getListaDeDespesa(int usuario_id) {

		/*
		 * Definindo variáveis para o nome dos campos relevantes
		 */
		String despesa_id = "despesa_id", valor = "valor", dtocorrencia = "dtocorrencia", despesa_dtcadastro = "despesa_dtcadastro", descricao_id = "descricao_id", descricao = "descricao", descricao_dtcadastro = "descricao_dtcadastro", conta_id = "conta_id", titulo_conta = "titulo_conta", conta_dtcadastro = "conta_dtcadastro", numero_tipododocumento = "num_tipdoc", tipododocumento_id = "tipododocumento_id", nome_tipododocumento = "nome_tipododocumento", tipododocumento_dtcadastro = "tipododocumento_dtcadastro";

		/*
		 * Passando o nome dos campos para o método que monta a string do select
		 */
		String sql = stSelectdespesasPorUsuario(despesa_id, valor,
				dtocorrencia, despesa_dtcadastro, descricao_id, conta_id,
				descricao, descricao_dtcadastro, titulo_conta,
				conta_dtcadastro, numero_tipododocumento, tipododocumento_id,
				nome_tipododocumento, tipododocumento_dtcadastro);

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, usuario_id);// --> atribuir o usuario_id para a
										// variável do Preparedstatement.

			ResultSet rs = ps.executeQuery();

			List<Despesa> lista = new ArrayList<Despesa>();

			/*
			 * deve-se instanciar os objetos tipo despesa dentro da estrutura de
			 * repetição, pois cada registro do ResultSet é uma nova despesa.
			 */
			while (rs.next()) {

				Despesa r = new Despesa();

				r.setId(rs.getInt(despesa_id));

				r.setDtCadastro(rs.getTimestamp(despesa_dtcadastro));

				r.setDtDeOcorrencia(rs.getTimestamp(dtocorrencia));

				r.setValor(rs.getDouble(valor));

				// Toda despesa entra numa conta
				Conta c = new Conta();

				c.setId(rs.getInt(conta_id));

				c.setTitulo(rs.getString(titulo_conta));

				c.setDtcadastro(rs.getTimestamp(conta_dtcadastro));

				r.setConta(c);

				// Toda despesa tem uma descrição
				Descricao d = new Descricao();

				d.setDescricao(rs.getString(descricao));

				d.setDtCadastro(rs.getTimestamp(descricao_dtcadastro));

				r.setDescricao(d);

				// Toda despesa pode ter um documento provante com seu número
				TipoDeDocumentoProvante t = new TipoDeDocumentoProvante();

				r.setNumeroDoDocumentoProvante(rs
						.getString(numero_tipododocumento));

				t.setId(rs.getInt(tipododocumento_id));

				t.setNome(rs.getString(nome_tipododocumento));

				t.setDtCadastro(rs.getTimestamp(tipododocumento_dtcadastro));

				r.setTipoDeDocumentoProvante(t);

				lista.add(r);

			}

			return lista;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;

		}

	}

	/**
	 * Este método monta o select que consulta as despesas por usuari_id. O
	 * parâmetro usuario_id 'e colocado como variável de PreparedStatemt.
	 * 
	 * @param despesa_id
	 * @param valor
	 * @param dtocorrencia
	 * @param despesa_dtcadastro
	 * @param descricao_id
	 * @param conta_id
	 * @param descricao
	 * @param descricao_dtcadastro
	 * @param titulo_conta
	 * @param conta_dtcadastro
	 * @param tipododocumento_dtcadastro
	 * @param nome_tipododocumento
	 * @param tipododocumento_id
	 * @param numero_tipododocumento
	 * @return
	 */
	private String stSelectdespesasPorUsuario(String despesa_id, String valor,
			String dtocorrencia, String despesa_dtcadastro,
			String descricao_id, String conta_id, String descricao,
			String descricao_dtcadastro, String titulo_conta,
			String conta_dtcadastro, String numero_tipododocumento,
			String tipododocumento_id, String nome_tipododocumento,
			String tipododocumento_dtcadastro) {

		/*
		 * Utiliza-se a classe StringBuilder por ser mais rápida para
		 * concatenação que String.
		 */
		StringBuilder st = new StringBuilder();
		st.append("SELECT ");
		st.append("r.fatofinanceiro_id as ");
		st.append(despesa_id);
		st.append(",");
		st.append("f.valor as ");
		st.append(valor);
		st.append(",");
		st.append("f.dtocorrencia as ");
		st.append(dtocorrencia);
		st.append(",");
		st.append("f.dtcadastro as ");
		st.append(despesa_dtcadastro);
		st.append(",");
		st.append("f.descricao_id as ");
		st.append(descricao_id);
		st.append(",");
		st.append("f.conta_id as ");
		st.append(conta_id);
		st.append(",");
		st.append("d.descricao as ");
		st.append(descricao);
		st.append(",");
		st.append("d.dtcadastro as ");
		st.append(descricao_dtcadastro);
		st.append(",");
		st.append("c.titulo as ");
		st.append(titulo_conta);
		st.append(",");
		st.append("c.dtcadastro as ");
		st.append(conta_dtcadastro);
		st.append(",");
		st.append("r.numerodocumento as ");
		st.append(numero_tipododocumento);
		st.append(",");
		st.append("r.tipodocumento_id as ");
		st.append(tipododocumento_id);
		st.append(",");
		st.append("t.nome as ");
		st.append(nome_tipododocumento);
		st.append(",");
		st.append("t.dtcadastro as ");
		st.append(tipododocumento_dtcadastro);
		st.append(" FROM ");
		st.append("despesa r INNER JOIN(");
		st.append("fatofinanceiro f INNER JOIN ");
		st.append("descricao d INNER JOIN ");
		st.append("conta c INNER JOIN ");
		st.append("tipodocumento t");
		st.append(") ON (");
		st.append("r.fatofinanceiro_id = f.id AND ");
		st.append("f.descricao_id = d.id AND ");
		st.append("f.conta_id = c.id AND ");
		st.append("r.tipodocumento_id = t.id) ");
		st.append("WHERE ");
		st.append("f.dead = FALSE AND ");
		st.append("c.usuario_id = ?;");// --> aqui a variável '?' parâmetro de
										// PreparedStatemnet

		return st.toString();

	}

	@Override
	public void cadastrarDespesa(Despesa despesa) {

		String sql;

		PreparedStatement ps;

		try {

			// rotinas para inserir o fato financeiro

			sql = "insert into fatofinanceiro (conta_id, descricao_id, valor, dtocorrencia) values (?,?,?,?);";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, despesa.getConta().getId());
			ps.setInt(2, despesa.getDescricao().getId());
			ps.setDouble(3, despesa.getValor());
			ps.setTimestamp(4, despesa.getDtDeOcorrencia());

			ps.execute();

			ps.close();

			// rotinas para inserir a despesa
			
			// SELECT LAST_INSERT_ID() Referência:
			// <http://dev.mysql.com/doc/refman/5.0/en/information-functions.html#function_last-insert-id>
			// acessado em 3-12-2013 as 17:45

			sql = "insert into despesa (numerodocumento,tipodocumento_id,fatofinanceiro_id) values (?,?,(SELECT LAST_INSERT_ID()));";

			ps = conn.prepareStatement(sql);

			ps.setString(1, despesa.getNumeroDoDocumentoProvante());
			ps.setInt(2, despesa.getTipoDeDocumentoProvante().getId());

			ps.execute();

			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
