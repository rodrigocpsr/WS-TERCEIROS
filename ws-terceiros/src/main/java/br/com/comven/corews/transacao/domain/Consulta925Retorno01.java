package br.com.comven.corews.transacao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Entity(name = "T925_RT01")
@Table
public class Consulta925Retorno01 {

	@Id
	@Column(name = "IDTRANSACAO")
	private Long id;

	@Column(name = "DIA_JULIANO")
	private Short diaJuliano;
	
	@Column(name = "CODIGO_RETORNO")
	private Integer codigoRetorno;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDTRANSACAO")
	private Transacao transacao;
	
	public Consulta925Retorno01(String retornoAplic) {
		this.codigoRetorno = new Integer(retornoAplic.substring(37,40));
	}

	
	
}
