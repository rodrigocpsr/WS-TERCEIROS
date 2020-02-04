package br.com.comven.corews.transacao.domain;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.ValidationException;

import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.LongValidator;

import br.com.comven.corews.transacao.util.DateUtil;
import br.com.comven.corews.transacao.util.Mensagem;
import br.com.comven.corews.transacao.util.ValidatorUtil;
import br.com.comven.corews.transacao.util.enumerator.ESiglaEstado;
import br.com.comven.corews.transacao.util.enumerator.ESimNao;
import br.com.comven.corews.transacao.util.enumerator.ETipoOperacao;
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
@Entity(name = "T233_EV01")
@Table
public class ComunicacaoVenda {

	@Id
	@Column(name = "IDTRANSACAO")
	private Long id;

	@Column(name = "DIA_JULIANO")
	private Short diaJuliano;

	@Column(name = "PLACA_UNICA")
	private String placaUnica;

	@Column(name = "RENAVAM")
	private String renavam;

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
	private String numeroCRV;

	@Column(name = "DATA_EMISSAO")
	private String dataEmissao;

	@Column(name = "NUMERO_VIA")
	private String numeroVia;

	@Column(name = "CODIGO_SEGURANCA_CRV")
	private String codigoSegurancaCRV;

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

	@Column(name = "CEP_IMOVEL_CARTORIO")
	private String cepImovelCartorio;

	@Column(name = "DDD_TEL_CARTORIO")
	private Integer dddTelCartorio;

	@Column(name = "NUM_TEL_CARTORIO")
	private Long numTelCartorio;

	@Column(name = "NUM_IDENT_CV")
	private Long numIdentCV;

	@Column(name = "UF_IMOVEL_COMPRADOR")
	private String ufImovelComprador;

	@Column(name = "UF_IMOVEL_CARTORIO")
	private String ufImovelCartorio;

	@Column(name = "ORIGEM_TRANSACAO")
	private String origemTransacao;

	@Column(name = "NUMERO_CNPJ_IF")
	private String numeroCnpjIF;

	@Column(name = "COMPRADOR_CELULAR")
	private String compradorCelular;

	@Column(name = "VALOR_TRANSACAO")
	private BigDecimal valorTransacao;

	@Column(name = "FG_BLOQUEIO")
	private String fgBloqueio;

	@Transient
	private Transacao transacao;

	@Transient
	private String operacao;

	@Transient
	private Integer idCompromisso;

	@Transient
	private String seloReconhecimento;

	@Transient
	private Long ticketVolante;

	@Transient
	private String marca;

	@Transient
	private String modelo;

	@Transient
	private String detalhe;

	@Transient
	private String procuracao;

	public ComunicacaoVendaDetalhe convertDetalhe() {

		ComunicacaoVendaDetalhe detalhe = new ComunicacaoVendaDetalhe();

		detalhe.setId(this.getId());
		detalhe.setDiaJuliano(this.getDiaJuliano());
		detalhe.setMarca(this.getMarca());
		detalhe.setModelo(this.getModelo());
		detalhe.setDetalhe(this.getDetalhe());

		return detalhe;
	}

