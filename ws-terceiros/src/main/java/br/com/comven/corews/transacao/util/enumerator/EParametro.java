package br.com.comven.corews.transacao.util.enumerator;

/**
 * Enum que reflete o codigo dos parametros da aplicação 
 * localizados na tabela PARAMETRO
 * 
 * @author Cateno Viglio
 * @since 10/07/2016
 */
public enum EParametro {
	
	AMBIENTE("APLIC_AMBIENTE"),
	CARACTERES_PERMITIDOS("CARACTERES_PERMITIDOS"),
	CONECTOR("CONECTOR"),
	TESTE_MODE_SERPRO("TESTE_MODE_SERPRO"),
	CODIGO_RETORNO_TESTE_MODE("CODIGO_RETORNO_TESTE_MODE"),
	T925_AUTOMATICA("T925_AUTOMATICA"),
	WS_PRE_PAGO("WS_PRE_PAGO"),
	WS_PRE_PAGO_CPF("WS_PRE_PAGO_CPF");
	
	public static final String NAME = "parametro";
	
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
