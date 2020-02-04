package br.com.comven.corews.transacao.bean;

public class ErroBean {
	
	private String mensagem;

	public ErroBean(String message) {
		this.mensagem = message;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
