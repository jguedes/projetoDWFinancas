package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Descricao;

public class JDBCDescricaoDao implements DescricaoDao {

	private Connection conn;

	public JDBCDescricaoDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Descricao> listarDescricaoPorUsuario(int usuario_id) {

		List<Descricao> lista = new ArrayList<Descricao>();

		String st = "select * from descricao where usuario_id = ? and dead = false;";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(st);

			ps.setInt(1, usuario_id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				Descricao d = new Descricao();

				d.setId(rs.getInt("id"));
				d.setDescricao(rs.getString("descricao"));
				d.setDtCadastro(rs.getTimestamp("dtCadastro"));

				lista.add(d);

			}

			return lista;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public Descricao buscarDescricaoDeUsuarioPorNome(int usuario_id,
			String descricao) {

		String st = "select * from descricao where usuario_id = ? and descricao = ?;";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(st);

			ps.setInt(1, usuario_id);
			ps.setString(2, descricao);

			ResultSet rs = ps.executeQuery();

			Descricao descricaoEncontrada = null;

			while (rs.next()) {

				descricaoEncontrada = new Descricao();

				descricaoEncontrada.setId(rs.getInt("id"));
				descricaoEncontrada.setDescricao(rs.getString("descricao"));
				descricaoEncontrada
						.setDtCadastro(rs.getTimestamp("dtcadastro"));

				// se a descrição estava como excluída, isto é, marcada como
				// dead,
				// deve-se reativá-la
				if (rs.getBoolean("dead")) {
					ps.clearBatch();
					ps.execute("update descricao set dead = false where titulo = '"
							+ descricao
							+ "' and usuario_id = "
							+ usuario_id
							+ ";");

					descricaoEncontrada.setDead(false);

				} else {

					descricaoEncontrada.setDead(rs.getBoolean("dead"));

				}

			}

			return descricaoEncontrada;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void cadastrarDescricaoDeUsuario(Descricao descricao) {

		String sql = "insert into descricao (usuario_id,descricao) values (?,?);";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, descricao.getUsuario().getId());
			ps.setString(2, descricao.getDescricao());

			ps.execute();// executar a inserção

			// recuperar o id da descrição

			sql = "select id,dtcadastro from descricao where id = (select LAST_INSERT_ID());";

			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				descricao.setId(rs.getInt("id"));
				descricao.setDtCadastro(rs.getTimestamp("dtcadastro"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
