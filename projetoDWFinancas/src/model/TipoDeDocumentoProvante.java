package model;

import java.sql.Timestamp;

/**
 * Um Tipo de documento provante possui um nome, uma data de cadastro registrada
 * pelo sistema. Deve ser exclusivo do usuario.
 * 
 * @author joao
 * 
 */
public class TipoDeDocumentoProvante {
	private int id;
	private Usuario usuario;
	private String nome;
	private Timestamp dtcadastro;
	private boolean dead;

	public TipoDeDocumentoProvante() {

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Timestamp getDtcadastro() {
		return dtcadastro;
	}

	public void setDtCadastro(Timestamp dtcadastro) {
		this.dtcadastro = dtcadastro;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
