package dao;

import model.Usuario;

public interface UsuarioDao {

	public abstract boolean createUsuario(Usuario novo);

	/**
	 * se o usuário existir o método, por referência, carrega-o com todos os seus
	 * dados, isto é, id, receitas e despesas.
	 * 
	 * @param user
	 * @return
	 */
	public abstract boolean usuarioExiste(Usuario user);

}
