package br.com.comven.corews.transacao.util.enumerator;

public enum ETipoOperacao {

	COMPRA("1", "Compra"), 
	VENDA("2", "Venda");

	private String codigo;
	private String descricao;

	private ETipoOperacao(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public static ETipoOperacao fromId(String codigo) {
		for (ETipoOperacao value : values()) {
			if (codigo != null && value.codigo.equals(codigo)) {
				return value;
			}
		}
		return null;
	}

}