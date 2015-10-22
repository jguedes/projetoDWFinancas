package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conta;
import model.Descricao;
import model.Receita;

public class JDBCReceitaDao implements ReceitaDao {

	Connection conn;

	public JDBCReceitaDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Receita> getListaDeReceita(int usuario_id) {

		/*
		 * Definindo variaveis para o nome dos campos relevantes
		 */
		String receita_id = "receita_id", valor = "valor", dtocorrencia = "dtocorrencia", receita_dtcadastro = "receita_dtcadastro", descricao_id = "descricao_id", conta_id = "conta_id", descricao = "descricao", descricao_dtcadastro = "descricao_dtcadastro", titulo_conta = "titulo_conta", conta_dtcadastro = "conta_dtcadastro";

		/*
		 * Passando o nome dos campos para o metodo que monta a string do select
		 */
		String st = stSelectReceitasPorUsuario(receita_id, valor, dtocorrencia,
				receita_dtcadastro, descricao_id, conta_id, descricao,
				descricao_dtcadastro, titulo_conta, conta_dtcadastro);

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(st);

			ps.setInt(1, usuario_id);// --> atribuir o usuario_id para a
										// variável.

			ResultSet rs = ps.executeQuery();

			List<Receita> lista = new ArrayList<Receita>();

			/*
			 * deve-se instanciar os obejtos tipo Receita dentro da estrutura de
			 * repetição, pois cada registro do ResultSet é uma nova Receita.
			 */
			while (rs.next()) {

				Receita r = new Receita();

				r.setId(rs.getInt(receita_id));

				r.setDtCadastro(rs.getTimestamp(receita_dtcadastro));

				r.setDtDeOcorrencia(rs.getTimestamp(dtocorrencia));

				r.setValor(rs.getDouble(valor));

				// Toda receita entra numa conta
				Conta c = new Conta();

				c.setId(rs.getInt(conta_id));

				c.setTitulo(rs.getString(titulo_conta));

				c.setDtcadastro(rs.getTimestamp(conta_dtcadastro));

				r.setConta(c);

				// Toda receita tem uma descricao
				Descricao d = new Descricao();

				d.setDescricao(rs.getString(descricao));

				d.setDtCadastro(rs.getTimestamp(descricao_dtcadastro));

				r.setDescricao(d);

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
	 * Este método monta o select que consulta as receitas por usuari_id. O
	 * parametro usuario_id 'e colocado como variavel de PreparedStatemt.
	 * 
	 * @param receita_id
	 * @param valor
	 * @param dtocorrencia
	 * @param receita_dtcadastro
	 * @param descricao_id
	 * @param conta_id
	 * @param descricao
	 * @param descricao_dtcadastro
	 * @param titulo_conta
	 * @param conta_dtcadastro
	 * @return
	 */
	private String stSelectReceitasPorUsuario(String receita_id, String valor,
			String dtocorrencia, String receita_dtcadastro,
			String descricao_id, String conta_id, String descricao,
			String descricao_dtcadastro, String titulo_conta,
			String conta_dtcadastro) {

		/*
		 * Utiliza-se a classe StringBuilder por ser mais rápida para
		 * concatenação que String.
		 */
		StringBuilder st = new StringBuilder();
		st.append("SELECT ");
		st.append("r.fatofinanceiro_id as ");
		st.append(receita_id);
		st.append(",");
		st.append("f.valor as ");
		st.append(valor);
		st.append(",");
		st.append("f.dtocorrencia as ");
		st.append(dtocorrencia);
		st.append(",");
		st.append("f.dtcadastro as ");
		st.append(receita_dtcadastro);
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
		st.append(" FROM ");
		st.append("receita r INNER JOIN(");
		st.append("fatofinanceiro f INNER JOIN ");
		st.append("descricao d INNER JOIN ");
		st.append("conta c");
		st.append(") ON (");
		st.append("r.fatofinanceiro_id = f.id AND ");
		st.append("f.descricao_id = d.id AND ");
		st.append("f.conta_id = c.id) ");
		st.append("WHERE ");
		st.append("f.dead = FALSE AND ");
		st.append("c.usuario_id = ?;");// --> aqui a variavel '?' parametro de
										// PreparedStatemnet

		return st.toString();

	}

	@Override
	public void cadastrarReceita(Receita receita) {

		String sql;

		PreparedStatement ps;

		try {

			// rotinas para inserir o fato financeiro

			sql = "insert into fatofinanceiro (conta_id, descricao_id, valor, dtocorrencia) values (?,?,?,?);";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, receita.getConta().getId());
			ps.setInt(2, receita.getDescricao().getId());
			ps.setDouble(3, receita.getValor());
			ps.setTimestamp(4, receita.getDtDeOcorrencia());

			ps.execute();

			// rotinas para inserir a despesa

			// SELECT LAST_INSERT_ID() Referência:
			// <http://dev.mysql.com/doc/refman/5.0/en/information-functions.html#function_last-insert-id>
			// acessado em 3-12-2013 as 17:45

			sql = "insert into receita (fatofinanceiro_id) values ((SELECT LAST_INSERT_ID()));";

			ps.execute(sql);

			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
