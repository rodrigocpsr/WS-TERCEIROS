package br.com.comven.corews.usuario.util.enumerator;

public enum ETipoToken {
	
	SYSTEM_GRANT("SYSTEM_GRANT");
	

    private String codigo;

    ETipoToken(String codigo) {
        this.codigo = codigo;
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

   
}
