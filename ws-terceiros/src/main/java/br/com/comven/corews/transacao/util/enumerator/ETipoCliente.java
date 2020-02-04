package br.com.comven.corews.transacao.util.enumerator;

public enum ETipoCliente {

	REVENDA("RV", "Revenda"), 
	CARTORIO("CR", "Cartorio"),
	LOCADORA("LO", "Locadora"),
	PARCEIRO("PA", "Parceiro"),
	DESPACHANTE("AD", "Despachante"),
	INSTITUICAO_FINANCEIRA("IF", "Instiuição Financeira");

	private String codigo;
	private String descricao;

	private ETipoCliente(String codigo, String descricao) {
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



	public static ETipoCliente fromId(String codigo) {
		for (ETipoCliente value : values()) {
			if (codigo != null && value.codigo.equals(codigo)) {
				return value;
			}
		}
		return null;
	}

}