	/**
	 * Verifica se os dados informados no bean estão de acordo com o esperado pelo modelo
	 * 
	 * @return
	 * @throws ValidationException
	 */
	public boolean valida() throws ValidationException {

		LongValidator longValidator = LongValidator.getInstance();
		DoubleValidator doubleValidator = DoubleValidator.getInstance();

		// documento do comprador
		if (this.getTipoDocComprador() == null)
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "tipoDocComprador"));

		if (this.getNumIdentComprador() == null)
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "numIdentComprador"));
		else if (!longValidator.isValid(this.getNumIdentComprador())) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_NAN_VALIDATION_MSG, "numIdentComprador"));
		}

		if (this.getTipoDocComprador() == 1) {

			if (!ValidatorUtil.isValidCPF(this.getNumIdentComprador())) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "numIdentComprador"));
			}

		} else if (this.getTipoDocComprador() == 2) {
			if (!ValidatorUtil.isValidCNPJ(this.getNumIdentComprador())) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "numIdentComprador"));
			}

		} else {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "tipoDocComprador"));
		}

		if (this.getPlacaUnica() == null || this.getPlacaUnica().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "placaUnica"));
		} else {

			this.setPlacaUnica(ValidatorUtil.cleanMask(this.getPlacaUnica().toUpperCase()));

			if (!ValidatorUtil.isValidPlaca(this.getPlacaUnica())) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "placaUnica"));
			}
		}

		if (this.getRenavam() == null || this.getRenavam().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "renavam"));
		} else {
			if (!ValidatorUtil.validarRenavam(this.getRenavam() + "")) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "renavam"));
			}
		}

		if (this.getNumIdentProprietario() == null || this.getNumIdentProprietario().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "numIdentProprietario"));
		} else if (!longValidator.isValid(this.getNumIdentProprietario())) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_NAN_VALIDATION_MSG, "numIdentProprietario"));
		} else {

			if (this.getNumIdentProprietario().length() == 11) {
				if (!ValidatorUtil.isValidCPF(this.getNumIdentProprietario())) {
					throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "numIdentProprietario"));
				}
			} else if (this.getNumIdentProprietario().length() == 14) {
				if (!ValidatorUtil.isValidCNPJ(this.getNumIdentProprietario())) {
					throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "numIdentProprietario"));
				}
			} else {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "numIdentProprietario"));
			}

		}

		if (this.getNomeProprietario() == null || this.getNomeProprietario().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "nomeProprietario"));
		} else if (this.getNomeProprietario().length() > 40) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "nomeProprietario", "40"));
		}

		if (this.getAnoFabricacao() == null || this.getAnoFabricacao().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "anoFabricacao"));
		} else if (this.getAnoFabricacao() > 9999) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "anoFabricacao", "4"));
		}
		
		if (this.getAnoModelo() == null || this.getAnoModelo().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "anoModelo"));
		} else if (this.getAnoModelo() > 9999) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "anoModelo", "4"));
		}
		
		if (this.getAnoFabricacao() != null && !this.getAnoFabricacao().equals("") && 
				this.getAnoModelo() != null && !this.getAnoModelo().equals("")) {
			if (this.getAnoModelo().intValue() < this.getAnoFabricacao())
				throw new ValidationException(Mensagem.TRANSACAO_ANO_MODELO_ANO_FAB);
		}
		
		if (this.getUfEmissao() == null || this.getUfEmissao().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "ufEmissao"));
		} else {
			if (ESiglaEstado.fromId(this.getUfEmissao()) == null)
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "ufEmissao"));
		}
		
		if (this.getNumeroCRV() == null || this.getNumeroCRV().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "numeroCRV"));
		} else if (this.getNumeroCRV().length() > 12) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "numeroCRV", "12"));
		} else {
			if (this.getNumeroCRV().equals("111111111111") ||
					this.getNumeroCRV().equals("222222222222") || this.getNumeroCRV().equals("333333333333") ||
					this.getNumeroCRV().equals("444444444444") || this.getNumeroCRV().equals("555555555555") ||
					this.getNumeroCRV().equals("666666666666") || this.getNumeroCRV().equals("777777777777") ||
					this.getNumeroCRV().equals("888888888888") || this.getNumeroCRV().equals("999999999999"))
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "numeroCRV"));
		}
		
		if (this.getDataEmissao() == null || this.getDataEmissao().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "dataEmissao"));
		} else {
			try {
				
				if (Integer.parseInt(this.getDataEmissao()) != 0)
					DateUtil.converteDataDenatran(this.getDataEmissao()+"");
				
			} catch (ParseException e) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "dataEmissao"));
			}
		}
		
		if (this.getNumeroVia() == null || this.getNumeroVia().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "numeroVia"));
		} else if (this.getNumeroVia().length() > 2) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "numeroVia", "2"));
		}
		
		if (this.getCodigoSegurancaCRV() == null || this.getCodigoSegurancaCRV().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "codigoSegurancaCRV"));
		} else if (this.getCodigoSegurancaCRV().length() > 11) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "codigoSegurancaCRV", "11"));
		} else {
			if (this.getCodigoSegurancaCRV().equals("11111111111") ||
					this.getCodigoSegurancaCRV().equals("22222222222") || this.getCodigoSegurancaCRV().equals("33333333333") ||
					this.getCodigoSegurancaCRV().equals("44444444444") || this.getCodigoSegurancaCRV().equals("55555555555") ||
					this.getCodigoSegurancaCRV().equals("66666666666") || this.getCodigoSegurancaCRV().equals("77777777777") ||
					this.getCodigoSegurancaCRV().equals("88888888888") || this.getCodigoSegurancaCRV().equals("99999999999"))
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "codigoSegurancaCRV"));
		}
		
		if (this.getNomeComprador() == null || this.getNomeComprador().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "nomeComprador"));
		} else if (this.getNomeComprador().length() > 40) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "nomeComprador", "40"));
		}
		
		if (this.getNomeLogradouroComprador() == null || this.getNomeLogradouroComprador().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "nomeLogradouroComprador"));
		} else if (this.getNomeLogradouroComprador().length() > 30) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "nomeLogradouroComprador", "30"));
		}
		
		if (this.getNumeroImovelComprador() == null || this.getNumeroImovelComprador().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "numeroImovelComprador"));
		} else if (this.getNumeroImovelComprador().length() > 5) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "numeroImovelComprador", "5"));
		}
		
		
//		if (this.getComplementoImovelComprador() != null) {
		if (this.getComplementoImovelComprador().length() > 20) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "complementoImovelComprador", "20"));
		}
