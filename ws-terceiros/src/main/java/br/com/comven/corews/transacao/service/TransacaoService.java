package br.com.comven.corews.transacao.service;

import br.com.comven.corews.transacao.bean.SaidaBean;
import br.com.comven.corews.transacao.domain.CancelamentoVenda;
import br.com.comven.corews.transacao.domain.ComunicacaoVenda;
import br.com.comven.corews.transacao.domain.Consulta925;

public interface TransacaoService {

	SaidaBean cancelarVendaVeiculo234(String operador, String cnpjResponsavel, CancelamentoVenda bean) throws Exception;
	SaidaBean consulta925(String operador, String cnpjResponsavel, Consulta925 bean) throws Exception;
	SaidaBean comunicarVendaVeiculo233(String operador, String cnpjResponsavel, ComunicacaoVenda cve) throws Exception;
	SaidaBean consultaPrepago(String operador, String cnpjResponsavel) throws Exception;

}
