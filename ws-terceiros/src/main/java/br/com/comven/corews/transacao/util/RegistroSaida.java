package br.com.comven.corews.transacao.util;

public class RegistroSaida {
	private static final String TIPO_REGISTRO = "2";
	private String diaJuliano;
	private String numeroTransacaoComven;
	private String retornoTransacao;
	private String ufVeiculo;
	private String placa;
	private String renavam;
	private String marca;
	private String modelo;
	private String dataHoraTransacao;
	private String chassi;
	
	public RegistroSaida() {
	}

	public RegistroSaida(String diaJuliano, String numeroTransacaoComven,
			String retornoTransacao, String ufVeiculo, String placa,
			String renavam, String chassi, String marca, String modelo,
			String dataHoraTransacao) {
		super();
		this.diaJuliano = diaJuliano;
		this.numeroTransacaoComven = numeroTransacaoComven;
		this.retornoTransacao = retornoTransacao;
		this.ufVeiculo = ufVeiculo;
		this.placa = placa;
		this.renavam = renavam;
		this.marca = marca;
		this.modelo = modelo;
		this.dataHoraTransacao = dataHoraTransacao;
	}



	public static String getTipoRegistro() {
		return TIPO_REGISTRO;
	}

	public String getDiaJuliano() {
		return diaJuliano;
	}

	public void setDiaJuliano(String diaJuliano) {
		this.diaJuliano = diaJuliano;
	}

	public String getNumeroTransacaoComven() {
		return numeroTransacaoComven;
	}

	public void setNumeroTransacaoComven(String numeroTransacaoComven) {
		this.numeroTransacaoComven = numeroTransacaoComven;
	}

	public String getRetornoTransacao() {
		return retornoTransacao;
	}

	public void setRetornoTransacao(String retornoTransacao) {
		this.retornoTransacao = retornoTransacao;
	}

	public String getUfVeiculo() {
		return ufVeiculo;
	}

	public void setUfVeiculo(String ufVeiculo) {
		this.ufVeiculo = ufVeiculo;
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

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDataHoraTransacao() {
		return dataHoraTransacao;
	}

	public void setDataHoraTransacao(String dataHoraTransacao) {
		this.dataHoraTransacao = dataHoraTransacao;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	@Override
	@Deprecated
	/***
	 * Registros de Saidas agora são dependentes de layout {@link #AbstractLayout.getStringTrailler(RegistroSaida saida)} 
	 */
	public String toString() {
		return	getTipoRegistro() + ";" + 
				getDiaJuliano() + ";" + 
				getNumeroTransacaoComven() + ";" + 
				getRetornoTransacao() + ";" + 
				getUfVeiculo() + ";" + 
				getPlaca() + ";" + 
				getRenavam() + ";" +
				getChassi() + ";" +
				getMarca() + ";" + 
				getModelo() + ";" + 
				getDataHoraTransacao()+ ";";
	}
	
}
