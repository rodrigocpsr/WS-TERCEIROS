package br.com.comven.corews.transacao.util;

public class Constantes {
	
	public static final String LOG4J_CLASS = "br.com.comven.comvenws.transacao";
	
	public final static String LIXO_RETORNO_SERPRO = "BR";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String[] EMAILS_FEEDBACK = new String[]{"cviglio@metatron.com.br", "doliveira@metatron.com.br"};
	public static final String EMAIL_SUPORTE = "suporte@autenticis.com.br";
	
	public static final Integer TIPO_TRANSACAO_COMPRA = 1;
	public static final Integer TIPO_TRANSACAO_VENDA = 2;
	
	// tipos de carga de dados
	public static final String TIPO_CARGA_VEICULO = "V";
	public static final String TIPO_CARGA_MUNICIPIO = "M";
	public static final String TIPO_CARGA_FAIXA_CEP = "F";
	
	public static final String SIM = "Sim";
	public static final String NAO = "Não";
	
	public static final Integer RENAVAM_NOVE_ALGARISMOS = 9;
	public static final Integer RENAVAM_ONZE_ALGARISMOS = 11;
	
	public static final int TIPO_DOCUMENTO_CPF = 1;
	public static final int TIPO_DOCUMENTO_CNPJ = 2;
	
	//cartorio febranor
	public static final String NOME_CARTORIO_FEBRANOR = "FEBRANOR";
	public static final String CEP_CARTORIO_FEBRANOR = "70340000";
	public static final String CIDADE_CARTORIO_FEBRANOR = "BRASILIA";
	public static final String BAIRRO_CARTORIO_FEBRANOR = "ASA SUL";
	public static final String LOGRADOURO_CARTORIO_FEBRANOR = "SRTVS QD701 LT 05 BL A SL 601";
	public static final String COMPLEMENTO_CARTORIO_FEBRANOR = null;
	public static final String NUMERO_CARTORIO_FEBRANOR = "604";
	public static final String UF_CARTORIO_FEBRANOR = "DF";
	public static final String DDD_CARTORIO_FEBRANOR = "61";
	public static final String TEL_CARTORIO_FEBRANOR = "033231555";
	public static final String CNPJ_CARTORIO_FEBRANOR = "06293184000101";
	
	public static final int ETAPA_MIGRACAO_NAO_INICIADA = 0;
	public static final int ETAPA_MIGRACAO_INICIADA = 1;
	public static final int ETAPA_MIGRACAO_FINALIZADA = 2;
	public static final int ETAPA_MIGRACAO_LOGIN_SIMPLES = 3;	
	
	public static final int TELA_LOGIN_MIGRACAO_CERT = 1;
	public static final int TELA_LOGIN_POS_MIGRACAO_CERT = 2;
	public static final int TELA_LOGIN_SIMPLES = 3;
	
