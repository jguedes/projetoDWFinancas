package model;

import java.sql.Timestamp;

/**
 * Uma conta 'e um repositorio de receitas ou uma fonte de saidas. Tem um
 * titulo, uma data de cadastro registrada pelo sistema. Deve ser exclusiva do
 * usuario.
 * 
 * @author joao
 * 
 */
public class Conta {
	private int id;
	private Usuario usuario;
	private String titulo;
	private Timestamp dtcadastro;
	private boolean dead;

	public Conta() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Timestamp getDtcadastro() {
		return dtcadastro;
	}

	public void setDtcadastro(Timestamp dtcadastro) {
		this.dtcadastro = dtcadastro;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
