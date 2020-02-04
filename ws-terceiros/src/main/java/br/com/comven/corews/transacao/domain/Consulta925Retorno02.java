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

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "T925_RT02")
@Table
public class Consulta925Retorno02 {

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
	
	@Column(name = "NUM_IDENT_PROPRIETARIO")
	private String numIdentProprietario;
	
	@Column(name = "NOME_PROPRIETARIO")
	private String nomeProprietario;
	
	@Column(name = "ANO_FABRICACAO")
	private Integer anoFabricacao;
	
	@Column(name = "ANO_MODELO")
	private Integer anoModelo;
	
	@Column(name = "UF_EMISSAO")
	private String ufEmissao;
	
	@Column(name = "NUMERO_CRV")
	private Long numeroCRV;
	
	@Column(name = "DATA_EMISSAO")
	private Integer dataEmissao;
	
	@Column(name = "NUMERO_VIA")
	private Integer numeroVia;
	
	@Column(name = "TIPO_DOC_COMPRADOR")
	private Integer tipoDocComprador;
	
	@Column(name = "NUM_IDENT_COMPRADOR")
	private String numIdentComprador;
	
	@Column(name = "NOME_COMPRADOR")
	private String nomeComprador;
	
	@Column(name = "NOME_LOGRADOURO_COMPRADOR")
	private String nomeLogradouroComprador;
	
	@Column(name = "NUMERO_IMOVEL_COMPRADOR")
	private String numeroImovelComprador;
	
	@Column(name = "COMPLEMENTO_IMOVEL_COMPRADOR")
	private String complementoImovelComprador;
	
	@Column(name = "BAIRRO_IMOVEL_COMPRADOR")
	private String bairroImovelComprador;
	
	@Column(name = "COD_MUNICIPIO_IMOVEL_COMPRADOR")
	private Integer codMunicipioImovelComprador;
	
	@Column(name = "UF_IMOVEL_COMPRADOR")
	private String ufImovelComprador;
	
	@Column(name = "CEP_IMOVEL_COMPRADOR")
	private String cepImovelComprador;
	
	@Column(name = "COD_MUNICIPIO_LOCAL_VENDA")
	private Integer codMunicipioLocalVenda;
	
	@Column(name = "DATA_VENDA")
	private String dataVenda;
	
	@Column(name = "TIPO_DOC_CARTORIO")
	private Integer tipoDocCartorio;
	
	@Column(name = "NUM_IDENT_CARTORIO")
	private String numIdentCartorio;
	
	@Column(name = "NOME_CARTORIO")
	private String nomeCartorio;
	
	@Column(name = "NOME_LOGRADOURO_CARTORIO")
	private String nomeLogradouroCartorio;
	
	@Column(name = "NUMERO_IMOVEL_CARTORIO")
	private String numeroImovelCartorio;
	
	@Column(name = "COMPLEMENTO_IMOVEL_CARTORIO")
	private String complementoImovelCartorio;
	
	@Column(name = "BAIRRO_IMOVEL_CARTORIO")
	private String bairroImovelCartorio;
	
	@Column(name = "COD_MUNICIPIO_IMOVEL_CARTORIO")
	private Integer codMunicipioImovelCartorio;
	
	@Column(name = "UF_IMOVEL_CARTORIO")
	private String ufImovelCartorio;
	
	@Column(name = "CEP_IMOVEL_CARTORIO")
	private String cepImovelCartorio;
	
	@Column(name = "DDD_TEL_CARTORIO")
	private Integer dddTelCartorio;
	
	@Column(name = "NUM_TEL_CARTORIO")
	private Integer numTelCartorio;
	
	@Column(name = "NUM_IDENT_CV")
	private Long numIdentCV;
	
	@Column(name = "DATA_REGISTRO")
	private Integer dataRegistro;
	
	@Column(name = "HORA_REGISTRO")
	private Integer horaRegistro;
	
	@Column(name = "DATA_ACEITE_UF_REGISTRO")
	private Integer dataAceiteUFRegistro;
	
	@Column(name = "UF_ORIGEM_CANCELAMENTO")
	private String ufOrigemCancelamento;
	
	@Column(name = "MOTIVO_CANCELAMENTO")
	private Byte motivoCancelamento;
	
	@Column(name = "DATA_CANCELAMENTO")
	private Integer dataCancelamento;
	
	@Column(name = "HORA_CANCELAMENTO")
	private Integer horaCancelamento;
	
	@Column(name = "DATA_ACEITE_UF_CANCELAMENTO")
	private Integer dataAceiteUFCancelamento;
	
	@Column(name = "INDICADOR_SITUACAO")
	private Integer indicadorSituacao;
	
