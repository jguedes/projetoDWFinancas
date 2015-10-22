package dao;

import java.util.List;

import model.Despesa;

public interface DespesaDao {
	/**
	 * Este método deve retornar uma lista de todos as despesas registradas por um usuário
	 * @param usuario_id
	 * @return
	 */
	public abstract List<Despesa> getListaDeDespesa(int usuario_id);
	
	public abstract void cadastrarDespesa(Despesa despesa);
}
