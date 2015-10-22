package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conta;

public class JDBCContaDao implements ContaDao {

	private Connection conn;

	public JDBCContaDao(Connection conn) {

		System.out.println("Instanciando ");

		this.conn = conn;
	}

	@Override
	public List<Conta> listarContasPorUsuario(int usuario_id) {

		List<Conta> lista = new ArrayList<Conta>();

		String st = "select * from conta where usuario_id = ? and dead = false;";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(st);

			ps.setInt(1, usuario_id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Conta c = new Conta();

				c.setId(rs.getInt("id"));
				c.setTitulo(rs.getString("titulo"));
				c.setDtcadastro(rs.getTimestamp("dtcadastro"));

				lista.add(c);

			}

			return lista;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Conta buscarContaDeUsuarioPorNome(int usuario_id, String titulo) {

		String st = "select * from conta where usuario_id = ? and titulo = ?;";

		System.out.println(st);

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(st);

			ps.setInt(1, usuario_id);
			ps.setString(2, titulo);

			ResultSet rs = ps.executeQuery();

			Conta contaEncontrada = null;

			while (rs.next()) {

				contaEncontrada = new Conta();

				contaEncontrada.setId(rs.getInt("id"));
				contaEncontrada.setTitulo(rs.getString("titulo"));
				contaEncontrada.setDtcadastro(rs.getTimestamp("dtcadastro"));

				// se a conta estava como excluída, isto é, marcada como dead,
				// deve-se reativá-la
				if (rs.getBoolean("dead")) {
					ps.clearBatch();
					ps.execute("update conta set dead = false where titulo = '"
							+ titulo + "' and usuario_id = " + usuario_id + ";");

					contaEncontrada.setDead(false);
				} else {
					contaEncontrada.setDead(rs.getBoolean("dead"));
				}

			}

			return contaEncontrada;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void cadastrarContaDeUsuario(Conta conta) {

		String sql = "insert into conta (usuario_id,titulo) values (?,?);";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, conta.getUsuario().getId());
			ps.setString(2, conta.getTitulo());

			ps.execute();// executar a inserção

			// recuperar o id da conta

			sql = "select id, dtcadastro from conta where id = (select LAST_INSERT_ID());";

			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				conta.setId(rs.getInt("id"));
				conta.setDtcadastro(rs.getTimestamp("dtcadastro"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
