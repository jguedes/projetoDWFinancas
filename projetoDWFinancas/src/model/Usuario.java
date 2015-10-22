package model;

import java.sql.Timestamp;
import java.util.List;

/**
 * Um usuario tem nome, e-mail, senha. Um usuario realiza registro de suas
 * receitas e suas despesas. O sistema se encarrega de registrar a data de
 * cadastro do usuario.
 * 
 * @author joao
 * 
 */
public class Usuario {

	private int id;
	private String nome;
	private String email;
	private String senha;
	private List<Receita> receitas;
	private List<Despesa> despesas;
	private Timestamp dtCadastro;

	public Usuario() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}

	public List<Despesa> getDespesas() {
		return despesas;
	}

	public void setDespesas(List<Despesa> despesas) {
		this.despesas = despesas;
	}

	public Timestamp getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Timestamp dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

}
