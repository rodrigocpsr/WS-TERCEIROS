package br.com.comven.corews.transacao.domain;

import java.text.MessageFormat;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Entity(name = "T234_EV01")
@Table
public final class CancelamentoVenda {

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
	private Long codigoSegurancaCRV;
	
	@Column(name = "TIPO_DOC_CARTORIO")
	private Integer tipoDocCartorio;
	
	@Column(name = "NUM_IDENT_CARTORIO")
	private String numIdentCartorio;
	
	@Column(name = "NUM_IDENT_CV")
	private Long numIdentCV;
	
	@Column(name = "MOTIVO_CANCELAMENTO")
	private Integer motivoCancelamento;
	
	@Transient
	private String bloquearCVE;
	
	@Transient
	private Transacao transacao;

	public CancelamentoVenda cveToCancelamento(ComunicacaoVenda cve) {
		
		this.placaUnica = cve.getPlacaUnica();
		this.renavam = cve.getRenavam();
		this.numIdentProprietario = cve.getNumIdentProprietario();
		this.nomeProprietario = cve.getNomeProprietario();
		this.anoFabricacao = cve.getAnoFabricacao();
		this.anoModelo = cve.getAnoModelo();
		this.ufEmissao = cve.getUfEmissao();
		this.numeroCRV = cve.getNumeroCRV();
		this.dataEmissao = cve.getDataEmissao();
		this.numeroVia = cve.getNumeroVia();
		this.tipoDocCartorio = cve.getTipoDocCartorio();
		this.numIdentCartorio = cve.getNumIdentCartorio();
		this.numIdentCV = cve.getNumIdentCV();
		
		return this;
	}
	
	/**
	 * Verifica se os dados informados no bean estão de acordo com o esperado pelo modelo
	 * 
	 * @return
	 * @throws ValidationException
	 */
	public boolean valida() throws ValidationException {

		if (this.getRenavam() == null || this.getRenavam().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "renavam"));
		} else {
			if (!ValidatorUtil.validarRenavam(this.getRenavam() + "")) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_INVALID_VALIDATION_MSG, "renavam"));
			}
		}
		
		if (this.getNumIdentCV() == null || this.getNumIdentCV().equals("")) {
			throw new ValidationException(MessageFormat.format(Mensagem.MODEL_MANDATORY_VALIDATION_MSG, "numIdentCV"));
		} else {
			if (this.getNumIdentCV() > 999999999999l) {
				throw new ValidationException(MessageFormat.format(Mensagem.MODEL_SIZE_VALIDATION_MSG, "numIdentCV", "12"));
			}
		}
		
		return true;
	}

	

}
