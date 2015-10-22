package model;

import java.sql.Timestamp;

/**
 * Uma descricao 'e uma justificativa de um Fato Financeiro. Deve ser exclusivo
 * do usuario. Tem uma data de casdastro regitrada pelo sistema.
 * 
 * @author joao
 * 
 */
public class Descricao {

	private int id;
	private Usuario usuario;
	private String descricao;
	private Timestamp dtCadastro;
	private boolean dead;

	public Descricao() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Timestamp getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Timestamp dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
