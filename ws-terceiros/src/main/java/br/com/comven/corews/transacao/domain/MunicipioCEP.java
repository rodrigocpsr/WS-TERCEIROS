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
@Entity(name="MUNICIPIO_CEP")
@Table
public final class MunicipioCEP {

	@Id
	@GeneratedValue
	@Column(name = "ID_MUNICIPIO_CEP")
	private Long id;

	@Column(name = "FK_MUNICIPIO")
	private Integer codigoMunicipioDenatran;

	@Column(name = "FAIXA_CEP_INICIAL")
	private Integer cepInicial;

	@Column(name = "FAIXA_CEP_FINAL")
	private Integer cepFinal;


}
