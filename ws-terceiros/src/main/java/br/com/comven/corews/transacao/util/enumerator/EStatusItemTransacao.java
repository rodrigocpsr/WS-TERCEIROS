package br.com.comven.corews.transacao.util.enumerator;

public enum EStatusItemTransacao {
	AGUARDANDO_ENVIO("AGE"),
	ENVIADO("ENV"),
	PROCESSADO("PRC"),
	PROCESSADO_COM_ERRO("PCE"),
	CANCELADO("CAN"),
	CANCELADO_DETRAN("DET");

	private String status;
	
	private EStatusItemTransacao(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}


