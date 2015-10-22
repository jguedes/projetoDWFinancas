package model;

import java.sql.Timestamp;

/**
 * Um Fato Financeiro 'e uma entrada ou saida de uma conta. Tem uma descricao,
 * um valor, opcionalmente um data de ocorrência. O sistema se encarrega de
 * registrar a data de cadastro do Fato Financeiro.
 * 
 * @author joao
 * 
 */
public abstract class FatoFinanceiro {

	private int id;
	private Conta conta;
	private Descricao descricao;
	private double valor;
	private Timestamp dtDeOcorrencia;
	private Timestamp dtCadastro;
	private boolean dead;

	public FatoFinanceiro() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Descricao getDescricao() {
		return descricao;
	}

	public void setDescricao(Descricao descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Timestamp getDtDeOcorrencia() {
		return dtDeOcorrencia;
	}

	public void setDtDeOcorrencia(Timestamp dtDeOcorrencia) {
		this.dtDeOcorrencia = dtDeOcorrencia;
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

}
