package br.com.comven.corews.transacao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Entity(name="TRANSACAO_NOVA_BLOQUEIO")
@Table
public final class TransacaoBloqueio {

	@Id
	@Column(name = "IDTRANSACAO")
	private Long id;

	@Column(name = "FK_CODIGO_ERRO")
	private Integer codigoErro;
	
	@Column(name = "PLACA")
	private String placa;
	
	@Column(name = "RENAVAM")
	private String renavam;
	
	@Column(name = "NUM_IDENT_PROPRIETARIO")
	private String numIdentProprietario;
	
	@Column(name = "NUM_IDENT_OPERADOR")
	private String numIdentOperador;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_BLOQUEIO")
	private Date dataBloqueio;
	
	public TransacaoBloqueio(Long id, Integer codigoErro,
			String placa, String renavam, String numIdentProprietario,
			String numIdentOperador) {
		this.id = id;
		this.codigoErro = codigoErro;
		this.placa = placa;
		this.renavam = renavam;
		this.numIdentProprietario = numIdentProprietario;
		this.numIdentOperador = numIdentOperador;
	}

}
