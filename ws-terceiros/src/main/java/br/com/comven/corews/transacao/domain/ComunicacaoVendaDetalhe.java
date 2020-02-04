package br.com.comven.corews.transacao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "t233_ev01_detalhe")
@Table
public class ComunicacaoVendaDetalhe {

	@Id
	@Column(name = "IDTRANSACAO")
	private Long id;

	@Column(name = "DIA_JULIANO")
	private Short diaJuliano;
	
	@Column(name = "VEICULO_MARCA")
	private String marca;
	
	@Column(name = "VEICULO_MODELO")
	private String modelo;
	
	@Column(name = "VEICULO_DETALHE")
	private String detalhe;
	
	
	
}
