package dao;

import java.util.List;

import model.TipoDeDocumentoProvante;

public interface TipoDeDocumentoProvanteDao {

	public abstract List<TipoDeDocumentoProvante> listarTipoDeDecumentoProvantePorUsuario(
			int usuario_id);

	
	public abstract TipoDeDocumentoProvante buscarTipoDeDocumentoProvanteDeUsuarioPorNome(
			int usuario_id, String nome);
	
	public abstract void cadastrarTipoDeDocumentoProvanteDeUsuario(TipoDeDocumentoProvante tipoDeDocumentoProvante);
	
}
