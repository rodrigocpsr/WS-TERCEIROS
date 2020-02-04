package br.com.comven.corews.transacao.util.enumerator;

public enum EConnectorType {

	APLIC("A"),
	API_SERPRO("P");

	private String codigo;
	
	private EConnectorType(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
}
