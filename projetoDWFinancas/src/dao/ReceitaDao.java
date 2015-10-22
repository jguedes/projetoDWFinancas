package dao;

import java.util.List;

import model.Receita;

public interface ReceitaDao {

	/**
	 * Este método deve retornar uma lista de todos as receitas registradas por
	 * um usuário
	 * 
	 * @param usuario_id
	 * @return
	 */
	public abstract List<Receita> getListaDeReceita(int usuario_id);

	public abstract void cadastrarReceita(Receita receita);

}
