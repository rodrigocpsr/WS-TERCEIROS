package br.com.comven.corews.transacao.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import sun.misc.BASE64Encoder;

public class Util {
	
	public static final int DIRECAO_ESQUERDA = 1;
	public static final int DIRECAO_DIREITA = 2;	
	
	public static String trunc(String valor, int tamanho){
		
		if (valor == null)
			return "";
		
		if (valor.length() > tamanho)
			return valor.substring(0, tamanho);
		else
			return valor;
	}
	
	public static String encript(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            
            digest.update(senha.getBytes());
            
            BASE64Encoder encoder = new BASE64Encoder();
                        
            return encoder.encode(digest.digest());
            
        } catch (NoSuchAlgorithmException ns) {
            ns.printStackTrace();
            return senha;
        }
    }
	
	
	/**
	 * @author cviglio
	 * @since 24/09/2009
	 * Retorna a data atual no formato juliano (dia no ano)
	 * @return Short : dia juliano no formato Short
	 */
	public static Short geraDiaJuliano(){
		return geraDiaJuliano(Calendar.getInstance().getTime());
		
	}
	
	/**
	 * @author dmagalhaes
	 * @since 21/10/2009
	 * Retorna a data passada por parametro no formato juliano (dia no ano)
	 * @return Short : dia juliano no formato Short
	 */	
	public static Short geraDiaJuliano(Date data){
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(data);
		return (short) calendar.get(Calendar.DAY_OF_YEAR);		
	}
	
	/**
	 * @author cviglio
	 * @since 24/09/2009
	 * Retorna a string posicional que sera enviada ao detran
	 * @return String : parte fixa
	 */
	public static String geraStringDetran(Long idTransacao, String codigoTransacao, Short diaJuliano, String parteVariavel, String caracteresPermitidos){
		
		// preparando parte fixa para envio da transacao ao Detran
		StringBuilder strDetran = new StringBuilder("");
		
		// numero sequencial transacao (tamanho = 6)
		strDetran.append(preencheCom(idTransacao+"", "0", 6, 1, caracteresPermitidos));
		
		// codigo Transacao (tamanho = 3)
		strDetran.append(preencheCom(codigoTransacao, "0", 3, 1, caracteresPermitidos));
		
		// codigo modalidade processamento (tamanho = 1)
		//3 = CPU assincrona (transacoes CPU x CPU que nao exigem resposta imediata)
		//4 = CPU sincrona (transacoes CPU x CPU que exigem resposta REAL-TIME)
		strDetran.append("5");
		
		// codigo de identificacao do cliente (tamanho = 11)
		// FIXO - codigo da gebranor junto ao cadastro do SERPRO
		strDetran.append(preencheCom("84010000000", "0", 11, 1, caracteresPermitidos)); 
		
		// UF origem transacao (tamanho = 2) - FIXO
		strDetran.append(preencheCom("FN", "0", 2, 1, caracteresPermitidos));
		
		// UF Origem transmissao (tamanho = 2) - FIXO
		strDetran.append(preencheCom("FN", "0", 2, 1, caracteresPermitidos));
		
		// UF Destino transmissao (tamanho = 2) - FIXO
		strDetran.append(preencheCom("BR", "0", 2, 1, caracteresPermitidos));
		
		// tipo condicionalidade transacao (tamanho = 1)
		// 1 = Executa sem restricao (default)
		// 2 = Executa com restricao (executa a transacao mesmo que ela tenha restricoes)
		strDetran.append("1");
		
		// tamanho transacao (tamanho = 4)
		// tamanho da transacao em bytes
		strDetran.append("9999");
		
		// codigo retorno execucao
		strDetran.append("00");
		
		// dia Juliano
		strDetran.append(preencheCom(diaJuliano.toString(), "0", 3, 1, caracteresPermitidos));

		// parte variavel da transa��o
		strDetran.append(parteVariavel);
		
		//a tualizando o numero de caracteres da parte fixa
		String byteSaida = strDetran.length() + "";
		strDetran.replace(28, 32, preencheCom(byteSaida, "0", 4, 1, caracteresPermitidos));
	 
		return strDetran.toString();
		
	}
	
	public static String preencheCom(String linha_a_preencher, String letra,
			int tamanho, int direcao) {
		return preencheCom(linha_a_preencher, letra, tamanho, direcao, null);
	}
	
	
	public static String preencheCom(String linha_a_preencher, String letra,
			int tamanho, int direcao, String caracteresPermitidos) {

		// checa se linha a preenchera nula ou branco
		if (linha_a_preencher == null || linha_a_preencher.trim() == "") {
			linha_a_preencher = "";
		}

		/*
		 * Removido por causa do GV 71
		 * 
		// enquanto Linha a preencher possuir 2 espacos em branco seguidos,
		// substitui por 1 espaco apenas
		while (linha_a_preencher.contains("  ")) {
			linha_a_preencher = linha_a_preencher.replaceAll("  ", " ").trim();
		}
		*/

		// retira caracteres estranhos

		linha_a_preencher = Util.limpaString(linha_a_preencher, caracteresPermitidos);
		StringBuffer sb = new StringBuffer(linha_a_preencher);

		if (direcao == DIRECAO_ESQUERDA) { // valor 1 = a esquerda
			for (int i = sb.length(); i < tamanho; i++) {
				sb.insert(0, letra);
			}
		} else if (direcao == DIRECAO_DIREITA) { // valor 2 = a direita	
			for (int i = sb.length(); i < tamanho; i++) {
				sb.append(letra);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Formnata data no formato AAAAMMDD para DD/MM/AAAA
	 * @param data
	 * @return
	 */
	public static String formatDateString(String data){
		
		return data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4);
	}
	
	public static String converteDataLayout(Integer numDate){
		
		String tempDate = numDate+"";
		return tempDate.substring(6, 8) + "/" + tempDate.substring(4, 6) + "/" + tempDate.substring(0, 4);
		
	}
	
	static public String formatIntegerToStringDate(Integer val)
	{	
		if (val == null) return "";
		
		String dt = val.toString();
		if(dt.length() == 8) return dt.substring(6, 8) + "/" + dt.substring(4, 6) + "/" + dt.substring(0, 4);
		else return null;
	}

	static public String formatStringToDate(String val)
	{
		if (val == null) return "";
		
		if(val.length() == 8) return val.substring(6, 8) + "/" + val.substring(4, 6) + "/" + val.substring(0, 4);
		else return null;
	}

	static public String formatIntegerToStringTime(Integer val)
	{
		if (val == null) return "";
		
		String dt = val.toString();
		if(dt.length() == 6) return dt.substring(0, 2) + ":" + dt.substring(2, 4) + ":" + dt.substring(4, 6);
		else return null;
	}

	public static String formatDataParaPersistencia(String data) {
		String[] campos = data.split("/");
		
		return campos[2] + campos[1] + campos[0];
	}
	
	static public String formatPlaca(String val)
	{
		if(val.length() == 7) return val.substring(0, 3) + "-" + val.substring(3, 7);
		else return null;
	}

	static public String formatCpfOrCnpj(String val)
	{
		val = removeMascara(val);
		
		if(val != null && val.trim().length() >= 11 && val.trim().length() <= 14)
		{
			if(val.trim().length() == 11) 
				val = val.substring(0, 3) + "." + val.substring(3, 6) + "." + val.substring(6, 9) + "-" + val.substring(9, 11);
			else 
				val = val.substring(0, 2) + "." + val.substring(2, 5) + "." + val.substring(5, 8) + "/" + val.substring(8, 12) + "-" + val.substring(12, 14);
		}
		
		return val;
	}
	
	static public String formatCep(String val)
	{
		if(val.length() == 8) return val.substring(0, 5) + "-" + val.substring(5, 8);
		else return null;
	}

	static public String formatDddAndTelefone(String ddd, String tel)
	{
		if(ddd != null && tel.length() == 8) return "(" + ddd + ") " + tel.substring(0, 4) + "-" + tel.substring(4, 8);
		else return null;
	}

	static public String formatEndereco(String logradouro, String numero, String complemento)
	{
		if(complemento != null && !complemento.equals("") && !complemento.equals("                    ")) 
			complemento = ", " + complemento;
		else 
			complemento = "";
		return logradouro + " " + numero + complemento;
	}
	
	static public Date converteIntegersData(Integer data, Integer intHora) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		// como o banco guarda a hora no formato inteiro, as horas antes das 10:00 tera removido e ao
		// executar uma substring com os 6 campos ocorrera problema, por isso para parsear a hora ela 
		// deve ser formatada antes
		
		String ano 		= Integer.toString(data).substring(0, 4);
		String mes 		= Integer.toString(data).substring(4, 6);
		String dia 		= Integer.toString(data).substring(6, 8);
		
		String horaTemp = Integer.toString(intHora);
		
		while (horaTemp.length() < 6)
			horaTemp = "0" + horaTemp;
		
		String hora 	= horaTemp.substring(0, 2);
		String minuto 	= horaTemp.substring(2, 4);
		String segundo 	= horaTemp.substring(4, 6);
		
		return sdf.parse(dia + "/" + mes + "/" + ano + " " + hora + ":" + minuto + ":" + segundo);
		
	}
	
	static public Date converteDataDetranToDate(Integer data) {
		SimpleDateFormat sdf = null;
		String dataStr = "";
		try
		{
			sdf = new SimpleDateFormat("yyyyMMdd");
			
			dataStr = String.valueOf(data);
			
			return sdf.parse(dataStr);						
		}
		catch(Exception e)
		{
			return null;
		}						
	}
	
	static public String formatDateHour(Date data) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");		
		
		return sdf.format(data);		
	}
	
	static public String formatDate(Date data) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		
		return sdf.format(data);		
	}
	
	static public String formatDateDetran(Date data) throws ParseException{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");		
		
		return sdf.format(data);		
	}
	
	private static final String UPPERCASE_ASCII =
	    "AEIOU" // grave
	    + "AEIOUY" // acute
	    + "AEIOUY" // circumflex
	    + "AON" // tilde
	    + "AEIOUY" // umlaut
	    + "A" // ring
	    + "C" // cedilla
	    + "OU" // double acute
	    ;

	  private static final String UPPERCASE_UNICODE =
	    "\u00C0\u00C8\u00CC\u00D2\u00D9"
	    + "\u00C1\u00C9\u00CD\u00D3\u00DA\u00DD"
	    + "\u00C2\u00CA\u00CE\u00D4\u00DB\u0176"
	    + "\u00C3\u00D5\u00D1"
	    + "\u00C4\u00CB\u00CF\u00D6\u00DC\u0178"
	    + "\u00C5"
	    + "\u00C7"
	    + "\u0150\u0170"
	    ;

	  public static String toUpperCaseSansAccent(String txt) {
	       if (txt == null) {
	          return null;
	       } 
	       String txtUpper = txt.toUpperCase();
	       StringBuilder sb = new StringBuilder();
	       int n = txtUpper.length();
	       for (int i = 0; i < n; i++) {
	          char c = txtUpper.charAt(i);
	          int pos = UPPERCASE_UNICODE.indexOf(c);
	          if (pos > -1){
	            sb.append(UPPERCASE_ASCII.charAt(pos));
	          }
	          else {
	            sb.append(c);
	          }
	       }
	       return sb.toString();
	  }
	  
	  //a string de envio deve bloquear os caracteres abaixo. Por padrao sempre seraqo permitidos aA-zZ e 0-9.
	  public static String limpaString(String txt, String paramAprovados){

		  String novaString = "";

		  if (paramAprovados != null){
			  paramAprovados += "QWERTYUIOPASDFGHJKLZXCVBNM0123456789 ";

			  //mantem na string de envio somente os caracteres permitidos
			  for (char c : txt.toCharArray())
				  novaString = !(paramAprovados.toUpperCase()).contains(c+"".toUpperCase()) ? novaString : novaString + c;
		  }else{
			  return txt;
		  }

		  return novaString;
	  }	
	  
	  /*
	  public static String salvarArquivoRetorno(TransacaoNova transacao, T233Ev01 contextBeanEV01, String retornoAplic, Cliente cliente, String caminho, int digitosRenavam) throws IOException {
			
		  String nomeArq = "/WEB_"+ cliente.getCnpj() + "T" + transacao.getId().getIdtransacao() + "D" + transacao.getId().getDiaJuliano() + new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + ".txt";
			
			caminho = caminho.trim() + nomeArq;
			
			String header = "1;ITA;" + new SimpleDateFormat("yyyyMMddHHmmss").format(transacao.getDataEnvio()) + ";" + transacao.getCliente().getCnpj() + ";00000;"; 
			String trailler = "3;" + transacao.getCliente().getCnpj() + ";00000;000000001;";
			SaidaGenerator saida = new SaidaGenerator(header, trailler, caminho);
			RegistroSaida registroSaida = new RegistroSaida();
			
			registroSaida.setChassi("");
			registroSaida.setRetornoTransacao( retornoAplic.substring(37,40) );
			registroSaida.setDiaJuliano( String.valueOf(transacao.getId().getDiaJuliano()) );
			registroSaida.setMarca("");
			registroSaida.setModelo("");
			registroSaida.setPlaca(contextBeanEV01.getPlacaUnica());
			registroSaida.setUfVeiculo(transacao.getUfTransacao().getSigla());
			registroSaida.setDataHoraTransacao( new SimpleDateFormat("yyyyMMddHHmmss").format(transacao.getDataEnvio()) );
			registroSaida.setNumeroTransacaoComven( contextBeanEV01.getNumIdentCv().toString() );
			registroSaida.setRenavam( contextBeanEV01.getRenavam().toString() );
			saida.getRegistros().add(registroSaida);					
			saida.salvar(digitosRenavam);
			nomeArq = "../../downloads/arq_if" + nomeArq;
			
			return nomeArq;
		}
		*/
	
	  public static String formataStringParaAplic(String input){
		
		  if (input != null){
			  //input = Normalizer.normalize(input, Normalizer.Form.NFD);  
			  //input = input.replaceAll("[^\\p{ASCII}]", "");  
			  return input.trim().toUpperCase();
		  }
		  
		  return input;

	  }
	  
	  public static String formataNumberParaAplic(String input){
		
		  if (input != null){
			  input = Normalizer.normalize(input, Normalizer.Form.NFD);  
			  input = input.replaceAll(".\\/-", "");  
			  return input.trim().toUpperCase();
		  }
		  
		  return input;
	  }
	  
	  public static String removeMascara(String input){
			
		  if (input != null){
			  input = Normalizer.normalize(input, Normalizer.Form.NFD);
			  input = input.replaceAll("\\.", "");
			  input = input.replaceAll("/", "");  
			  input = input.replaceAll("-", "");  
			  input = input.replaceAll("_", "");
			  input = input.replace("(", "");
			  input = input.replace(")", "");
			  input = input.replace(" ", "");
			
			  return input.trim().toUpperCase();
		  }
		  
		  return input;
	  }
	  
	  public static String clearNumber (String str)
	    {
	    	String newStr="";
	        if (str == null) str ="";    	
	    	for (int i=0;i<str.length();i++)
	    	{
	    		if ((str.charAt(i) >= '0') &&
	    			(str.charAt(i) <= '9'))
	    		{
	    			newStr = newStr + str.charAt(i);
	    		}
	    	}
	    	return newStr;
	    }

		static public String padStringLeft(String str, int length, char c)
	    {
	        if (str == null) str ="";
	    	while(str.length() < length)
	            str = c + str;
	    	if (str.length()>length) str = str.substring (0,length);    	
	        return str;
	    }
		
		public static String removerAcentosLote(String s) {
			
			String acentuado = "ÁÍÓÚÉÄÏÖÜËÀÌÒÙÈÃÕÂÎÔÛÊáíóúéäïöüëàìòùèãõâîôûêÇç`\"()";  
			String semAcento = "AIOUEAIOUEAIOUEAOAIOUEaioueaioueaioueaoaioueCc    ";
			
		    char[] tabela;  
		    
		    tabela = new char[256];
		    for (int i = 0; i < tabela.length; ++i) {  
		    	tabela [i] = (char) i;  
		    }  
		    for (int i = 0; i < acentuado.length(); ++i) {  
		            tabela [acentuado.charAt(i)] = semAcento.charAt(i);  
		    }  

	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < s.length(); ++i) {  
	            char ch = s.charAt (i);  
	            if (ch < 256) {   
	                sb.append (tabela [ch]);  
	            } else {  
	                sb.append (ch);
	            }  
	        }
	        
	        String regex = "[^A-Za-z0-9(\\s)]";
	        
	        String resultado = sb.toString().replaceAll(regex, " ");
	        
	        // Enquanto Linha a preencher possuir 2 espa�os em branco seguidos,
			// substitui por 1 espa�o apenas
			while (resultado.contains("  ")) {
				resultado = resultado.replaceAll("  ", " ").trim();
			}
	        
	        return resultado.toUpperCase();	        
	    }
		
		public static String removerAcentos(String s) {
			
			String acentuado = "ÁÍÓÚÉÄÏÖÜËÀÌÒÙÈÃÕÂÎÔÛÊáíóúéäïöüëàìòùèãõâîôûêÇç";
			String semAcento = "AIOUEAIOUEAIOUEAOAIOUEaioueaioueaioueaoaioueCc";
			
		    char[] tabela;  
		    
		    tabela = new char[256];
		    for (int i = 0; i < tabela.length; ++i) {  
		    	tabela [i] = (char) i;  
		    }  
		    for (int i = 0; i < acentuado.length(); ++i) {  
		            tabela [acentuado.charAt(i)] = semAcento.charAt(i);  
		    }  

	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < s.length(); ++i) {  
	            char ch = s.charAt (i);  
	            if (ch < 256) {   
	                sb.append (tabela [ch]);  
	            } else {  
	                sb.append (ch);  
	            }  
	        }  
	        return sb.toString();
	    }
		
		public static String removerAcentosCarga(String s) {
			
			String acentuado = "ÁÍÓÚÉÄÏÖÜËÀÌÒÙÈÃÕÂÎÔÛÊáíóúéäïöüëàìòùèãõâîôûêÇç";  
			String semAcento = "AIOUEAIOUEAIOUEAOAIOUEaioueaioueaioueaoaioueCc";
			
		    char[] tabela;  
		    
		    tabela = new char[256];
		    for (int i = 0; i < tabela.length; ++i) {  
		    	tabela [i] = (char) i;  
		    }  
		    for (int i = 0; i < acentuado.length(); ++i) {  
		            tabela [acentuado.charAt(i)] = semAcento.charAt(i);  
		    }  

	        StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < s.length(); ++i) {
	            char ch = s.charAt (i);  
	            if (ch < 256) {   
	                sb.append (tabela [ch]);  
	            } else {  
	                sb.append (ch);  
	            }  
	        }
	        
	        String regex = "[^A-Za-z0-9(\\.)(\\-)(\\s)]";
	        
	        return sb.toString().replaceAll(regex, " ");
	    }
		
		public static String formataAlphaNumerico(String s) {			
	        
	        String regex = "[^A-Za-z0-9]";
	        
	        return s.replaceAll(regex, "");
	    }
		
		/**
		 * 
		 * @author lsoares
		 * @throws IOException 
		 * @since 26/08/2011
		 * 
		 * Descri��o: Converte as linhas do arquivo para um List.		 * 
		 */
		public static LinkedList<String> getFileToLinkedList(File file) throws IOException{
			
	        BufferedReader in = new BufferedReader(new FileReader(file));	        
	        
	        return getFileToLinkedList(in);
		}
		
		public static List<String> getFileToList(File file) throws IOException{
			
			BufferedReader in = new BufferedReader(new FileReader(file));	        
			
			return getFileToList(in);
		}
		
		
		/**
		 * 
		 * @author lsoares
		 * @throws IOException 
		 * @since 26/08/2011
		 * 
		 * Descri��o: Converte as linhas do arquivo para um List.		 * 
		 */
		public static List<String> getFileToList(BufferedReader in) throws IOException{
			
	        String line = "";	        
	        
	        ArrayList<String> listLines = new ArrayList<String>();	        	        
	        	
	        while((line = in.readLine()) != null)
	        {
	        	listLines.add(line);	        	
	        }
	              
	        if( in != null )
	        	in.close();	        
	        
	        return listLines;
		}
		
        public static LinkedList<String> getFileToLinkedList(BufferedReader reader) throws IOException{  	
        	
        	LinkedList<String> builder = new LinkedList<String>();
			String aux = "";

			while ((aux = reader.readLine()) != null) {
				
			builder.add(aux);
			
			}

        	return builder;
		}
		
		public static File convertFileToUTF8(String file, String out) throws IOException
		{
			BufferedReader br = new BufferedReader (new InputStreamReader ( new FileInputStream(file), "ISO-8859-1")); 
			
			BufferedWriter bw = new BufferedWriter (new OutputStreamWriter ( new FileOutputStream(out), "UTF-8"));  
			
			for (String linha = br.readLine(); linha != null; linha = br.readLine())  
			{  
				bw.write (linha); bw.newLine();  
			}  
			
			bw.close();  
			br.close();
			
			return new File(out);
		}		
		
		/**
		 *  
		 * @author lsoares
		 * @since 05/09/2011
		 * Descri��o: Adiciona o tamano x para uma string. Ex: "123" --> x = 5 --> "123  "
		 */
		public static String strToLength(String str, int length)
	    {
	        if(str.length() < length)
	        {
	            str = str + " ";
	            
	            return strToLength(str, length);
	        }
	        else{
	            return str;            
	        }        
	    }
		
		public static int getDayOfWeek( )
		{
			GregorianCalendar gc = new GregorianCalendar();
			
			return gc.get(Calendar.DAY_OF_WEEK);
		}
		
	public static String getDataVendaPorExtenso() {

		// 20080120
		DateFormat dfyyymmdd = new SimpleDateFormat("yyyyMMdd");
		String auxDataVenda = dfyyymmdd.format(new Date());
		String year = auxDataVenda.substring(0, 4);
		;
		String month = auxDataVenda.substring(4, 6);
		String day = auxDataVenda.substring(6, 8);

		// window.alert(day + ' de ' + month + ' de ' + year );

		String dias[] = { "Zero", "Primeiro", "Segundo", "Terceiro", "Quarto",
				"Quinto", "Sexto", "Setimo", "Oitavo", "Nono", "Décimo",
				"Décimo primeiro", "Décimo segundo", "Décimo terceiro",
				"Décimo quarto", "Décimo quinto", "Décimo sexto",
				"Décimo setimo", "Décimo oitavo", "Décimo nono", "Vigésimo",
				"Vigésimo primeiro", "Vigésimo segundo", "Vigésimo terceiro",
				"Vigésimo quarto", "Vigésimo quinto", "Vigésimo sexto",
				"Vigésimo setimo", "Vigésimo oitavo", "Vigésimo nono",
				"Trigésimo", "Trigésimo primeiro" };

		String unidade[] = { "Zero", "Um", "Dois", "Tres", "Quatro", "Cinco",
				"Seis", "Sete", "Oito", "Nove" };

		String dez[] = { "Zero", "Onze", "Doze", "Treze", "Quatorze", "Quinze",
				"Dezesseis", "Dezessete", "Dezoito", "Dezenove" };

		String dezenas[] = { "Zero", "Dez", "Vinte", "Trinta", "Quarenta",
				"Cinquenta", "Sessenta", "Setenta", "Oitenta", "Noventa" };

		String centenas[] = { "Zero", "Cento", "Duzentos", "Trezentos",
				"Quatrocentos", "Quinhentos", "Seiscentos", "Setecentos",
				"Oitocentos", "Novecentos" };

		// 2 0 1 1
		String saidaPorExtenso = "";
		if (new Integer(year.substring(0, 1)) > 0) {
			saidaPorExtenso = unidade[new Integer(year.substring(0, 1))]
					+ " mil";
		}
		if (new Integer(year.substring(1, 2)) > 0) {
			if (new Integer(year.substring(1, 2)) == 1
					&& (new Integer(year.substring(2, 3)) == 0 && new Integer(
							year.substring(3, 4)) == 0))
				saidaPorExtenso = saidaPorExtenso + " e Cem";
			else if ((new Integer(year.substring(2, 3)) == 0 && new Integer(
					year.substring(3, 4)) == 0))
				saidaPorExtenso = saidaPorExtenso + " e "
						+ centenas[new Integer(year.substring(1, 2))];
			else
				saidaPorExtenso = saidaPorExtenso + " "
						+ centenas[new Integer(year.substring(1, 2))];
		}
		if (new Integer(year.substring(2, 3)) > 0) {
			if (new Integer(year.substring(2, 3)) == 1
					&& new Integer(year.substring(3, 4)) > 0)
				saidaPorExtenso = saidaPorExtenso + " e "
						+ dez[new Integer(year.substring(3, 4))];
			else
				saidaPorExtenso = saidaPorExtenso + " e "
						+ dezenas[new Integer(year.substring(2, 3))];
		}
		if (new Integer(year.substring(3, 4)) > 0
				&& new Integer(year.substring(2, 3)) != 1) {
			saidaPorExtenso = saidaPorExtenso + " e "
					+ unidade[new Integer(year.substring(3, 4))];
		}
		String mes = "";
		switch (new Integer(month)) {
		case 1:
			mes = " de Janeiro de ";
			break;
		case 2:
			mes = " de Fevereiro de ";
			break;
		case 3:
			mes = " de Março de ";
			break;
		case 4:
			mes = " de Abril de ";
			break;
		case 5:
			mes = " de Maio de ";
			break;
		case 6:
			mes = " de Junho de ";
			break;
		case 7:
			mes = " de Julho de ";
			break;
		case 8:
			mes = " de Agosto de ";
			break;
		case 9:
			mes = " de Setembro de ";
			break;
		case 10:
			mes = " de Outubro de ";
			break;
		case 11:
			mes = " de Novembro de ";
			break;
		case 12:
			mes = " de Dezembro de ";
			break;
		}

		// window.alert("Ao " + dias[day] + " dia do m�s" + mes +
		// saidaPorExtenso);
		return (dias[new Integer(day)] + " dia do mês" + mes + saidaPorExtenso);

	}

	public static String getDataVendaPorExtenso2() {

		// 20080120
		DateFormat dfyyymmdd = new SimpleDateFormat("yyyyMMdd");
		String auxDataVenda = dfyyymmdd.format(new Date());
		String year = auxDataVenda.substring(0, 4);
		;
		String month = auxDataVenda.substring(4, 6);
		String day = auxDataVenda.substring(6, 8);

		// window.alert(day + ' de ' + month + ' de ' + year );

		String dias[] = { "Zero", "1 (um)", "2 (dois)", "3 (três)",
				"4 (quatro)", "5 (cinco)", "6 (seis)", "7 (sete)", "8 (oito)",
				"9 (nove)", "10 (dez)", "11 (onze)", "12 (doze)", "13 (treze)",
				"14 (quatorze)", "15 (quinze)", "16 (dezesseis)",
				"17 (dezessete)", "18 (dezoito)", "19 (dezenove)",
				"20 (vinte)", "21 (vinte e um)", "22 (vinte e dois)",
				"23 (vinte e três)", "24 (vinte e quatro)",
				"25 (vinte e cinco)", "26 (vinte e seis)", "27 (vinte e sete)",
				"28 (vinte e oito)", "29 (vinte e nove)", "30 (trinta)",
				"31 (trinta e um)" };

		String unidade[] = { "Zero", "Um", "Dois", "Tres", "Quatro", "Cinco",
				"Seis", "Sete", "Oito", "Nove" };

		String dez[] = { "Zero", "Onze", "Doze", "Treze", "Quatorze", "Quinze",
				"Dezesseis", "Dezessete", "Dezoito", "Dezenove" };

		String dezenas[] = { "Zero", "Dez", "Vinte", "Trinta", "Quarenta",
				"Cinquenta", "Sessenta", "Setenta", "Oitenta", "Noventa" };

		String centenas[] = { "Zero", "Cento", "Duzentos", "Trezentos",
				"Quatrocentos", "Quinhentos", "Seiscentos", "Setecentos",
				"Oitocentos", "Novecentos" };

		// 2 0 1 1
		String saidaPorExtenso = "";
		if (new Integer(year.substring(0, 1)) > 0) {
			saidaPorExtenso = unidade[new Integer(year.substring(0, 1))]
					+ " mil";
		}
		if (new Integer(year.substring(1, 2)) > 0) {
			if (new Integer(year.substring(1, 2)) == 1
					&& (new Integer(year.substring(2, 3)) == 0 && new Integer(
							year.substring(3, 4)) == 0))
				saidaPorExtenso = saidaPorExtenso + " e Cem";
			else if ((new Integer(year.substring(2, 3)) == 0 && new Integer(
					year.substring(3, 4)) == 0))
				saidaPorExtenso = saidaPorExtenso + " e "
						+ centenas[new Integer(year.substring(1, 2))];
			else
				saidaPorExtenso = saidaPorExtenso + " "
						+ centenas[new Integer(year.substring(1, 2))];
		}
		if (new Integer(year.substring(2, 3)) > 0) {
			if (new Integer(year.substring(2, 3)) == 1
					&& new Integer(year.substring(3, 4)) > 0)
				saidaPorExtenso = saidaPorExtenso + " e "
						+ dez[new Integer(year.substring(3, 4))];
			else
				saidaPorExtenso = saidaPorExtenso + " e "
						+ dezenas[new Integer(year.substring(2, 3))];
		}
		if (new Integer(year.substring(3, 4)) > 0
				&& new Integer(year.substring(2, 3)) != 1) {
			saidaPorExtenso = saidaPorExtenso + " e "
					+ unidade[new Integer(year.substring(3, 4))];
		}
		String mes = "";
		switch (new Integer(month)) {
		case 1:
			mes = " de Janeiro de ";
			break;
		case 2:
			mes = " de Fevereiro de ";
			break;
		case 3:
			mes = " de Março de ";
			break;
		case 4:
			mes = " de Abril de ";
			break;
		case 5:
			mes = " de Maio de ";
			break;
		case 6:
			mes = " de Junho de ";
			break;
		case 7:
			mes = " de Julho de ";
			break;
		case 8:
			mes = " de Agosto de ";
			break;
		case 9:
			mes = " de Setembro de ";
			break;
		case 10:
			mes = " de Outubro de ";
			break;
		case 11:
			mes = " de Novembro de ";
			break;
		case 12:
			mes = " de Dezembro de ";
			break;
		}

		// window.alert("Ao " + dias[day] + " dia do m�s" + mes +
		// saidaPorExtenso);
		return (dias[new Integer(day)] + " dias do mês" + mes + year + " ("
				+ saidaPorExtenso + ")");

	}

	public static String getDataVendaPorExtenso3() {

		// 20080120
		DateFormat dfyyymmdd = new SimpleDateFormat("yyyyMMdd");
		String auxDataVenda = dfyyymmdd.format(new Date());
		String year = auxDataVenda.substring(0, 4);
		;
		String month = auxDataVenda.substring(4, 6);
		String day = auxDataVenda.substring(6, 8);

		// window.alert(day + ' de ' + month + ' de ' + year );

		String dias[] = { "Zero", "Um", "Dois", "Três", "Quatro", "Cinco",
				"Seis", "Sete", "Oito", "Nove", "Dez", "Onze", "Doze", "Treze",
				"Quatorze", "Quinze", "Dezesseis", "Dezessete", "Dezoito",
				"Dezenove", "Vinte", "Vinte e um", "Vinte e dois",
				"Vinte e três", "Vinte e quatro", "Vinte e cinco",
				"Vinte e seis", "Vinte e sete", "Vinte e oito", "Vinte e nove",
				"Trinta", "Trinta e um" };

		String unidade[] = { "Zero", "Um", "Dois", "Tres", "Quatro", "Cinco",
				"Seis", "Sete", "Oito", "Nove" };

		String dez[] = { "Zero", "Onze", "Doze", "Treze", "Quatorze", "Quinze",
				"Dezesseis", "Dezessete", "Dezoito", "Dezenove" };

		String dezenas[] = { "Zero", "Dez", "Vinte", "Trinta", "Quarenta",
				"Cinquenta", "Sessenta", "Setenta", "Oitenta", "Noventa" };

		String centenas[] = { "Zero", "Cento", "Duzentos", "Trezentos",
				"Quatrocentos", "Quinhentos", "Seiscentos", "Setecentos",
				"Oitocentos", "Novecentos" };

		// 2 0 1 1
		String saidaPorExtenso = "";
		if (new Integer(year.substring(0, 1)) > 0) {
			saidaPorExtenso = unidade[new Integer(year.substring(0, 1))]
					+ " mil";
		}
		if (new Integer(year.substring(1, 2)) > 0) {
			if (new Integer(year.substring(1, 2)) == 1
					&& (new Integer(year.substring(2, 3)) == 0 && new Integer(
							year.substring(3, 4)) == 0))
				saidaPorExtenso = saidaPorExtenso + " e Cem";
			else if ((new Integer(year.substring(2, 3)) == 0 && new Integer(
					year.substring(3, 4)) == 0))
				saidaPorExtenso = saidaPorExtenso + " e "
						+ centenas[new Integer(year.substring(1, 2))];
			else
				saidaPorExtenso = saidaPorExtenso + " "
						+ centenas[new Integer(year.substring(1, 2))];
		}
		if (new Integer(year.substring(2, 3)) > 0) {
			if (new Integer(year.substring(2, 3)) == 1
					&& new Integer(year.substring(3, 4)) > 0)
				saidaPorExtenso = saidaPorExtenso + " e "
						+ dez[new Integer(year.substring(3, 4))];
			else
				saidaPorExtenso = saidaPorExtenso + " e "
						+ dezenas[new Integer(year.substring(2, 3))];
		}
		if (new Integer(year.substring(3, 4)) > 0
				&& new Integer(year.substring(2, 3)) != 1) {
			saidaPorExtenso = saidaPorExtenso + " e "
					+ unidade[new Integer(year.substring(3, 4))];
		}
		String mes = "";
		switch (new Integer(month)) {
		case 1:
			mes = " de Janeiro de ";
			break;
		case 2:
			mes = " de Fevereiro de ";
			break;
		case 3:
			mes = " de Março de ";
			break;
		case 4:
			mes = " de Abril de ";
			break;
		case 5:
			mes = " de Maio de ";
			break;
		case 6:
			mes = " de Junho de ";
			break;
		case 7:
			mes = " de Julho de ";
			break;
		case 8:
			mes = " de Agosto de ";
			break;
		case 9:
			mes = " de Setembro de ";
			break;
		case 10:
			mes = " de Outubro de ";
			break;
		case 11:
			mes = " de Novembro de ";
			break;
		case 12:
			mes = " de Dezembro de ";
			break;
		}

		// window.alert("Ao " + dias[day] + " dia do m�s" + mes +
		// saidaPorExtenso);
		return (dias[new Integer(day)] + mes + year);

	}
	
	/**
	 * @author lsoares
	 * @since 15/03/2013
	 * Retorna diferen�a em horas, caso o par�metro tipoRetorno seja 0, 
	 * retorna a diferen�a em dias, caso o par�metro tipoRetorno seja 1
	 * e retorna a diferen�a em meses, caso o par�metro tipoRetorno seja 2
	 * 
	 * Para diferen�a de dias:
	 * Caso o retorno seja 0: as datas s�o iguais
	 * Caso o retorno seja um n�mero positivo: � quantidade de dias que a data inicial falta para chegar a data final
	 * Caso o retorno seja um n�mero negativo: � quantidade de dias que a data inicial que j� passaram da data final
	 * 
	 * @return String: diferen�a
	 */
	public static String diffTime(Date dInicial, Date dFinal, int tipoRetorno){
		if(tipoRetorno == 0)
		{
					
	        Calendar calFinal = Calendar.getInstance();
	        calFinal.setTime(dFinal);
	        
	        Calendar calInicial = Calendar.getInstance();
	        calInicial.setTime(dInicial);        
	        
	        calFinal.add(Calendar.HOUR_OF_DAY, -calInicial.get(Calendar.HOUR_OF_DAY));
	        calFinal.add(Calendar.MINUTE, -calInicial.get(Calendar.MINUTE));
	        calFinal.add(Calendar.SECOND, -calInicial.get(Calendar.SECOND));        
	        
	        return new SimpleDateFormat("HH:mm:ss").format(calFinal.getTime());
		}
		else if(tipoRetorno == 1)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	        
	        
	        try {
				dInicial = sdf.parse( sdf.format(dInicial) );
				dFinal = sdf.parse( sdf.format(dFinal) );
			} catch (ParseException e) {				
				e.printStackTrace();
			}	        
			
			Calendar caInicial = Calendar.getInstance();
	        caInicial.setTime(dInicial);
	        
	        Calendar caFinal = Calendar.getInstance();
	        caFinal.setTime(dFinal);
	        
	        /*
	         * M�todo Calendar.compareTo - compara��o de datas
	         * 
	         *  menor: -1
	         *  igual: 0
	         *  maior: 1
	         * 
	         */
	        
	        if(caInicial.compareTo(caFinal) == 0){ //sem diferen�a
	            return "0";
	        }
	        else
	        {
	            if(caInicial.compareTo(caFinal) > 0)
	            {
	                caInicial.add(Calendar.DAY_OF_MONTH, -caFinal.get(Calendar.DAY_OF_MONTH));
	                caInicial.add(Calendar.MONTH, -caFinal.get(Calendar.MONTH));
	                caInicial.add(Calendar.YEAR, -caFinal.get(Calendar.YEAR));
	                
	                return String.valueOf(-1 * caInicial.get(Calendar.DAY_OF_YEAR));
	            }
	            else
	            {
	                caFinal.add(Calendar.DAY_OF_MONTH, -caInicial.get(Calendar.DAY_OF_MONTH));
	                caFinal.add(Calendar.MONTH, -caInicial.get(Calendar.MONTH));
	                caFinal.add(Calendar.YEAR, -caInicial.get(Calendar.YEAR));
	        
	                return String.valueOf(caFinal.get(Calendar.DAY_OF_YEAR));
	            }
	        }
		}
		else if(tipoRetorno == 2)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");	        
	        
	        try {
				dInicial = sdf.parse( sdf.format(dInicial) );
				dFinal = sdf.parse( sdf.format(dFinal) );
			} catch (ParseException e) {				
				e.printStackTrace();
			}	        
			
			Calendar caInicial = Calendar.getInstance();
	        caInicial.setTime(dInicial);
	        
	        Calendar caFinal = Calendar.getInstance();
	        caFinal.setTime(dFinal);
	        
	        caInicial.set(Calendar.DATE, 1);
	        caFinal.set(Calendar.DATE, 1);
	        
	        int diffYear = caInicial.get(Calendar.YEAR) - caFinal.get(Calendar.YEAR);
	        
	        int diffMonth = 0;
	        
	        if(caInicial.get(Calendar.MONTH) < caFinal.get(Calendar.MONTH))
	        {
	        	caFinal.add(Calendar.MONTH, -caInicial.get(Calendar.MONTH));
	        	
	        	diffMonth = caFinal.get(Calendar.MONTH);
	        }
	        else if(caFinal.get(Calendar.MONTH) < caInicial.get(Calendar.MONTH)){
	        	caInicial.add(Calendar.MONTH, -caFinal.get(Calendar.MONTH));
	        	
	        	diffMonth = -1 * caInicial.get(Calendar.MONTH);
	        }	        
	        
	        int calc = -1 * (12 * diffYear) + diffMonth;
	        
	        return String.valueOf(calc);
		}
		
		return null;
    }
	
	public static String diffTime(Date dInicial, Date dFinal){
		return diffTime(dInicial, dFinal, 0);
	}
	
	public static byte[] getBytesFromFile(File inFile) {  
	    InputStream is = null;  
	    byte[] buffer = null;  
	    try {  
	        is = new FileInputStream(inFile);  
	        buffer = new byte[is.available()];  
	        is.read(buffer);  
	        is.close();  
	    } catch (FileNotFoundException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return buffer;  
	} 
	
	public static void copy(String fromFileName, String toFileName,
			Boolean blDeleteFrom) throws IOException {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		if (!fromFile.exists())
			throw new IOException("FileCopy: " + "no such source file: "
					+ fromFileName);
		if (!fromFile.isFile())
			throw new IOException("FileCopy: " + "can't copy directory: "
					+ fromFileName);
		if (!fromFile.canRead())
			throw new IOException("FileCopy: " + "source file is unreadable: "
					+ fromFileName);

		if (toFile.isDirectory())
			toFile = new File(toFile, fromFile.getName());

		/*
		 * if (toFile.exists()) { if (!toFile.canWrite()) throw new
		 * IOException("FileCopy: " + "destination file is unwriteable: " +
		 * toFileName); System.out.print("Overwrite existing file " +
		 * toFile.getName() + "? (Y/N): "); System.out.flush(); BufferedReader
		 * in = new BufferedReader(new InputStreamReader( System.in)); String
		 * response = in.readLine(); if (!response.equals("Y") &&
		 * !response.equals("y")) throw new IOException("FileCopy: " +
		 * "existing file was not overwritten."); } else { String parent =
		 * toFile.getParent(); if (parent == null) parent =
		 * System.getProperty("user.dir"); File dir = new File(parent); if
		 * (!dir.exists()) throw new IOException("FileCopy: " +
		 * "destination directory doesn't exist: " + parent); if (dir.isFile())
		 * throw new IOException("FileCopy: " +
		 * "destination is not a directory: " + parent); if (!dir.canWrite())
		 * throw new IOException("FileCopy: " +
		 * "destination directory is unwriteable: " + parent); }
		 */

		FileInputStream from = null;
		FileOutputStream to = null;
		try {
			from = new FileInputStream(fromFile);
			to = new FileOutputStream(toFile);
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = from.read(buffer)) != -1)
				to.write(buffer, 0, bytesRead); // write
		} finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}

			if (blDeleteFrom)
				fromFile.delete();
		}
	}
	
	public static void bytesToFile(byte[] data, String name) throws IOException{
		File file = new File(name); //Criamos um nome para o arquivo  
		BufferedOutputStream bos;
		
		bos = new BufferedOutputStream(new FileOutputStream(file));
		
		bos.write(data); //Gravamos os bytes lá  
		bos.close(); //Fechamos o stream.  
	}
	
	@SuppressWarnings({ "unchecked", "finally" })
	public static List<String> bytesToList(byte[] data) throws IOException{
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bais);
		
		ArrayList<String> arrayList = null;
		try {



			arrayList = ( ArrayList<String>) ois.readObject();

			ois.close();

		} catch (EOFException ex) { //This exception will be caught when EOF is reached
			System.out.println("End of file reached.");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			
			//Close the ObjectInputStream
			try {
				if (ois!= null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
			
			return arrayList;
		}
	}
	
	/**
	 * @author wfigueiredo
	 * @since 28/04/2014
	 * Descrição: método que retira acentos e caracteres especiais de uma string,
	 * mantendo caráter alfanumérico.
	 */
	public static String normalizeString(String input) {
		
		String regexNonWordBlankspace = "[^\\w\\s]";
		
		String result = Normalizer.normalize(input, Normalizer.Form.NFD);
		result = result.replaceAll("[^\\p{ASCII}]", "");
		result = result.replaceAll(regexNonWordBlankspace, "");
		
		return result;
	}

    public static String getIdTicketFormatado(Long idTicket) {
        String idTicketFormatado = "#" + String.format("%07d", idTicket);
        return idTicketFormatado;
}
    
    /**
     * Gera um hash MD5 a partir de um arquivo
     * 
     * @param file : arquivo para gerar o hash
     * @throws IOException 
     * 
     */
    public static String generateHash(String filePath) throws IOException  {
        
    	FileInputStream fis = null;
    	
        try{
        	
        	MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(filePath);
     
            byte[] dataBytes = new byte[1024];
     
            int nread = 0; 
            while ((nread = fis.read(dataBytes)) != -1) {
              md.update(dataBytes, 0, nread);
            };
            byte[] mdbytes = md.digest();
     
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
              sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
     
     
            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            
            for (int i=0;i<mdbytes.length;i++) {
                String hex=Integer.toHexString(0xff & mdbytes[i]);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();

        }catch(Exception e){
            
        }finally {
        	fis.close();
        }
        
        return null;
    }

  
}
