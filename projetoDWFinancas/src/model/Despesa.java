package model;

/**
 * Uma Despesa 'e um Fato Financeiro de saida. Tem opcionalmente um tipo de documento
 * provante e o numero deste.
 * 
 * @author joao
 * 
 */
public class Despesa extends FatoFinanceiro {

	private TipoDeDocumentoProvante tipoDeDocumentoProvante;
	private String numeroDoDocumentoProvante;

	public Despesa() {
		super();
	}

	public TipoDeDocumentoProvante getTipoDeDocumentoProvante() {
		return tipoDeDocumentoProvante;
	}

	public void setTipoDeDocumentoProvante(
			TipoDeDocumentoProvante tipoDeDocumentoProvante) {
		this.tipoDeDocumentoProvante = tipoDeDocumentoProvante;
	}

	public String getNumeroDoDocumentoProvante() {
		return numeroDoDocumentoProvante;
	}

	public void setNumeroDoDocumentoProvante(String numeroDoDocumentoProvante) {
		this.numeroDoDocumentoProvante = numeroDoDocumentoProvante;
	}

}
