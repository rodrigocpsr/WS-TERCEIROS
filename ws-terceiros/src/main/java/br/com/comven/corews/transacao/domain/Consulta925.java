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
import oracle.jdbc.proxy.annotation.Post;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name="T925_EV01")
@Table
public final class Consulta925 {

	@Id
	@Column(name = "IDTRANSACAO")
	private Long id;

	@Column(name = "DIA_JULIANO")
	private Short diaJuliano;

	@Column(name = "PLACA_UNICA")
	private String placaUnica;

	@Column(name = "RENAVAM")
	private String renavam;

	@Column(name = "TIPO_DOC_CARTORIO")
	private Integer tipoDocCartorio;

	@Column(name = "NUM_IDENT_CARTORIO")
	private String numIdentCartorio;

	@Column(name = "NUM_IDENT_CV")
	private Long numIdentCV;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDTRANSACAO")
	private Transacao transacao;

	public Consulta925(Transacao transacao, ComunicacaoVenda cve) {

		this.id = cve.getId();
		this.diaJuliano = cve.getDiaJuliano();
		this.placaUnica = cve.getPlacaUnica();
		this.renavam = cve.getRenavam();
		this.tipoDocCartorio = cve.getTipoDocCartorio();
		this.numIdentCartorio = cve.getNumIdentCartorio();
		this.numIdentCV = cve.getNumIdentCV();

	}

}
