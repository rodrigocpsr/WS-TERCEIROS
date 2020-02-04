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
@Entity(name = "T233_RT02")
@Table
public class ComunicacaoVendaRetorno02 {

	@Id
	@Column(name = "IDTRANSACAO")
	private Long id;

	@Column(name = "DIA_JULIANO")
	private Long diaJuliano;
	
	@Column(name = "CODIGO_RETORNO")
	private Integer codigoRetorno;
	
	@Column(name = "PLACA_UNICA")
	private String placaUnica;
	
	@Column(name = "RENAVAM")
	private Long renavam;

	@Column(name = "NUM_IDENT_CV")
	private Long numIdentCV;
	
	@Column(name = "DATA_REGISTRO")
	private Integer dataRegistro;
	
	@Column(name = "HORA_REGISTRO")
	private Integer horaRegistro;
	
	public ComunicacaoVendaRetorno02(String retornoAplic) {
		
		this.codigoRetorno = new Integer(retornoAplic.substring(37,40));
		this.placaUnica = retornoAplic.substring(40,47);
		this.renavam = new Long(retornoAplic.substring(47,58));
		this.numIdentCV = new Long(retornoAplic.substring(58,70));
		this.dataRegistro = new Integer(retornoAplic.substring(70,78));
		this.horaRegistro = new Integer(retornoAplic.substring(78,84));
		
	}
	
}