//		} 
		
		if (this.getBairroImovelComprador() == null || this.getBairroImovelComprador().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "bairroImovelComprador"));
		} else if (this.getBairroImovelComprador().length() > 20) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "bairroImovelComprador", "20"));
		}
		
		//TODO: 
		//codMunicipioImovelComprador -> buscar o codigo pelo CEP informado no imovel do comprador
		//codMunicipioImovelCartorio -> buscar o codigo pelo CEP informado no imovel do cartorio
		//codMunicipioLocalVenda -> como buscar os campos se o cep não é informado ?
		
		if (this.getCepImovelComprador() == null || this.getCepImovelComprador().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "cepImovelComprador"));
		} else if (this.getCepImovelComprador().length() > 8) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "cepImovelComprador", "8"));
		}
		
		if (this.getUfImovelComprador() == null || this.getUfImovelComprador().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "ufImovelComprador"));
		} else {
			if (ESiglaEstado.fromId(this.getUfImovelComprador()) == null)
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "ufImovelComprador"));
		}
		
		if (this.getDataVenda() == null || this.getDataVenda().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "dataVenda"));
		} else {
			try {
				DateUtil.converteDataDenatran(this.getDataVenda()+"");
			} catch (ParseException e) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "dataVenda"));
			}
		}
		
		
		//DADOS DE CARTORIO

		if (this.getNumIdentCartorio() != null) {
			if (!ValidatorUtil.isValidCNPJ(this.getNumIdentCartorio())) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "numIdentCartorio"));
			}
		}
		
		if (this.getNomeCartorio() != null) {
			if (this.getNomeCartorio().length() > 40) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "nomeCartorio", "40"));
			}
		} 
		
		if (this.getNomeLogradouroCartorio() != null) {
			if (this.getNomeLogradouroCartorio().length() > 30) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "nomeLogradouroCartorio", "30"));
			}
		} 
		
		if (this.getNumeroImovelCartorio() != null) {
			if (this.getNumeroImovelCartorio().length() > 5) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "numeroImovelCartorio", "5"));
			}
		} 
		
		if (this.getComplementoImovelCartorio() != null) {
			if (this.getNumeroImovelCartorio().length() > 20) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "complementoImovelCartorio", "20"));
			}
		} 
		
		if (this.getBairroImovelCartorio() != null) {
			if (this.getBairroImovelCartorio().length() > 20) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "bairroImovelCartorio", "20"));
			}
		} 
		
		if (this.getCepImovelCartorio() != null) {
			if (this.getCepImovelCartorio().length() > 8) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "cepImovelCartorio", "8"));
			}
		} 
		
		if (this.getUfImovelCartorio() != null) {
			if (ESiglaEstado.fromId(this.getUfImovelCartorio()) == null)
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "ufImovelCartorio"));
		}
		
		if (this.getDddTelCartorio() != null) {
			if (this.getDddTelCartorio() > 99) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "dddTelCartorio", "2"));
			}
		}
		
		if (this.getNumTelCartorio() != null) {
			if (this.getNumTelCartorio() > 999999999l) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "numTelCartorio", "9"));
			}
		}

		
//		if (this.getCompradorCelular() != null) {
		if (this.getCompradorCelular().length() > 11) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "compradorCelular", "11"));
		}
//		} 
		
//		if (this.getValorTransacao() == null || this.getValorTransacao().equals("")) {
		if (this.getValorTransacao() == null) {
			valorTransacao = BigDecimal.ZERO;
//			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "valorTransacao"));
		}else if (!doubleValidator.isValid(this.getValorTransacao().doubleValue()+"")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_NAN_VALIDATION_MSG, "valorTransacao"));
		}
		
		if (this.getOperacao() == null || this.getOperacao().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "operacao"));
		}else if (ETipoOperacao.fromId(this.getOperacao()) == null) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "operacao"));
		}
		
//		if (this.getMarca() == null || this.getMarca().equals("")) {
//			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "marca"));
		if (this.getMarca().length() > 100) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "marca", "100"));
		}
		
//		if (this.getMarca() == null || this.getMarca().equals("")) {
//			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "marca"));
//		} else if (this.getMarca().length() > 100) {
//			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "marca", "100"));
//		}
		
//		if (this.getModelo() == null || this.getModelo().equals("")) {
//			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "modelo"));
		if (this.getModelo().length() > 100) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "modelo", "100"));
		}
		
//		if (this.getDetalhe() == null || this.getDetalhe().equals("")) {
//			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "detalhe"));
		if (this.getDetalhe().length() > 100) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "detalhe", "100"));
		}
		
		if (this.getProcuracao() == null || this.getProcuracao().equals("") || this.getProcuracao().equals(ESimNao.SIM.getCodigo()))
			this.setProcuracao(ESimNao.NAO.getCodigo());

		if (this.getProcuracao().equals(ESimNao.SIM.getCodigo())) {
//			if (this.getSeloReconhecimento() == null || this.getSeloReconhecimento().equals("")) {
//				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "seloReconhecimento"));
			if (this.getSeloReconhecimento().length() > 20) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "seloReconhecimento", "20"));
			}
		}
		
		return true;
	}
	
	

}