	//parametros
	public static final String PARAMETRO_HORA_LIMITE_DEMO = "HORA_LIMITE_DEMO";
	public static final String PARAMETRO_APLIC_AMBIENTE = "APLIC_AMBIENTE";
	public static final String PARAMETRO_SMTP = "SMTP";
	public static final String PARAMETRO_SMTP_LOGIN = "SMTP_LOGIN";
	public static final String PARAMETRO_SMTP_SENHA = "SMTP_SENHA";
	public static final String PARAMETRO_EMAIL_AUTENTICIS = "EMAIL_AUTENTICIS";
	public static final String PARAMETRO_DIRETORIO_DOWNLOAD = "DIRETORIO_DOWNLOAD";
	public static final String PARAMETRO_TEMPO_LIMITE_234 = "TEMPO_LIMITE_234";
	public static final String PARAMETRO_SEPARADOR_LINHA = "SEPARADOR_LINHA";
	public static final String PARAMETRO_TESTE_MODE_SERPRO = "TESTE_MODE_SERPRO";
	public static final String PARAMETRO_MANUTENCAO_MODE = "MODO_MANUTENCAO";
	public static final String PARAMETRO_PATH_EXPORT = "PATH_EXPORT_TRANSACAO";
	public static final String PARAMETRO_PATH_QWARE_BACKUP = "PATH_QWARE_BACKUP";
	public static final String PARAMETRO_DT_INICIO_DEMO_VENDEDORES = "DT_INICIO_DEMO_VENDEDORES";
	public static final String PARAMETRO_TIMEOUT_DEMO_233 = "TIMEOUT_DEMO_233";
	public static final String PARAMETRO_ID_GRUPO_EMPRESARIAL_VENDEDORES = "ID_GRUPO_EMPRESARIAL_VENDEDORES";
	public static final String PARAMETRO_MESSAGE_NEW_PASSWORD = "MESSAGE_NEW_PASSWORD";
	public static final String DIGITOS_NUM_CRV = "DIGITOS_NUM_CRV";
	public static final String PARAMETRO_MENSAGEM_INC_DEMONSTRACAO = "MENSAGEM_INC_DEMONSTRACAO";
	public static final String PARAMETRO_LINK_APLICAO = "LINK_APLICAO";
	public static final String PARAMETRO_GRUPO_PARCEIRO_WS = "GRUPO_PARCEIRO_WS";
	public static final String PARAMETRO_VALOR_MIN_TRANSACAO = "VALOR_MIN_TRANSACAO";
	public static final String PARAMETRO_DATA_NOVO_LOG_LOTE = "DATA_NOVO_LOG_LOTE";
	public static final String DIGITOS_RENAVAM = "DIGITOS_RENAVAM";
	public static final String PARAMETRO_MIGRACAO_CERT_PERIODO_INICIAL = "MIGRACAO_CERT_PERIODO_INICIAL";
	public static final String PARAMETRO_MIGRACAO_CERT_PERIODO_FINAL = "MIGRACAO_CERT_PERIODO_FINAL";
	public static final String PARAMETRO_FLAG_AUTENTICACAO_SIMPLES = "FLAG_AUTENTICACAO_SIMPLES";
	public static final String PARAMETRO_DIRETORIO_ASSINATURA_DIGITAL = "DIRETORIO_ASSINATURA_DIGITAL";
	public static final String PARAMETRO_FLAG_ENVIO_SENHA_USUARIO = "FLAG_ENVIO_SENHA_USUARIO";
	public static final String PARAMETRO_MAX_MES_RELATORIO_TRANSACAO = "MAX_MES_RELATORIO_TRANSACAO";
	public static final String PARAMETRO_PATH_BACKUP_IMPORTACAO = "PATH_BACKUP_IMPORTACAO";
	public static final String PARAMETRO_CODIGO_RETORNO_TESTE_MODE = "CODIGO_RETORNO_TESTE_MODE";
	public static final String PARAMETRO_CARACTERES_PERMITIDOS= "CARACTERES_PERMITIDOS";
	public static final String PARAMETRO_CONECTOR= "CONECTOR";
	public static final String PARAMETRO_T925_AUTOMATICA = "T925_AUTOMATICA";
	public static final String PARAMETRO_CERTISIGN_PROXY_FLAG = "CERTISIGN_PROXY_FLAG";
	public static final String PARAMETRO_CERTISIGN_PROXY_HOST = "CERTISIGN_PROXY_HOST";
	public static final String PARAMETRO_CERTISIGN_PROXY_PORTA = "CERTISIGN_PROXY_PORTA";
	public static final String PARAMETRO_CERTISIGN_PROXY_USUARIO = "CERTISIGN_PROXY_USUARIO";
	public static final String PARAMETRO_CERTISIGN_PROXY_SENHA = "CERTISIGN_PROXY_SENHA";
	public static final String PARAMETRO_FLAG_ASSINATURA_TRANSACAO = "FLAG_ASSINATURA_TRANSACAO";
	public static final String PARAMETRO_INTEGRACAO_COMVEN2_URL = "INTEGRACAO_COMVEN2_URL";

	 // AUTENTICIS
    public static final String CNPJ_AUTENTICIS = "08763919000149";

    // Numero dos jobs (comven2)
    public static final String COMVEN2_WEBSERVICE_JOB_VALIDAR_EXTRACAO = "1";
    public static final String COMVEN2_WEBSERVICE_JOB_ENCAMINHAR_QWARE = "2";
    public static final String COMVEN2_WEBSERVICE_JOB_RETORNAR_QWARE = "3";
    public static final String COMVEN2_WEBSERVICE_JOB_VALIDAR_ARQUIVO_FINAL = "4";
    public static final String COMVEN2_WEBSERVICE_JOB_ENVIAR_ARQUIVO_DENATRAN = "5";
    public static final String COMVEN2_WEBSERVICE_JOB_REPROCESSAR_LINHA = "6";
    public static final String COMVEN2_WEBSERVICE_JOB_EXPORTAR_ARQUIVO = "7";

}
