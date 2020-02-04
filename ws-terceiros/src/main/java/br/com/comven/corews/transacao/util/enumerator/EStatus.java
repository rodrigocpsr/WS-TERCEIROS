package br.com.comven.corews.transacao.util.enumerator;

public enum EStatus {
	
	ATIVO("1"),
	INATIVO("0"),
	EXCLUIDO("9");

	private String status;
	
	private EStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}


