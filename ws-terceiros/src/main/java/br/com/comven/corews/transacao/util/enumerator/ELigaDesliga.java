package br.com.comven.corews.transacao.util.enumerator;

/**
 * Enum que reflete o codigo dos parametros da aplicação 
 * localizados na tabela PARAMETRO
 * 
 * @author Cateno Viglio
 * @since 10/07/2016
 */
public enum ELigaDesliga {
	
	LIGADO("1"),
	DESLIGADO("0");
	
    private String codigo;

    ELigaDesliga(String codigo) {
        this.codigo = codigo;
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

   
}
