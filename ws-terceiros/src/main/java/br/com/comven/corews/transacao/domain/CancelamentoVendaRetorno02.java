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
@Entity(name = "T234_RT02")
@Table
public class CancelamentoVendaRetorno02 implements java.io.Serializable {

	private static final long serialVersionUID = -4323530002324050391L;

	@Id
	@Column(name = "IDTRANSACAO")
	private Long id;

	@Column(name = "DIA_JULIANO")
	private Short diaJuliano;
	
	@Column(name = "CODIGO_RETORNO")
	private Integer codigoRetorno;
	
	@Column(name = "PLACA_UNICA")
	private String placaUnica;
	
	@Column(name = "RENAVAM")
	private Long renavam;

	@Column(name = "NUM_IDENT_CV")
	private Long numIdentCV;
	
	@Column(name = "MOTIVO_CANCELAMENTO_CV")
	private Byte motivoCancelamento;
	
	@Column(name = "DATA_REGISTRO")
	private Integer dataRegistro;
	
	@Column(name = "HORA_REGISTRO")
	private Integer horaRegistro;
	
	@Column(name = "DATA_CANCELAMENTO")
	private Integer dataCancelamento;
	
	@Column(name = "HORA_CANCELAMENTO")
	private Integer horaCancelamento;
	
	public CancelamentoVendaRetorno02(String retornoAplic) {
		
		this.codigoRetorno = new Integer(retornoAplic.substring(37,40));
		this.placaUnica = retornoAplic.substring(40,47);
		
		this.renavam = new Long(retornoAplic.substring(47,58));
		this.numIdentCV = new Long(retornoAplic.substring(58,70));
		this.motivoCancelamento = new Byte(retornoAplic.substring(70,71));
		this.dataRegistro = new Integer(retornoAplic.substring(71,79));
		this.horaRegistro = new Integer(retornoAplic.substring(79,85));
		this.dataCancelamento = new Integer(retornoAplic.substring(85,93));
		this.horaCancelamento = new Integer(retornoAplic.substring(93,99));
		
	}	
	

	
}

