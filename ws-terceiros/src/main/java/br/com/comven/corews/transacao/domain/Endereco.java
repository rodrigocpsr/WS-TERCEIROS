package br.com.comven.corews.transacao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Endereco {
	
	@Id
	@Column(name = "IDENDERECO")
	private Long idEndereco;
	
	@Column(name = "CEP")
	private String cep;
	
	@Column(name = "CIDADE")
	private String cidade;
	
	@Column(name = "BAIRRO")
	private String bairro;
	
	@Column(name = "LOGRADOURO")
	private String logradouro;
	
	@Column(name = "COMPLEMENTO")
	private String complemento;
	
	@Column(name = "NUMERO")
	private Integer numero;
	
	@Column(name = "UF")
	private String uf;
	
	@Column(name = "MUNICIPIO_SERPRO")
	private String municipioSerpro;

	
}
