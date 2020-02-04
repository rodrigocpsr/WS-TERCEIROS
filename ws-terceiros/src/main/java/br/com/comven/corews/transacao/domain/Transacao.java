package br.com.comven.corews.transacao.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Entity(name="TRANSACAO_NOVA")
@Table
public final class Transacao {

	@Id
	@Column(name = "IDTRANSACAO", nullable = false, precision = 22, scale = 0)
	private Long id;

	@Column(name = "DIA_JULIANO")
	private Short diaJuliano;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_TRANSACAO")
	private Date dataTransacao;

	@Column(name = "PARTE_FIXA")
	private String parteFixa;
	
	@Column(name = "ID_CERTIFICADO_MYSQL")
	private Long idCertificadoMysql;
	
	@Column(name = "IDLOTETRANSACAO")
	private Long idLoteTransacao;
	
	@Column(name = "IDITEMTRANSACAO_SEQ")
	private Long idItemTransacaoSequencial;
	
	@Column(name = "PARTE_VARIAVEL")
	private String parteVariavel;
	
	@Column(name = "ID_SEQ_DIARIA")
	private Long idSeqDiaria;
	
	@Column(name = "UF_TRANSACAO")
	private String ufTransacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_ENVIO")
	private Date dataEnvio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_INCLUSAO")
	private Date dataInclusao;
	
	@Column(name = "FK_CLIENTE")
	private Long idCLiente;
	
	@Column(name = "FK_CLIENTE_PARCEIRO")
	private Long idParceiro;
	
	@Column(name = "FK_USUARIO")
	private Long idUsuario;
	
	@Column(name = "OPERACAO")
	private String operacao;
	
	@Column(name = "FLG_DEMO")
	private String flgDemo;
	
	@Column(name = "NUMERO_COMPROMISSO_PRE")
	private Integer numeroCompromissoPre;
	
	@Column(name = "FK_LINHA_ARQ")
	private String idLinhaArquivo;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "RETORNO")
	private String retorno;
	
	@Column(name = "RETURN_SERPRO")
	private String retornoSERPRO;
	
	@Column(name = "FG_CANCELAR_AUTO")
	private String fgCancelarAuto;
	
	@Column(name = "SELO_PROCURACAO")
	private String seloProcuracao;
	
	@Column(name = "FK_TICKET_VOLANTE")
	private Long idTicketVolante;
	
	@Column(name = "FG_ORIGEM")
	private String fgOrigem;
	
	

}
