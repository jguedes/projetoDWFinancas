package dao;

import java.util.List;

import model.Descricao;

public interface DescricaoDao {

	/**
	 * Lista as descrições do usuário
	 * 
	 * @param usuario_id
	 * @return
	 */
	public abstract List<Descricao> listarDescricaoPorUsuario(int usuario_id);

	/**
	 * Este método deve averiguar a existência da descrição no banco e caso a
	 * tenha encontrado retorná-la ativa.
	 * 
	 * @param usuario_id
	 * @param descricao
	 * @return
	 */
	public abstract Descricao buscarDescricaoDeUsuarioPorNome(int usuario_id,
			String descricao);

	/**
	 * cria a descrição e atribui o id e a data de cadastro do banco nela
	 * 
	 * @param descricao
	 */
	public abstract void cadastrarDescricaoDeUsuario(Descricao descricao);

}
