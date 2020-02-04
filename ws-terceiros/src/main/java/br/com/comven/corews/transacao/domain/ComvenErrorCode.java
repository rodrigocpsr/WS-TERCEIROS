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
@Entity(name="COMVEN_ERROR_CODE")
@Table
public class ComvenErrorCode {
	
	@Id
	@Column(name = "CODIGO_ERRO")
	private Integer codigoErro;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "TENTATIVAS_BLOQUEIO")
	private Integer tentativasBloqueio;
	
	@Column(name = "SECURITY_HASH")
	private String securityHash;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ALTERACAO")
	private Date dataAlteracao;

	
}
