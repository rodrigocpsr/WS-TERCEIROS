package br.com.comven.corews.transacao.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.comven.corews.transacao.domain.Consulta925Retorno02;

@JsonInclude(Include.NON_NULL)
public class SaidaBean {
	
	private String placa;
	private String renavam;
	private String numIdentCV;
	private String dataHoraTransacao;
	private String codigoRetorno;
	
	private String mensagem;
	
	private Consulta925Retorno02 resultado;
	
	private Boolean transacaoEfetuada;

	public SaidaBean() {
		super();
	}

	public SaidaBean(String mensagem) {
		super();
		this.mensagem = mensagem;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public String getNumIdentCV() {
		return numIdentCV;
	}

	public void setNumIdentCV(String numIdentCV) {
		this.numIdentCV = numIdentCV;
	}

	public String getDataHoraTransacao() {
		return dataHoraTransacao;
	}

	public void setDataHoraTransacao(String dataHoraTransacao) {
		this.dataHoraTransacao = dataHoraTransacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Boolean getTransacaoEfetuada() {
		return transacaoEfetuada;
	}

	public void setTransacaoEfetuada(Boolean transacaoEfetuada) {
		this.transacaoEfetuada = transacaoEfetuada;
	}

	public Consulta925Retorno02 getResultado() {
		return resultado;
	}

	public void setResultado(Consulta925Retorno02 resultado) {
		this.resultado = resultado;
	}

	public String getCodigoRetorno() {
		return codigoRetorno;
	}

	public void setCodigoRetorno(String codigoRetorno) {
		this.codigoRetorno = codigoRetorno;
	}
	
	

}
