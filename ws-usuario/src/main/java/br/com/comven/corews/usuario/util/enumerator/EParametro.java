package br.com.comven.corews.usuario.util.enumerator;

/**
 * Enum que reflete o codigo dos parametros da aplicação 
 * localizados na tabela PARAMETRO
 * 
 * @author Cateno Viglio
 * @since 10/07/2016
 */
public enum EParametro {
	
	AMBIENTE("AMBIENTE");
	
    private String codigo;

    EParametro(String codigo) {
        this.codigo = codigo;
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

   
}
