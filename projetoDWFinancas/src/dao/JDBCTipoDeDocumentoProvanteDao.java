package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.TipoDeDocumentoProvante;

public class JDBCTipoDeDocumentoProvanteDao implements
		TipoDeDocumentoProvanteDao {

	private Connection conn;

	public JDBCTipoDeDocumentoProvanteDao(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<TipoDeDocumentoProvante> listarTipoDeDecumentoProvantePorUsuario(
			int usuario_id) {

		List<TipoDeDocumentoProvante> lista = new ArrayList<TipoDeDocumentoProvante>();

		String st = "select * from tipodocumento where usuario_id = ? and dead = false;";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(st);

			ps.setInt(1, usuario_id);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				TipoDeDocumentoProvante t = new TipoDeDocumentoProvante();

				t.setId(rs.getInt("id"));
				t.setNome(rs.getString("nome"));
				t.setDtCadastro(rs.getTimestamp("dtCadastro"));

				lista.add(t);

			}

			return lista;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public TipoDeDocumentoProvante buscarTipoDeDocumentoProvanteDeUsuarioPorNome(
			int usuario_id, String nome) {

		String st = "select * from tipodocumento where usuario_id = ? and nome = ?;";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(st);

			ps.setInt(1, usuario_id);
			ps.setString(2, nome);

			ResultSet rs = ps.executeQuery();

			TipoDeDocumentoProvante tipoDocumentoEncontrado = null;

			while (rs.next()) {

				tipoDocumentoEncontrado = new TipoDeDocumentoProvante();

				tipoDocumentoEncontrado.setId(rs.getInt("id"));
				tipoDocumentoEncontrado.setNome(rs.getString("nome"));
				tipoDocumentoEncontrado.setDtCadastro(rs
						.getTimestamp("dtcadastro"));

				// se o tipoDocumentoEncontrado estava como excluído, isto é,
				// marcado como
				// dead,
				// deve-se reativá-la
				if (rs.getBoolean("dead")) {
					ps.clearBatch();
					ps.execute("update tipodocumento set dead = false where nome = '"
							+ nome + "' and usuario_id = " + usuario_id + ";");

					tipoDocumentoEncontrado.setDead(false);

				} else {

					tipoDocumentoEncontrado.setDead(rs.getBoolean("dead"));

				}

			}

			return tipoDocumentoEncontrado;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void cadastrarTipoDeDocumentoProvanteDeUsuario(
			TipoDeDocumentoProvante tipoDeDocumentoProvante) {

		String sql = "insert into tipodocumento (usuario_id,nome) values (?,?);";

		PreparedStatement ps;

		try {

			ps = conn.prepareStatement(sql);

			ps.setInt(1, tipoDeDocumentoProvante.getUsuario().getId());
			ps.setString(2, tipoDeDocumentoProvante.getNome());

			ps.execute();// executar a inserção

			// recuperar o id do tipo De Documento Provante

			sql = "select id, dtcadastro from tipodocumento where id = (select LAST_INSERT_ID());";

			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {

				tipoDeDocumentoProvante.setId(rs.getInt("id"));
				tipoDeDocumentoProvante.setDtCadastro(rs
						.getTimestamp("dtcadastro"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}