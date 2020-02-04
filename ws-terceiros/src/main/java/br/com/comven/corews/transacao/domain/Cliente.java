package br.com.comven.corews.transacao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Entity
@Table
public class Cliente {
	
	@Id
	@Column(name = "IDCLIENTE")
	private Long idCliente;
	
	@Column(name = "FK_ENDERECO")
	private Long idEndereco;
	
	@Column(name = "FK_GRUPO_EMPRESARIAL")
	private Long idGrupoEmpresarial;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "CNPJ")
	private String cnpj;
	
	@Column(name = "DDD")
	private String ddd;
	
	@Column(name = "TELEFONE")
	private String telefone;
	
	@Column(name = "TIPO_INSTITUICAO")
	private String tipoInstituicao;
	
	@Column(name = "TIPO_PAGAMENTO")
	private String tipoPagamento;
	
	@Column(name = "STATUS")
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CRIACAO")
	private Date dataCriacao;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ALTERACAO")
	private Date dataAlteracao;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_EXCLUSAO")
	private Date dataExclusao;
	
	@Column(name = "FG_PROCURACAO")
	private String fgProcuracao;
	
	@Column(name = "FG_ALLUF_PROCURACAO")
	private String fgAllUFProcuracao;
	
	@Column(name = "CODIGO_SERPRO")
	private String codigoSERPRO;
	
	@Column(name = "NOME_TABELIAO")
	private String nomeTabeliao;
	
	@Column(name = "CPF_TABELIAO")
	private String cpfTabeliao;
	
	@Column(name = "CODIGO_CARTORIO")
	private String codigoCartorio;
	
	@Column(name = "TOKEN")
	private String token;
	
	@Column(name = "FG_ACESSO_WS233")
	private String fgAcessoWs233;
	
	@Column(name = "FG_ACESSO_WS234")
	private String fgAcessoWs234;
	
	@Column(name = "FG_ACESSO_WS925")
	private String fgAcessoWs925;
	
	@Column(name = "WS_KEY")
	private String wsKey;

	
}
