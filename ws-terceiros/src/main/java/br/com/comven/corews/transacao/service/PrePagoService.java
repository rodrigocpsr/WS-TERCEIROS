package br.com.comven.corews.transacao.service;

public interface PrePagoService {

	Boolean confirmarTransacaoPrePaga(Integer idCompromissoPrePago) throws Exception;

	Integer reservarTransacaoPrePagaCpfCnpj(String cpfCnpj) throws Exception;

	String getStatusClienteSemCreditoPrePagoCpfCnpj(String cpfCnpj) throws Exception;

	Integer verificaTransacoesPrePagoCpfCnpj(String cpfCnpj) throws Exception;
	
	Integer consultaTransacoesPrePagoCpfCnpj(String cpfCnpj) throws Exception;

}
