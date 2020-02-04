package br.com.comven.corews.transacao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TransacaoHelper {

	static SimpleDateFormat sdf = new SimpleDateFormat("yy");
	
	public static String getNumeroTransacaoComven(Integer anoDiaJuliano, Long sequencialDiario, String modulo) {
		
		String idxYY	  = (17 + Integer.parseInt(sdf.format(new Date()))) + "";	
		String diaJuliano = Util.preencheCom(anoDiaJuliano.toString(), "0", 3, Util.DIRECAO_ESQUERDA);
		String transacao  = Util.preencheCom(sequencialDiario.toString(), "0", 6, Util.DIRECAO_ESQUERDA);
		
		return new String(idxYY + diaJuliano + transacao + modulo);
	}
}
