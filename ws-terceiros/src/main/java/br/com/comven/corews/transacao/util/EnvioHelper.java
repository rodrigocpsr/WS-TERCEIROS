package br.com.comven.corews.transacao.util;

import org.apache.log4j.Logger;


public class EnvioHelper {
	
	Logger logger = Logger.getLogger(EnvioHelper.class);
	
	public final String AMBIENTE;
	public final String IP;
	public final Integer PORTA;

	private static EnvioHelper instance;
	public static final int MAX_BUF = 32768;

	public final String SOAP_HEADER;
	public final String SOAP_CONTENT_PRE;
	public final String SOAP_CONTENT_POS;
	
	private EnvioHelper(String ambiente) throws Exception{
		
		try{
			
		String host = "200.155.30.70";
    	String port = "13000";
    	
		AMBIENTE = ambiente;
		IP = host;
		PORTA = Integer.parseInt(port);
		
		}catch(Exception e){
			throw new Exception("Variaveis de ambiente do APLIC não configuradas: " + e);
		}

		
		SOAP_HEADER = "POST /vendaemcartorios.asmx HTTP/1.1\n"
			+ "Host: "
			+ IP
			+ "\n"
			+ "Content-Type: application/soap+xml; charset=utf-8\n"
			+ "Content-Length: ";
		
		SOAP_CONTENT_PRE = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
			+ "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n"
			+ "<soap12:Body>\n"
			+ "<RegistraComunicacaoDeVendaEmCartorio xmlns=\"http://"
			+ IP
			+ "/\">\n" + "<conteudo>";	
		
		SOAP_CONTENT_POS = "</conteudo>\n"
			+ "</RegistraComunicacaoDeVendaEmCartorio>\n" + "</soap12:Body>\n"
			+ "</soap12:Envelope>\n";
	}

	public String getSOAPContent(String strEnvio) {
		String SERPROConteudo =  StringOperations.limpaStringEnvio(strEnvio);
		String conteudo = SOAP_CONTENT_PRE + AMBIENTE + SERPROConteudo + SOAP_CONTENT_POS;
		return SOAP_HEADER + conteudo.length() + "\r\n\r\n" + conteudo;
	}
	
	public static EnvioHelper getInstance(String ambiente) throws Exception {
		if (instance == null) {
			instance = new EnvioHelper(ambiente);
		}

		return instance;
	}
	
	
}
