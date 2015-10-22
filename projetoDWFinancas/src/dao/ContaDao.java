package dao;

import java.util.List;

import model.Conta;

public interface ContaDao {

	/**
	 * Lista as contas por usuário
	 * 
	 * @param usuario_id
	 * @return
	 */
	public abstract List<Conta> listarContasPorUsuario(int usuario_id);
	
	/**
	 * recebe o indentificador do usuario e o titulo da conta. se a conta existe retorna a encotrada
	 * @param conta
	 * @return
	 */
	public abstract Conta buscarContaDeUsuarioPorNome(int usuario_id,String titulo);
	
	/**
	 * cria a conta e atribui o id e a data de cadastro do banco nela
	 * @param conta
	 */
	public abstract void cadastrarContaDeUsuario(Conta conta);

}
