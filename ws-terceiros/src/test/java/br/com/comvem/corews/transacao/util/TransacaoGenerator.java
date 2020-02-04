package br.com.comvem.corews.transacao.util;

import br.com.comven.corews.transacao.bean.SaidaBean;
import br.com.comven.corews.transacao.domain.CancelamentoVenda;
import br.com.comven.corews.transacao.domain.ComunicacaoVenda;
import br.com.comven.corews.transacao.domain.Consulta925;

public class TransacaoGenerator {

	public static ComunicacaoVenda generate233() {

		ComunicacaoVenda cve = new ComunicacaoVenda();
		return cve;
	}

	public static CancelamentoVenda generate234() {

		CancelamentoVenda can = new CancelamentoVenda();
		return can;
	}

	public static Consulta925 generate925() {

		Consulta925 con = new Consulta925();
		return con;
	}

	public static SaidaBean generate925Retorno() {

		SaidaBean retorno = new SaidaBean();
		return retorno;
	}

	public static SaidaBean generate233Retorno() {

		SaidaBean retorno = new SaidaBean();
		return retorno;
	}

	public static SaidaBean generate234Retorno() {

		SaidaBean retorno = new SaidaBean();
		return retorno;
	}

}