	@Column(name = "DATA_SITUACAO")
	private Integer dataSituacao;
	
	@Column(name = "HORA_SITUACAO")
	private Integer horaSituacao;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IDTRANSACAO")
	private Transacao transacao;
	
	public Consulta925Retorno02(String retornoAplic) {		
		
		this.codigoRetorno = new Integer(retornoAplic.substring(37,40));
		this.placaUnica = retornoAplic.substring(40,47) == null ? null : retornoAplic.substring(40,47).trim();
		this.renavam = new Long(retornoAplic.substring(47,58));
		this.numIdentProprietario = retornoAplic.substring(58,72) == null ? null : retornoAplic.substring(58,72).trim();
		this.nomeProprietario = retornoAplic.substring(72,112) == null ? null : retornoAplic.substring(72,112).trim();
		this.anoFabricacao = new Integer(retornoAplic.substring(112,116));
		this.anoModelo = new Integer(retornoAplic.substring(116,120));
		this.ufEmissao = retornoAplic.substring(120,122);
		
		this.numeroCRV= new Long(retornoAplic.substring(122,134));
		this.dataEmissao = new Integer(retornoAplic.substring(134, 142));
		this.numeroVia = new Integer(retornoAplic.substring(142, 144));
		this.tipoDocComprador = new Integer(retornoAplic.substring(144, 145));
		this.numIdentComprador = retornoAplic.substring(145, 159);
		this.nomeComprador = retornoAplic.substring(159,199) == null ? null : retornoAplic.substring(155,199).trim();
		this.nomeLogradouroComprador = retornoAplic.substring(199,229) == null ? null : retornoAplic.substring(199,229).trim();
		this.numeroImovelComprador = retornoAplic.substring(229,234) == null ? null : retornoAplic.substring(229,234).trim();
		this.complementoImovelComprador = retornoAplic.substring(234,254) == null ? null : retornoAplic.substring(234,254).trim();
		this.bairroImovelComprador = retornoAplic.substring(254,274) == null ? null : retornoAplic.substring(254,274).trim();
		this.codMunicipioImovelComprador = new Integer(retornoAplic.substring( 274, 278));
		this.ufImovelComprador = retornoAplic.substring(278, 280);
		this.cepImovelComprador = retornoAplic.substring(280, 288);
		this.codMunicipioLocalVenda = new Integer(retornoAplic.substring(288, 292));
		this.dataVenda = retornoAplic.substring(292, 300);
		this.tipoDocCartorio = new Integer(retornoAplic.substring(300, 301));
		this.numIdentCartorio = retornoAplic.substring(301, 315);
		this.nomeCartorio = retornoAplic.substring(315,355) == null ? null : retornoAplic.substring(315,355).trim();
		this.nomeLogradouroCartorio = retornoAplic.substring(355,385) == null ? null : retornoAplic.substring(355,385).trim();
		
		this.numeroImovelCartorio = retornoAplic.substring(385,390) == null ? null : retornoAplic.substring(385,390).trim();
		this.complementoImovelCartorio = retornoAplic.substring(390,410) == null ? null : retornoAplic.substring(390,410).trim();
		this.bairroImovelCartorio = retornoAplic.substring(410,430) == null ? null : retornoAplic.substring(410,430).trim();
		
		this.codMunicipioImovelCartorio = new Integer(retornoAplic.substring(430,434));
		this.ufImovelCartorio = retornoAplic.substring(434, 436);
		this.cepImovelCartorio = retornoAplic.substring(436, 444);
		this.dddTelCartorio = new Integer(retornoAplic.substring(444, 446));
		this.numTelCartorio = new Integer(retornoAplic.substring(446, 455));
		this.numIdentCV = new Long(retornoAplic.substring(455, 467));
		this.dataRegistro = new Integer(retornoAplic.substring(467, 475));
		this.horaRegistro = new Integer(retornoAplic.substring(475, 481));
		this.dataAceiteUFRegistro = new Integer(retornoAplic.substring(481, 489));
		this.ufOrigemCancelamento = retornoAplic.substring(489, 491);
		this.motivoCancelamento = new Byte(retornoAplic.substring(491, 492));
		this.dataCancelamento = new Integer(retornoAplic.substring(492, 500));
		this.horaCancelamento = new Integer(retornoAplic.substring(500, 506));
		this.dataAceiteUFCancelamento = new Integer(retornoAplic.substring(506, 514));
		this.indicadorSituacao = new Integer(retornoAplic.substring(514, 515));
		this.dataSituacao = new Integer(retornoAplic.substring(515, 523));
		this.horaSituacao = new Integer(retornoAplic.substring(523, 529));	
	}

	
	
}
