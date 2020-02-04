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
@Entity
@Table
public final class Parametro {

	@Id
	@GeneratedValue
	@Column(name = "IDPARAMETRO")
	private Long id;

	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "VALOR")
	private String valor;


}
