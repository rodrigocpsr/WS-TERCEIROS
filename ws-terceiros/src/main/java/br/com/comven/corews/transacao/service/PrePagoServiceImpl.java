package br.com.comven.corews.transacao.service;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import br.com.comven.corews.transacao.client.prepago.cpf.WSControleIntCOMVEN;
import br.com.comven.corews.transacao.util.Token;
import br.com.comven.corews.transacao.util.Util;
import br.com.comven.corews.transacao.util.exception.PrePagoException;

@Service
public class PrePagoServiceImpl implements PrePagoService {

	private Logger logger = LoggerFactory.getLogger(PrePagoServiceImpl.class);

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Autowired
	CacheManager cacheManager;

	/**
	 * @author lsoares
	 * @since 06/12/2012 Descrição:método responsavel por verificar no sistema de faturamento através de um webservice quanto o cliente ainda possui de crédito para realizar as transações pré-pagas
	 * @parm cpfCnpj: CPF ou CNPJ do cliente pré-pago
	 */
	@Override
	public Integer verificaTransacoesPrePagoCpfCnpj(String cpfCnpj) throws Exception {

		Integer intIdCompromisso = null;
		cpfCnpj = Util.clearNumber(cpfCnpj);

		logger.info("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Iniciada verificação de saldo pré-pago para o cliente {0}", cpfCnpj);

		WSControleIntCOMVEN ws = new WSControleIntCOMVEN();

		try {

			intIdCompromisso = ws.getWSControleIntCOMVENSoap().estoquePrePago(Token.credentialWSprePago, cpfCnpj);

			logger.info("Retorno do WebService estoquePrePago: {0}", intIdCompromisso);

			if (intIdCompromisso == -1) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Credencial inválida: " + cpfCnpj);
				throw new PrePagoException("Credencial inválida: " + cpfCnpj);
			} else if (intIdCompromisso == -2) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - CPF ou CNPJ não existe no BO: " + cpfCnpj);
				throw new PrePagoException("CPF ou CNPJ não existe no BO: " + cpfCnpj);
			} else if (intIdCompromisso == -3) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - CPF ou CNPJ não está cadastrado como pré-pago no BO: " + cpfCnpj);
				throw new PrePagoException("CPF ou CNPJ não está cadastrado como pré-pago no BO: " + cpfCnpj);
			} else if (intIdCompromisso == -4) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - CPF ou CNPJ não possui saldo: " + cpfCnpj);
				return null;
			} else if (intIdCompromisso == -900) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Erro Interno: " + cpfCnpj);
				throw new PrePagoException("Erro retornado pelo serviço pré-pago (900) : " + cpfCnpj);
			} else if (intIdCompromisso > 0) {
				return intIdCompromisso;
			} else {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Erro Desconhecido no serviço de prépago: " + cpfCnpj);
				throw new PrePagoException("Erro desconhecido retornado pelo serviço pré-pago");
			}

		} catch (PrePagoException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Falha geral no serviço verificaTransacoesPrePagoCpfCnpj (message): " + e.getMessage());
			logger.error("Falha geral no serviço verificaTransacoesPrePagoCpfCnpj (causa): " + e.getCause());
		}

		return null;
	}
	
	/**
	 * @author rrodrigues
	 * @since 06/12/2012 Descrição:método responsavel por verificar no sistema de faturamento através de um webservice quanto o cliente ainda possui de crédito para realizar as transações pré-pagas
	 * @parm cpfCnpj: CPF ou CNPJ do cliente pré-pago
	 */
	@Override
	public Integer consultaTransacoesPrePagoCpfCnpj(String cpfCnpj) throws Exception {

		Integer intIdCompromisso = null;
		cpfCnpj = Util.clearNumber(cpfCnpj);

		logger.info("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Iniciada verificação de saldo pré-pago para o cliente {0}", cpfCnpj);

		WSControleIntCOMVEN ws = new WSControleIntCOMVEN();

		try {

			intIdCompromisso = ws.getWSControleIntCOMVENSoap().estoquePrePago(Token.credentialWSprePago, cpfCnpj);

			logger.info("Retorno do WebService estoquePrePago: {0}", intIdCompromisso);

		} catch (Exception e) {
			logger.error("Falha geral no serviço verificaTransacoesPrePagoCpfCnpj (message): " + e.getMessage());
			logger.error("Falha geral no serviço verificaTransacoesPrePagoCpfCnpj (causa): " + e.getCause());
		}

		return intIdCompromisso;
	}

	/**
	 * @author lsoares
	 * @since 06/12/2012 Descrição:método responsavel por verificar, no webservice, o motivo do cpf ou cnpj estar sem saldo
	 * @parm credencial: credencial
	 * @parm cpfCnpj: CPF ou CNPJ do cliente pré-pago
	 */
	@Override
	public String getStatusClienteSemCreditoPrePagoCpfCnpj(String cpfCnpj) throws Exception {

		WSControleIntCOMVEN ws = new WSControleIntCOMVEN();

		String result = "";
		logger.info("[PREPAGO - getStatusClienteSemCreditoPrePagoCpfCnpj] - CNPJ/CPF: " + cpfCnpj);

		try {
			result = ws.getWSControleIntCOMVENSoap().statusClienteSemCreditoPrePago(Token.credentialWSprePago, cpfCnpj);
			logger.info("[PREPAGO - getStatusClienteSemCreditoPrePagoCpfCnpj] - retorno: " + result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[PREPAGO - getStatusClienteSemCreditoPrePagoCpfCnpj] - Erro ao verificar status sem pre prago: " + e.getMessage());
		}

		return result;
	}

	@Override
	public Integer reservarTransacaoPrePagaCpfCnpj(String cpfCnpj) throws Exception {

		cpfCnpj = Util.clearNumber(cpfCnpj);

		try {

			logger.info("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - Iniciada verificação de saldo pré-pago para o cliente {0}", cpfCnpj);

			WSControleIntCOMVEN ws = new WSControleIntCOMVEN();
			Integer intIdCompromisso = ws.getWSControleIntCOMVENSoap().compromissaEstoquePrePago(Token.credentialWSprePago, cpfCnpj);

			logger.info("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - retorno: {0}", intIdCompromisso);

			if (intIdCompromisso == -1) {
				logger.error("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - Credencial inválida: " + cpfCnpj);
				throw new PrePagoException("Credencial inválida: " + cpfCnpj);
			} else if (intIdCompromisso == -2) {
				logger.error("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - CNPJ não existe no BO: " + cpfCnpj);
				throw new PrePagoException("CNPJ não existe no BO: " + cpfCnpj);
			} else if (intIdCompromisso == -3) {
				logger.error("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - CNPJ não está cadastrado como pré-pago no BO: " + cpfCnpj);
				throw new PrePagoException("CNPJ não está cadastrado como pré-pago no BO: " + cpfCnpj);
			} else if (intIdCompromisso == -4) {
				logger.error("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - CNPJ não possui saldo: " + cpfCnpj);
				throw new PrePagoException(getStatusClienteSemCreditoPrePagoCpfCnpj(cpfCnpj));
			} else if (intIdCompromisso == -900) {
				logger.error("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - Erro Interno: " + cpfCnpj);
				throw new PrePagoException("Erro Interno: " + cpfCnpj);
			} else if (intIdCompromisso > 0) {
				logger.info("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - Id {0} compromissado para o cliente {1}", intIdCompromisso, cpfCnpj);
				return intIdCompromisso;
			} else {
				logger.error("[PREPAGO - reservarTransacaoPrePagaCpfCnpj] - Erro Desconhecido: " + cpfCnpj);
				throw new PrePagoException("Erro desconhecido retornado pelo serviço pré-pago");
			}

		} catch (PrePagoException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Falha geral no serviço reservarTransacaoPrePagaCpfCnpj (message): " + e.getMessage());
			logger.error("Falha geral no serviço reservarTransacaoPrePagaCpfCnpj (causa): " + e.getCause());
		}

		return null;
	}

	@Override
	public Boolean confirmarTransacaoPrePaga(Integer idCompromissoPrePago) throws Exception {

		try {

			logger.info("[PREPAGO - confirmarTransacaoPrePaga] - Id do compromisso: " + idCompromissoPrePago);

			WSControleIntCOMVEN ws = new WSControleIntCOMVEN();
			Integer result = ws.getWSControleIntCOMVENSoap().confirmaCompromissoEstoquePrePago(Token.credentialWSprePago, idCompromissoPrePago);

			logger.info("[PREPAGO - confirmarTransacaoPrePaga] - RETORNO: " + result);

			if (result == -1) {
				logger.error("[PREPAGO - confirmarTransacaoPrePaga] - Credencial inválida");
				throw new PrePagoException("Credencial inválida");
			} else if (result == -2) {
				logger.error("[PREPAGO - confirmarTransacaoPrePaga] - Id do compromisso inválido");
				throw new PrePagoException("Id do compromisso inválido");
			} else if (result == -3) {
				logger.error("[PREPAGO - confirmarTransacaoPrePaga] - Id do compromisso já confirmado");
				throw new PrePagoException("Id do compromisso já confirmado");
			} else if (result == -4) {
				logger.error("[PREPAGO - confirmarTransacaoPrePaga] - Id do compromisso expirado");
				throw new PrePagoException("Id do compromisso expirado");
			} else if (result == -900) {
				logger.error("[PREPAGO - confirmarTransacaoPrePaga] - Erro Interno");
				throw new PrePagoException("Erro Interno");
			} else if (result == 0) {
				logger.error("[PREPAGO - confirmarTransacaoPrePaga] - foi dado baixa com sucesso no compromisso {0}", idCompromissoPrePago);
			} else {
				logger.error("Erro Desconhecido");
				throw new PrePagoException("Erro desconhecido retornado pelo serviço pré-pago");
			}

		} catch (PrePagoException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Falha geral no serviço confirmarTransacaoPrePaga (message): " + e.getMessage());
			logger.error("Falha geral no serviço confirmarTransacaoPrePaga (causa): " + e.getCause());
		}

		return Boolean.TRUE;
	}

}
