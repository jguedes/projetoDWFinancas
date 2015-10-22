package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;

public class JDBCUsuarioDao implements UsuarioDao {

	private Connection conn;

	public JDBCUsuarioDao(Connection conn) {

		this.conn = conn;

	}

	@Override
	public boolean createUsuario(Usuario novo) {

		String sql;

		PreparedStatement ps;

		try {

			// INSERIR NOVO USUARIO
			sql = "insert into usuario (nome,email,senha) values (?,?,?);";

			ps = conn.prepareStatement(sql);

			ps.setString(1, novo.getNome());
			ps.setString(2, novo.getEmail());
			ps.setString(3, novo.getSenha());

			ps.execute();

			// CONSULTAR USUARIO INSERIDO PARA PEGAR SEU ID

			// sql =
			// "select id from usuario where nome like ? and email like ? and senha like ?";

			sql = "SELECT LAST_INSERT_ID();";// Referência:
												// <http://dev.mysql.com/doc/refman/5.0/en/information-functions.html#function_last-insert-id>
												// acessado em 3-12-2013 as
												// 17:45

			ps = conn.prepareStatement(sql);

			// ps.setString(1, novo.getNome());
			// ps.setString(2, novo.getEmail());
			// ps.setString(3, novo.getSenha());

			ResultSet rs = ps.executeQuery();

			rs.last();// -->vai para o ultimo registro do ResultSet

			novo.setId(rs.getInt(1));// -->pegando na coluna 1, na verdade a
										// única, o ID do novo usuário

			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;

		}

	}

	@Override
	public boolean usuarioExiste(Usuario user) {

		String sql = "SELECT * FROM usuario WHERE email LIKE ? AND senha LIKE ? AND dead = false";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(sql);

			ps.setString(1, user.getEmail());
			ps.setString(2, user.getSenha());

			ResultSet rs = ps.executeQuery();

			// carrgar os dados do usuário encontrado por referência
			while (rs.next()) {

				if (rs.getString("email").equalsIgnoreCase(user.getEmail())) {

					user.setNome(rs.getString("nome"));
					user.setId(rs.getInt("id"));
					user.setDtCadastro(rs.getTimestamp("dtcadastro"));

					// pegar as receitas
					JDBCReceitaDao daoReceita = new JDBCReceitaDao(conn);
					user.setReceitas(daoReceita.getListaDeReceita(user.getId()));

					// pegar as despesas
					JDBCDespesaDao daoDespesa = new JDBCDespesaDao(conn);
					user.setDespesas(daoDespesa.getListaDeDespesa(user.getId()));

					return true;

				}

			}

			return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;

		}

	}

}
