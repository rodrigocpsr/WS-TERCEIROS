package br.com.comven.corews.transacao.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.autenticis.conector.communicator.ConnectorFactory;
import br.com.autenticis.conector.communicator.IConnector;
import br.com.autenticis.conector.enumerator.EOrigem;
import br.com.comven.corews.transacao.bean.SaidaBean;
import br.com.comven.corews.transacao.domain.CancelamentoVenda;
import br.com.comven.corews.transacao.domain.CancelamentoVendaRetorno01;
import br.com.comven.corews.transacao.domain.CancelamentoVendaRetorno02;
import br.com.comven.corews.transacao.domain.Cliente;
import br.com.comven.corews.transacao.domain.ComunicacaoVenda;
import br.com.comven.corews.transacao.domain.ComunicacaoVendaDetalhe;
import br.com.comven.corews.transacao.domain.ComunicacaoVendaRetorno02;
import br.com.comven.corews.transacao.domain.ComvenErrorCode;
import br.com.comven.corews.transacao.domain.Consulta925;
import br.com.comven.corews.transacao.domain.Consulta925Retorno01;
import br.com.comven.corews.transacao.domain.Consulta925Retorno02;
import br.com.comven.corews.transacao.domain.Endereco;
import br.com.comven.corews.transacao.domain.HorarioBloqueio;
import br.com.comven.corews.transacao.domain.Transacao;
import br.com.comven.corews.transacao.domain.TransacaoBloqueio;
import br.com.comven.corews.transacao.domain.Usuario;
import br.com.comven.corews.transacao.domain.VwTransacoesBloqueadas;
import br.com.comven.corews.transacao.repository.CancelamentoRetorno01Repository;
import br.com.comven.corews.transacao.repository.CancelamentoRetorno02Repository;
import br.com.comven.corews.transacao.repository.CancelamentoVendaRepository;
import br.com.comven.corews.transacao.repository.ClienteRepository;
import br.com.comven.corews.transacao.repository.ComunicacaoVendaDetalheRepository;
import br.com.comven.corews.transacao.repository.ComunicacaoVendaRepository;
import br.com.comven.corews.transacao.repository.ComvenErrorCodeRepository;
import br.com.comven.corews.transacao.repository.Consulta925Repository;
import br.com.comven.corews.transacao.repository.Consulta925Retorno01Repository;
import br.com.comven.corews.transacao.repository.Consulta925Retorno02Repository;
import br.com.comven.corews.transacao.repository.EnderecoRepository;
import br.com.comven.corews.transacao.repository.HorarioBloqueioRepository;
import br.com.comven.corews.transacao.repository.MunicipioCepRepository;
import br.com.comven.corews.transacao.repository.TransacaoBloqueioRepository;
import br.com.comven.corews.transacao.repository.TransacaoRepository;
import br.com.comven.corews.transacao.repository.UsuarioRepository;
import br.com.comven.corews.transacao.repository.VwTransacaoBloqueioRepository;
import br.com.comven.corews.transacao.util.Constantes;
import br.com.comven.corews.transacao.util.Mensagem;
import br.com.comven.corews.transacao.util.Token;
import br.com.comven.corews.transacao.util.TransacaoHelper;
import br.com.comven.corews.transacao.util.Util;
import br.com.comven.corews.transacao.util.enumerator.ELigaDesliga;
import br.com.comven.corews.transacao.util.enumerator.EParametro;
import br.com.comven.corews.transacao.util.enumerator.ESimNao;
import br.com.comven.corews.transacao.util.enumerator.EStatus;
import br.com.comven.corews.transacao.util.enumerator.EStatusItemTransacao;
import br.com.comven.corews.transacao.util.enumerator.ETipoCliente;
import br.com.comven.corews.transacao.util.enumerator.ETipoPagamento;
import br.com.comven.corews.transacao.util.exception.PrePagoException;

@Service
public class TransacaoServiceImpl implements TransacaoService {

	private Logger logger = LoggerFactory.getLogger(TransacaoServiceImpl.class);

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private TransacaoRepository transacaoRepository;
	private ComunicacaoVendaRepository cveRepository;
	private ComunicacaoVendaDetalheRepository cveDetalheRepository;
	private ClienteRepository clienteRepository;
	private EnderecoRepository enderecoRepository;
	private UsuarioRepository usuarioRepository;
	private Consulta925Repository consulta925Repository;
	private CancelamentoVendaRepository cancelamentoRepository;
	private Consulta925Retorno02Repository consulta925Retorno02Repository;
	private Consulta925Retorno01Repository consulta925Retorno01Repository;
	private ComvenErrorCodeRepository comvenErrorCodeRepository;
	private TransacaoBloqueioRepository transacaoBloqueioRepository;
	private CancelamentoRetorno02Repository cancelamentoRetorno02Repository;
	private CancelamentoRetorno01Repository cancelamentoRetorno01Repository;
	private MunicipioCepRepository municipioCepRepository;
	private VwTransacaoBloqueioRepository vwTransacaoBloqueioRepository;
	private HorarioBloqueioRepository horarioBloqueioRepository;

	private PrePagoService prePagoService;

	@Autowired
	CacheManager cacheManager;

	@Autowired
	public TransacaoServiceImpl(TransacaoRepository transacaoRepository, ComunicacaoVendaRepository cveRepository, CancelamentoVendaRepository cancelamentoRepository, ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, UsuarioRepository usuarioRepository, ComunicacaoVendaDetalheRepository cveDetalheRepository, Consulta925Repository consulta925Repository, Consulta925Retorno02Repository consulta925Retorno02Repository,
			Consulta925Retorno01Repository consulta925Retorno01Repository, ComvenErrorCodeRepository comvenErrorCodeRepository, TransacaoBloqueioRepository transacaoBloqueioRepository, CancelamentoRetorno01Repository cancelamentoRetorno01Repository, CancelamentoRetorno02Repository cancelamentoRetorno02Repository, MunicipioCepRepository municipioCepRepository, VwTransacaoBloqueioRepository vwTransacaoBloqueioRepository, HorarioBloqueioRepository horarioBloqueioRepository,
			PrePagoService prePagoService) {

		this.transacaoRepository = transacaoRepository;
		this.cveRepository = cveRepository;
		this.cancelamentoRepository = cancelamentoRepository;
		this.clienteRepository = clienteRepository;
		this.enderecoRepository = enderecoRepository;
		this.usuarioRepository = usuarioRepository;
		this.cveDetalheRepository = cveDetalheRepository;
		this.consulta925Repository = consulta925Repository;
		this.consulta925Retorno02Repository = consulta925Retorno02Repository;
		this.consulta925Retorno01Repository = consulta925Retorno01Repository;
		this.comvenErrorCodeRepository = comvenErrorCodeRepository;
		this.transacaoBloqueioRepository = transacaoBloqueioRepository;
		this.cancelamentoRetorno01Repository = cancelamentoRetorno01Repository;
		this.cancelamentoRetorno02Repository = cancelamentoRetorno02Repository;
		this.municipioCepRepository = municipioCepRepository;
		this.vwTransacaoBloqueioRepository = vwTransacaoBloqueioRepository;
		this.horarioBloqueioRepository = horarioBloqueioRepository;
		this.prePagoService = prePagoService;

	}

	@Override
	public SaidaBean comunicarVendaVeiculo233(String operador, String cnpjResponsavel, ComunicacaoVenda cve) throws Exception {

		logger.debug("METHOD: comunicarVendaVeiculo233");

		String strEnvio = null;
		Cliente clienteResponsavel = null;
		Cliente clienteAutenticacao = null;
		Integer idCompromissoPrePago = null;
		Usuario usuario = null;

		String documentoCompromisso = null;

		try {

			if (verificarHorarioBloqueio())
				throw new ValidationException(Mensagem.HORARIO_BLOQUEIO);

			cve = preencheValoresDefault(cve);

			cve.valida(); // verifica se os dados estao de acordo 
						  // com o padrao para ser enviado na cve

			if (verificarCveBloqueada(cve))
				throw new ValidationException(Mensagem.CVE_BLOQUEADA);

			cve = loadCodigoMunicipio(cve); // busca o codigo do cep do comprador

			// busca os dados do usuario autenticado no spring security
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			// busca os dados do operador (a validação do operador é responsabilidade de
			// quem chama o servico)
			try {
				usuario = usuarioRepository.findUsuarioByLogin(operador).get();
			} catch (NoSuchElementException e) {
				throw new ValidationException(Mensagem.ERROR_OPERADOR_NAO_ENCONTRADO);
			}

			logger.info("PRINCIPAL: " + auth.getPrincipal().toString());

			clienteAutenticacao = clienteRepository.findClienteByCnpj(auth.getPrincipal().toString()).get();

			logger.debug("CLIENTE: " + clienteAutenticacao);

			// verifica se o usuario autenticado esta ativo
			if (clienteAutenticacao == null || !clienteAutenticacao.getStatus().equals(EStatus.ATIVO.getStatus()))
				throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

			// verifica se o cliente possui permissao para executar o servico
			if (clienteAutenticacao.getFgAcessoWs233() == null || !clienteAutenticacao.getFgAcessoWs233().equals(ESimNao.SIM.getCodigo())) {
				throw new ValidationException(Mensagem.PERMISSAO_WS);
			}

			// se o cliente for do tipo parceiro, ele deve ter o cnpj do responsavel pela transacao
			if (clienteAutenticacao.getTipoInstituicao().equals(ETipoCliente.PARCEIRO.getCodigo())) {

				if (cnpjResponsavel == null || cnpjResponsavel.equals(""))
					throw new ValidationException(Mensagem.CLIENTE_PARCEIRO_SEM_RESPONSAVEL);

				clienteResponsavel = clienteRepository.findClienteByCnpj(cnpjResponsavel.trim()).get();

				if (clienteResponsavel == null || !clienteResponsavel.getStatus().equals(EStatus.ATIVO.getStatus()))
					throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

				logger.debug("clienteResponsavel: " + clienteResponsavel);

			} else {
				clienteResponsavel = clienteAutenticacao;
			}

			if (clienteResponsavel.getTipoInstituicao().equals(ETipoCliente.DESPACHANTE.getCodigo()))
				documentoCompromisso = operador;
			else
				documentoCompromisso = clienteResponsavel.getCnpj();

			if (clienteAutenticacao.getTipoPagamento().equals(ETipoPagamento.PREPAGO.getSigla())) {

				logger.debug("CLIENTE PRÉ-PAGO identificado");

				try {

					idCompromissoPrePago = prePagoService.reservarTransacaoPrePagaCpfCnpj(documentoCompromisso);

				} catch (PrePagoException e) {
					throw new ValidationException(e);
				}
			}

			if (clienteResponsavel.getCnpj().equals(cve.getNumIdentComprador()) || clienteResponsavel.getCnpj().equals(cve.getNumIdentProprietario())) {
				cve.setProcuracao("N");
			} else {
				cve.setProcuracao("S");
			}

			cve.setNumeroCnpjIF(clienteResponsavel.getCnpj());

			if (clienteResponsavel.getTipoInstituicao().equals(ETipoCliente.REVENDA.getCodigo()) || clienteResponsavel.getTipoInstituicao().equals(ETipoCliente.CARTORIO.getCodigo()) || clienteResponsavel.getTipoInstituicao().equals(ETipoCliente.LOCADORA.getCodigo())) {
				cve.setOrigemTransacao("LOC");
			} else {
				cve.setOrigemTransacao("LAN");
			}

			// verifica se o CEP iformado para o comprador pertence a uf do comprador.

			// busca os parametros que estao armazenados em cache e que serão utilizados
			// para envio da CVE
			String ambiente = cacheManager.getCache(EParametro.NAME).get(EParametro.AMBIENTE.getCodigo()).get().toString();
			String caracteresPermitidos = cacheManager.getCache(EParametro.NAME).get(EParametro.CARACTERES_PERMITIDOS.getCodigo()).get().toString();
			String tipoConnector = cacheManager.getCache(EParametro.NAME).get(EParametro.CONECTOR.getCodigo()).get().toString();
			String teste = cacheManager.getCache(EParametro.NAME).get(EParametro.TESTE_MODE_SERPRO.getCodigo()).get().toString();
			String codigoRetornoTeste = cacheManager.getCache(EParametro.NAME).get(EParametro.CODIGO_RETORNO_TESTE_MODE.getCodigo()).get().toString();

			logger.debug("AMBIENTE_APLIC: " + ambiente);
			logger.debug("caracteresPermitidos: " + caracteresPermitidos);
			logger.debug("tipoConnector: " + tipoConnector);
			logger.debug("teste: " + teste);
			logger.debug("codigoRetornoTeste: " + codigoRetornoTeste);

			Transacao transacao = new Transacao();
			ComunicacaoVendaDetalhe cveDetalhe = new ComunicacaoVendaDetalhe();

			// gera a chave da transacao: Id da transacao + dia juliano
			Short diaJuliano = Util.geraDiaJuliano();
			Long idTransacao = transacaoRepository.getNextIdTransacao();

			logger.info("idTransacao: " + idTransacao);

			// gera numeros de controle: sequencia diaria e numero da transaçao comven
			// (NumIdentCV/Protocolo CVE)
			Long idSequenciaDiaria = null;
			idSequenciaDiaria = Long.parseLong(idTransacao.toString().substring(idTransacao.toString().length() - 6, idTransacao.toString().length()));

			Long numTransacaoComven = Long.valueOf(TransacaoHelper.getNumeroTransacaoComven(diaJuliano.intValue(), idSequenciaDiaria, "1"));
			cve.setNumIdentCV(numTransacaoComven);

			logger.info("Protocolo CVE: " + numTransacaoComven);

			// gerando string de envio ao DETRAN
			strEnvio = Util.geraStringDetran(idSequenciaDiaria, "233", diaJuliano, geraParteVariavel(cve, "233", null, caracteresPermitidos), caracteresPermitidos);

			logger.info("String enviada ao DENATRAN: " + strEnvio);

			// ignora o primeiro caracter visto que e o ambiente aplic
			String strParteFixa = strEnvio.substring(0, 37);

			// buscando parte variavel da transação
			String strParteVariavel = strEnvio.substring(37, strEnvio.length());

			// grava a transacao gerada no banco de dados antes de enviar para o DENATRAN
			transacao = gravaTransacao(diaJuliano, idSequenciaDiaria, idTransacao, strParteFixa, strParteVariavel, clienteAutenticacao, clienteResponsavel, usuario, cve.getOperacao(), idCompromissoPrePago, cve.getSeloReconhecimento(), null, cve.getTicketVolante());

			// salva os dados da CVE (T233EV01) no banco ligado a transacao
			cve.setId(transacao.getId());
			cve.setDiaJuliano(diaJuliano);
			cve = cveRepository.save(cve);

			// salva os dados do modelo do veiculo caso ele tenha sido informado
			if (cve.getMarca() != null || cve.getModelo() != null || cve.getDetalhe() != null) {
				cveDetalhe = cveDetalheRepository.save(cve.convertDetalhe());
			}

			// +++++++++++++++++++++++++++++++++++++++
			// ENVIANDO DADOS PARA O DENATRAN
			// +++++++++++++++++++++++++++++++++++++++

			String retornoDetran = null;

			try {

				if (teste.equals(ELigaDesliga.LIGADO.getCodigo())) {

					retornoDetran = "1290319233584010000000FNBRFN1008200057" + codigoRetornoTeste + "KQZ90232026618458219058429031920110227095412";
					logger.info("Retorno Detran (MODO TESTE): " + retornoDetran);

				} else {

					IConnector connector = ConnectorFactory.getConnectorFactory(tipoConnector);
					retornoDetran = connector.SendRequestToSistemaCentral(strEnvio, EOrigem.WEBSERVICE.getCodigo(), ambiente);

					if (retornoDetran == null)
						throw new Exception("Não foi possível conectar ao Sistema Central.");

					logger.info("Retorno Detran: " + retornoDetran);
				}

			} catch (Exception e) {

				logger.error("Não foi possivel enviar as informações para o sistema central");

				// como houve erro no envio da informacao para o SERPRO, a transacao sera
				// excluida da base (ROLLBACK)
				cveDetalheRepository.delete(cveDetalhe);
				cveRepository.delete(cve);
				transacaoRepository.delete(transacao);

				throw e;
			}

			// +++++++++++++++++++++++++++++++++++++++++++++
			// TRATANDO OS DADOS RETORNADOS PELO DENATRAN
			// +++++++++++++++++++++++++++++++++++++++++++++

			Boolean blRetorno = null;

			try {

				blRetorno = trataRetornoCVE(clienteResponsavel, usuario, cve, retornoDetran, transacao, strEnvio, operador, cnpjResponsavel);

			} catch (Exception e) {

				if (clienteResponsavel.getTipoInstituicao().equals("CR") && !retornoDetran.substring(37, 40).equals("000")) {

					/**
					 * if (!cve.getProcuracao().equals(ESimNao.SIM.getCodigo())) { operacao = 2; tipoTransacao = 4; }
					 **/
				}

			}

			// removendo ambiente da resposta
			retornoDetran = retornoDetran.substring(1, retornoDetran.length());

			String codRetornoDetran = retornoDetran.substring(37, 40);
			
			SaidaBean saidaBean = new SaidaBean();

			saidaBean.setPlaca(cve.getPlacaUnica());
			saidaBean.setRenavam(cve.getRenavam());
			saidaBean.setDataHoraTransacao(SDF.format(transacao.getDataEnvio()));
			saidaBean.setNumIdentCV(Util.preencheCom(cve.getNumIdentCV().toString(), "0", 12, Util.DIRECAO_ESQUERDA, caracteresPermitidos));
			saidaBean.setCodigoRetorno(codRetornoDetran);

			if (blRetorno) {

				if (clienteAutenticacao.getTipoPagamento().equals(ETipoPagamento.PREPAGO.getSigla())) {
					prePagoService.confirmarTransacaoPrePaga(idCompromissoPrePago);
				}

				saidaBean.setMensagem("Transação efetuada com sucesso");
				saidaBean.setTransacaoEfetuada(true);

			} else {
				saidaBean.setMensagem("Transação não efetuada");
				saidaBean.setTransacaoEfetuada(false);
			}

			return saidaBean;

		} catch (Exception e) {
			logger.error("Falha no processo de transação CVE", e);
			throw e;
		}

	}
	
	@Override
	public SaidaBean consultaPrepago(String operador, String cnpjResponsavel) throws Exception {

		logger.debug("METHOD: consultaPrepago");

		Cliente clienteResponsavel = null;
		Cliente clienteAutenticacao = null;
		
		Integer idCompromissoPrePago = null;
		
		Usuario usuario = null;

		String documentoCompromisso = null;

		try {

			// busca os dados do usuario autenticado no spring security
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			// busca os dados do operador (a validação do operador e responsabilidade de
			// quem chama o servico)
			try {
				usuario = usuarioRepository.findUsuarioByLogin(operador).get();
			} catch (NoSuchElementException e) {
				throw new ValidationException(Mensagem.ERROR_OPERADOR_NAO_ENCONTRADO);
			}

			logger.info("PRINCIPAL: " + auth.getPrincipal().toString());

			clienteAutenticacao = clienteRepository.findClienteByCnpj(auth.getPrincipal().toString()).get();

			logger.debug("CLIENTE: " + clienteAutenticacao);

			// verifica se o usuario autenticado esta ativo
			if (clienteAutenticacao == null || !clienteAutenticacao.getStatus().equals(EStatus.ATIVO.getStatus()))
				throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

			// se o cliente for do tipo parceiro, ele deve ter o cnpj do responsavel pela transacao
			if (clienteAutenticacao.getTipoInstituicao().equals(ETipoCliente.PARCEIRO.getCodigo())) {

				if (cnpjResponsavel == null || cnpjResponsavel.equals(""))
					throw new ValidationException(Mensagem.CLIENTE_PARCEIRO_SEM_RESPONSAVEL);

				clienteResponsavel = clienteRepository.findClienteByCnpj(cnpjResponsavel.trim()).get();

				if (clienteResponsavel == null || !clienteResponsavel.getStatus().equals(EStatus.ATIVO.getStatus()))
					throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

				logger.debug("clienteResponsavel: " + clienteResponsavel);

			} else {
				clienteResponsavel = clienteAutenticacao;
			}

			if (clienteResponsavel.getTipoInstituicao().equals(ETipoCliente.DESPACHANTE.getCodigo()))
				documentoCompromisso = operador;
			else
				documentoCompromisso = clienteResponsavel.getCnpj();

			// se o cliente da autenticacao e do tipo parceiro, verifica se o cliente responsavel e pre pago
			if (clienteAutenticacao.getTipoInstituicao().equals(ETipoCliente.PARCEIRO.getCodigo())) {
				if (clienteResponsavel.getTipoPagamento().equals(ETipoPagamento.PREPAGO.getSigla())) {
					logger.debug("CLIENTE PRÉ-PAGO identificado");

					try {

						idCompromissoPrePago = prePagoService.consultaTransacoesPrePagoCpfCnpj(documentoCompromisso);

					} catch (PrePagoException e) {
						throw new ValidationException(e);
					}
				}
			} else {
				throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO_CONSULTA_PREPAGO);
			}
			
			SaidaBean saidaBean = new SaidaBean();
			
			if (idCompromissoPrePago == -1) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Credencial inválida: " + documentoCompromisso);
				saidaBean.setMensagem("-2 | CPF ou CNPJ não cadastrado");
			} else if (idCompromissoPrePago == -2) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - CPF ou CNPJ não existe no BO: " + documentoCompromisso);
				saidaBean.setMensagem("-2 | CPF ou CNPJ não cadastrado");
			} else if (idCompromissoPrePago == -3) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - CPF ou CNPJ não está cadastrado como pré-pago no BO: " + documentoCompromisso);
				saidaBean.setMensagem("-2 | CPF ou CNPJ não cadastrado");
			} else if (idCompromissoPrePago == -4) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - CPF ou CNPJ não possui saldo: " + documentoCompromisso);
				saidaBean.setMensagem("-2 | CPF ou CNPJ não possui saldo");
			} else if (idCompromissoPrePago == -900) {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Erro interno: " + documentoCompromisso);
				saidaBean.setMensagem("-900 | Houve falha na consulta das trasações. Tente mais tarde");
			} else if (idCompromissoPrePago > 0) {
				saidaBean.setMensagem(idCompromissoPrePago + " | Total de crédito(s)");
			} else {
				logger.error("[PREPAGO - verificaTransacoesPrePagoCpfCnpj] - Erro Desconhecido no serviço de prépago: " + documentoCompromisso);
				throw new PrePagoException("Erro desconhecido retornado pelo serviço pré-pago.");
			}

			return saidaBean;
			
		} catch (PrePagoException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Falha no processo de consulta de prepago", e);
			throw e;
		}

	}

	@Override
	public SaidaBean cancelarVendaVeiculo234(String operador, String cnpjResponsavel, CancelamentoVenda cancelamento) throws Exception {

		logger.debug("METHOD: cancelarVendaVeiculo234");

		String strEnvio = null;
		Cliente clienteResponsavel = null;
		Cliente clienteAutenticacao = null;
		Integer idCompromissoPrePago = null;
		Usuario usuario = null;
		String documentoCompromisso = null;

		try {

			if (verificarHorarioBloqueio())
				throw new ValidationException(Mensagem.HORARIO_BLOQUEIO);

			cancelamento.valida();

			// busca os dados do usuario autenticado no spring security
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			// busca os dados do operador (a validação do operador e responsabilidade de
			// quem chama o servico)
			try {
				usuario = usuarioRepository.findUsuarioByLogin(operador).get();
			} catch (NoSuchElementException e) {
				throw new ValidationException(Mensagem.ERROR_OPERADOR_NAO_ENCONTRADO);
			}

			logger.info("PRINCIPAL: " + auth.getPrincipal().toString());

			clienteAutenticacao = clienteRepository.findClienteByCnpj(auth.getPrincipal().toString()).get();

			logger.debug("CLIENTE: " + clienteAutenticacao);

			// verifica se o usuario autenticado esta ativo
			if (clienteAutenticacao == null || !clienteAutenticacao.getStatus().equals(EStatus.ATIVO.getStatus()))
				throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

			// verifica se o cliente possui permissao para executar o servico
			if (clienteAutenticacao.getFgAcessoWs234() == null || !clienteAutenticacao.getFgAcessoWs234().equals(ESimNao.SIM.getCodigo())) {
				throw new ValidationException(Mensagem.PERMISSAO_WS);
			}

			// se o cliente for do tipo parceiro, ele deve ter o cnpj do responsavel pela transacao
			if (clienteAutenticacao.getTipoInstituicao().equals(ETipoCliente.PARCEIRO.getCodigo())) {

				if (cnpjResponsavel == null || cnpjResponsavel.equals(""))
					throw new ValidationException(Mensagem.CLIENTE_PARCEIRO_SEM_RESPONSAVEL);

				clienteResponsavel = clienteRepository.findClienteByCnpj(cnpjResponsavel.trim()).get();

				if (clienteResponsavel == null || !clienteResponsavel.getStatus().equals(EStatus.ATIVO.getStatus()))
					throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

				logger.debug("clienteResponsavel: " + clienteResponsavel);

			} else {
				clienteResponsavel = clienteAutenticacao;
			}

			if (clienteResponsavel.getTipoInstituicao().equals(ETipoCliente.DESPACHANTE.getCodigo()))
				documentoCompromisso = operador;
			else
				documentoCompromisso = clienteResponsavel.getCnpj();

			if (clienteAutenticacao.getTipoPagamento().equals(ETipoPagamento.PREPAGO.getSigla())) {

				logger.debug("CLIENTE PRÉ-PAGO identificado");

				try {

					idCompromissoPrePago = prePagoService.reservarTransacaoPrePagaCpfCnpj(documentoCompromisso);

				} catch (PrePagoException e) {
					throw new ValidationException(e);
				}
			}

			// busca os parametros que estao armazenados em cache e que serao utilizados
			// para envio da CVE
			String AMBIENTE_APLIC = cacheManager.getCache(EParametro.NAME).get(EParametro.AMBIENTE.getCodigo()).get().toString();
			String caracteresPermitidos = cacheManager.getCache(EParametro.NAME).get(EParametro.CARACTERES_PERMITIDOS.getCodigo()).get().toString();
			String tipoConnector = cacheManager.getCache(EParametro.NAME).get(EParametro.CONECTOR.getCodigo()).get().toString();
			String teste = cacheManager.getCache(EParametro.NAME).get(EParametro.TESTE_MODE_SERPRO.getCodigo()).get().toString();
			String codigoRetornoTeste = cacheManager.getCache(EParametro.NAME).get(EParametro.CODIGO_RETORNO_TESTE_MODE.getCodigo()).get().toString();

			logger.debug("AMBIENTE_APLIC: " + AMBIENTE_APLIC);
			logger.debug("caracteresPermitidos: " + caracteresPermitidos);
			logger.debug("tipoConnector: " + tipoConnector);
			logger.debug("teste: " + teste);
			logger.debug("codigoRetornoTeste: " + codigoRetornoTeste);

			Transacao transacao = new Transacao();

			// gera a chave da transacao: id da transação + dia juliano
			Short diaJuliano = Util.geraDiaJuliano();
			Long idTransacao = transacaoRepository.getNextIdTransacao();

			logger.info("idTransacao: " + idTransacao);

			// gera numeros de controle: sequencia diaria e numero da transaçao comven
			// (NumIdentCV/Protocolo CVE)
			Long idSequenciaDiaria = null;
			idSequenciaDiaria = Long.parseLong(idTransacao.toString().substring(idTransacao.toString().length() - 6, idTransacao.toString().length()));

			Long codigoSegurancaCancelamento = null;

			/*
			 * if (cancelamento.getCodigoSegurancaCRV() != null && !cancelamento.getCodigoSegurancaCRV().equals("")) codigoSegurancaCancelamento = new Long(cancelamento.getCodigoSegurancaCRV());
			 */

			logger.info("Protocolo CVE: " + cancelamento.getNumIdentCV());

			Optional<ComunicacaoVenda> cve = cveRepository.getComunicacaoVendaByNumIdentCV(cancelamento.getNumIdentCV());

			if (!cve.isPresent())
				throw new ValidationException("não foi encontrada comunicação de venda para o protocolo informado: " + cancelamento.getNumIdentCV());

			if (Long.parseLong(cve.get().getRenavam()) != Long.parseLong(cancelamento.getRenavam()))
				throw new ValidationException("renavam informado não pertence ao protocolo informado: " + cancelamento.getRenavam());

			Transacao transacaoCVE = transacaoRepository.getOne(cve.get().getId());

			if (transacaoCVE.getStatus().equals(EStatusItemTransacao.CANCELADO.getStatus()))
				throw new ValidationException("Transação já encontra-se cancelada.");

			cve.get().setTransacao(transacaoCVE);

			Cliente clienteCVE = clienteRepository.getOne(transacaoCVE.getIdCLiente());

			if (clienteCVE.getIdCliente() != clienteResponsavel.getIdCliente())
				throw new ValidationException("Não é possivel cancelar a venda pois o cliente do cancelamento é diferente do cliente da comunicação de venda.");

			// gerando string de envio ao DETRAN
			strEnvio = Util.geraStringDetran(idSequenciaDiaria, "234", diaJuliano, geraParteVariavel(cve.get(), "234", codigoSegurancaCancelamento, caracteresPermitidos), caracteresPermitidos);

			logger.info("String enviada ao DENATRAN: " + strEnvio);

			// Ignora o primeiro caracter visto que e o ambiente aplic.
			String strParteFixa = strEnvio.substring(0, 37);

			// buscando parte variavel da transacao
			String strParteVariavel = strEnvio.substring(37, strEnvio.length());

			// grava a transaçao gerada no banco de dados antes de enviar para o DENATRAN.
			transacao = gravaTransacao(diaJuliano, idSequenciaDiaria, idTransacao, strParteFixa, strParteVariavel, clienteAutenticacao, clienteResponsavel, usuario, null, idCompromissoPrePago, null, null, null);

			logger.info("ID Transação 234: " + transacao.getId());

			// gravando log de criaçao da transacao
			// logBusiness.gravaLog(transacao, StatusTransacao.CRIADA, null);

			cancelamento.setMotivoCancelamento(1);
			cancelamento.setCodigoSegurancaCRV(codigoSegurancaCancelamento);
			cancelamento = cancelamento.cveToCancelamento(cve.get());

			// salva os dados da CVE (T234EV01) no banco ligado a transacao
			cancelamento.setId(transacao.getId());
			cancelamento.setDiaJuliano(diaJuliano);
			cancelamento = cancelamentoRepository.save(cancelamento);

			cancelamento.setTransacao(transacao);

			// +++++++++++++++++++++++++++++++++++++++
			// ENVIANDO DADOS PARA O DENATRAN
			// +++++++++++++++++++++++++++++++++++++++

			String retornoDetran = null;

			try {

				if (teste.equals(ELigaDesliga.LIGADO.getCodigo())) {

					retornoDetran = "0002968233584010000000FNBRFN1008200314" + codigoRetornoTeste + "TST99998512317273912345678901212012010118000020120410094500";
					logger.info("Retorno Detran (MODO TESTE): " + retornoDetran);

				} else {

					IConnector connector = ConnectorFactory.getConnectorFactory(tipoConnector);
					retornoDetran = connector.SendRequestToSistemaCentral(strEnvio, EOrigem.WEBSERVICE.getCodigo(), AMBIENTE_APLIC);

					if (retornoDetran == null)
						throw new Exception("Não foi possível conectar ao Sistema Central.");

					logger.info("Retorno Detran: " + retornoDetran);
				}

			} catch (Exception e) {

				logger.error("Não foi possivel enviar as informações para o sistema central");

				// como houve erro no envio da informação para o SERPRO, a transação será
				// excluida da base (ROLLBACK)
				cancelamentoRepository.delete(cancelamento);
				transacaoRepository.delete(transacao);

				throw e;
			}

			// +++++++++++++++++++++++++++++++++++++++++++++
			// TRATANDO OS DADOS RETORNADOS PELO DENATRAN
			// +++++++++++++++++++++++++++++++++++++++++++++

			Boolean blRetorno = null;

			try {

				if (cancelamento.getBloquearCVE() != null && cancelamento.getBloquearCVE().equals(ESimNao.SIM.getCodigo()))
					cve.get().setFgBloqueio("S");
				else
					cve.get().setFgBloqueio("N");

				cveRepository.save(cve.get());

				// tratando retorno do DETRAN
				blRetorno = trataRetornoCancelamento(cancelamento, cve.get(), retornoDetran, strEnvio, usuario, cnpjResponsavel);

			} catch (Exception e) {

				if (clienteResponsavel.getTipoInstituicao().equals("CR") && !retornoDetran.substring(37, 40).equals("000")) {

					/*
					 * if (!cve.get().getProcuracao().equals(ESimNao.SIM.getCodigo())) { operacao = 2; tipoTransacao = 4; }
					 */
				}

			}

			// removendo ambiente da resposta
			retornoDetran = retornoDetran.substring(1, retornoDetran.length());

			String codRetornoDetran = retornoDetran.substring(37, 40);
			
			SaidaBean saidaBean = new SaidaBean();

			saidaBean.setPlaca(cve.get().getPlacaUnica());
			saidaBean.setRenavam(cve.get().getRenavam());
			saidaBean.setDataHoraTransacao(SDF.format(transacao.getDataEnvio()));
			saidaBean.setNumIdentCV(Util.preencheCom(cve.get().getNumIdentCV().toString(), "0", 12, Util.DIRECAO_ESQUERDA, caracteresPermitidos));
			saidaBean.setCodigoRetorno(codRetornoDetran);
			
			if (blRetorno) {

				if (clienteAutenticacao.getTipoPagamento().equals(ETipoPagamento.PREPAGO.getSigla())) {
					prePagoService.confirmarTransacaoPrePaga(idCompromissoPrePago);
				}

				saidaBean.setMensagem("Cancelamento efetuado com sucesso");
				saidaBean.setTransacaoEfetuada(true);

			} else {
				saidaBean.setMensagem("Cancelamento não efetuado");
				saidaBean.setTransacaoEfetuada(false);
			}

			return saidaBean;

		} catch (Exception e) {
			logger.error("Falha no processo de transação CVE", e);
			throw e;
		}

	}

	@Override
	public SaidaBean consulta925(String operador, String cnpjResponsavel, Consulta925 consulta925) throws Exception {

		logger.debug("METHOD: consulta925");

		String strEnvio = null;
		Cliente clienteResponsavel = null;
		Cliente clienteAutenticacao = null;

		try {

			// busca os dados do usuario autenticado no spring security
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			// busca os dados do operador (a validação do operador é responsabilidade de
			// quem chama o servico)
			Usuario usuario = usuarioRepository.findUsuarioByLogin(operador).get();

			logger.info("PRINCIPAL: " + auth.getPrincipal().toString());

			clienteAutenticacao = clienteRepository.findClienteByCnpj(auth.getPrincipal().toString()).get();

			logger.debug("CLIENTE: " + clienteAutenticacao);

			// verifica se o usuario autenticado esta ativo
			if (clienteAutenticacao == null || !clienteAutenticacao.getStatus().equals(EStatus.ATIVO.getStatus()))
				throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

			// verifica se o cliente possui permissao para executar o servico
			if (clienteAutenticacao.getFgAcessoWs925() == null || !clienteAutenticacao.getFgAcessoWs925().equals(ESimNao.SIM.getCodigo())) {
				throw new ValidationException(Mensagem.PERMISSAO_WS);
			}

			// se o cliente for do tipo parceiro, ele deve ter o cnpj do responsavel pela transacao
			if (clienteAutenticacao.getTipoInstituicao().equals(ETipoCliente.PARCEIRO.getCodigo())) {

				clienteResponsavel = clienteRepository.findClienteByCnpj(cnpjResponsavel.trim()).get();

				if (clienteResponsavel == null || !clienteResponsavel.getStatus().equals(EStatus.ATIVO.getStatus()))
					throw new ValidationException(Mensagem.CLIENTE_NAO_PERMITIDO);

				logger.debug("clienteResponsavel: " + clienteResponsavel);

			} else {
				clienteResponsavel = clienteAutenticacao;
			}

			// busca os parametros que estao armazenados em cache e que serao utilizados
			// para envio da CVE
			String AMBIENTE_APLIC = cacheManager.getCache(EParametro.NAME).get(EParametro.AMBIENTE.getCodigo()).get().toString();
			String caracteresPermitidos = cacheManager.getCache(EParametro.NAME).get(EParametro.CARACTERES_PERMITIDOS.getCodigo()).get().toString();
			String tipoConnector = cacheManager.getCache(EParametro.NAME).get(EParametro.CONECTOR.getCodigo()).get().toString();
			String teste = cacheManager.getCache(EParametro.NAME).get(EParametro.TESTE_MODE_SERPRO.getCodigo()).get().toString();
			String codigoRetornoTeste = cacheManager.getCache(EParametro.NAME).get(EParametro.CODIGO_RETORNO_TESTE_MODE.getCodigo()).get().toString();

			logger.debug("AMBIENTE_APLIC: " + AMBIENTE_APLIC);
			logger.debug("caracteresPermitidos: " + caracteresPermitidos);
			logger.debug("tipoConnector: " + tipoConnector);
			logger.debug("teste: " + teste);
			logger.debug("codigoRetornoTeste: " + codigoRetornoTeste);

			Transacao transacao = new Transacao();

			// Gera a chave da transação: Id da transaçao + dia juliano
			Short diaJuliano = Util.geraDiaJuliano();
			Long idTransacao = transacaoRepository.getNextIdTransacao();

			logger.info("idTransacao: " + idTransacao);

			// gera numeros de controle: sequencia diaria e numero da transaçao comven
			// (NumIdentCV/Protocolo CVE)
			Long idSequenciaDiaria = null;
			idSequenciaDiaria = Long.parseLong(idTransacao.toString().substring(idTransacao.toString().length() - 6, idTransacao.toString().length()));

			Long numTransacaoComven = Long.valueOf(TransacaoHelper.getNumeroTransacaoComven(diaJuliano.intValue(), idSequenciaDiaria, "1"));

			logger.info("Protocolo CVE: " + numTransacaoComven);

			Optional<ComunicacaoVenda> cve = cveRepository.getComunicacaoVendaByNumIdentCV(consulta925.getNumIdentCV());

			if (!cve.isPresent())
				throw new ValidationException("não foi encontrada comunicação de venda para o protocolo informado: " + consulta925.getNumIdentCV());

			Transacao transacaoCVE = transacaoRepository.getOne(cve.get().getId());
			cve.get().setTransacao(transacaoCVE);

			/*
			 * Optional<CancelamentoVenda> cancelamento = cancelamentoRepository.getCancelamentoVendaByNumIdentCV(consulta925. getNumIdentCV());
			 * 
			 * if (cancelamento.isPresent()) { Transacao transacaoCancelamento = transacaoRepository.getOne(cancelamento.get().getId()); cancelamento.get().setTransacao(transacaoCancelamento); }
			 */
			// gerando string de envio ao DENATRAN
			strEnvio = Util.geraStringDetran(idSequenciaDiaria, "925", diaJuliano, geraParteVariavel(cve.get(), "925", null, caracteresPermitidos), caracteresPermitidos);

			logger.info("String enviada ao DENATRAN: " + strEnvio);

			// Ignora o primeiro caracter visto que e o ambiente aplic.
			String strParteFixa = strEnvio.substring(0, 37);

			// buscando parte variavel da transaçao
			String strParteVariavel = strEnvio.substring(37, strEnvio.length());

			// grava a transacao gerada no banco de dados antes de enviar para o DENATRAN.
			transacao = gravaTransacao(diaJuliano, idSequenciaDiaria, idTransacao, strParteFixa, strParteVariavel, clienteAutenticacao, clienteResponsavel, usuario, null, null, null, null, null);

			// salva os dados da CVE (T233EV01) no banco ligado a transacao
			consulta925.setId(transacao.getId());
			consulta925.setDiaJuliano(diaJuliano);

			consulta925.setNumIdentCartorio(cve.get().getNumIdentCartorio());
			consulta925.setPlacaUnica(cve.get().getPlacaUnica());
			consulta925.setRenavam(cve.get().getRenavam());
			consulta925.setTipoDocCartorio(cve.get().getTipoDocCartorio());

			consulta925 = consulta925Repository.save(consulta925);

			consulta925.setTransacao(transacao);

			// +++++++++++++++++++++++++++++++++++++++
			// ENVIANDO DADOS PARA O DENATRAN
			// +++++++++++++++++++++++++++++++++++++++

			String retornoDetran = null;

			try {

				if (teste.equals(ELigaDesliga.LIGADO.getCodigo())) {

					retornoDetran = "1301596925584010000000FNBRFN1052900107000HET43220023238066080789935600   ANA APARECIDA MENDES                    20102010MG0081401920562010092201104984839678   JEDER DA CONCEICAO BARROS DUARTE        RUA ITA                          68                    BOA VISTA           5077MG34400000460120121122206293184000101FEBRANOR621                             SRTVS QD701 LT 05 BL A SL 601   604                    ASA SUL             9701DF70340000610332315552932777651112012112217462900000000FN12013041516541600000000220130415165416";
					logger.info("Retorno Detran (MODO TESTE): " + retornoDetran);

				} else {

					IConnector connector = ConnectorFactory.getConnectorFactory(tipoConnector);
					retornoDetran = connector.SendRequestToSistemaCentral(strEnvio, EOrigem.WEBSERVICE.getCodigo(), AMBIENTE_APLIC);

					if (retornoDetran == null)
						throw new Exception("Não foi possível conectar ao Sistema Central.");

					logger.info("Retorno Detran: " + retornoDetran);
				}

			} catch (Exception e) {

				logger.error("Não foi possivel enviar as informações para o sistema central");

				// como houve erro no envio da informação para o SERPRO, a transacao sera
				// excluida da base (ROLLBACK)
				consulta925Repository.delete(consulta925);
				transacaoRepository.delete(transacao);

				throw e;
			}

			// +++++++++++++++++++++++++++++++++++++++++++++
			// TRATANDO OS DADOS RETORNADOS PELO DENATRAN
			// +++++++++++++++++++++++++++++++++++++++++++++

			Consulta925Retorno02 retorno = trataRetornoConsultaOnline(consulta925, cve.get(), retornoDetran);

			String codRetornoDetran = retornoDetran.substring(37, 40);
			
			SaidaBean saidaBean = new SaidaBean();

			saidaBean.setPlaca(cve.get().getPlacaUnica());
			saidaBean.setRenavam(cve.get().getRenavam());
			saidaBean.setDataHoraTransacao(SDF.format(transacao.getDataEnvio()));
			saidaBean.setNumIdentCV(Util.preencheCom(cve.get().getNumIdentCV().toString(), "0", 12, Util.DIRECAO_ESQUERDA, caracteresPermitidos));
			saidaBean.setResultado(retorno);
			
			saidaBean.setCodigoRetorno(codRetornoDetran);

			if (retorno != null) {
				saidaBean.setMensagem("Transação efetuada com sucesso");
				saidaBean.setTransacaoEfetuada(true);

			} else {
				saidaBean.setMensagem("Transação não efetuada");
				saidaBean.setTransacaoEfetuada(false);
			}

			return saidaBean;

		} catch (Exception e) {
			logger.error("Falha no processo de transação CVE", e);
			throw e;
		}

	}

	public Boolean trataRetornoCVE(Cliente cliente, Usuario usuario, ComunicacaoVenda cve, String retornoDetran, Transacao transacaoNova, String envioDetran, String operador, String cnpjResponsavel) throws Exception {

		Boolean retorno = Boolean.FALSE;

		// tratando retorno do DETRAN
		if (retornoDetran != null)
			retornoDetran = retornoDetran.substring(1, retornoDetran.length());
		else
			throw new Exception(Token.ERRO_MSG_GENERICO);

		String retornoErro = retornoDetran.substring(32, 34);

		if (!retornoDetran.substring(6, 9).equals("233")) {
			// logBusiness.gravaLog(cve.getTransacaoNova(), StatusTransacao.ERRO, "Retorno
			// desconhecido da comunicação de venda.");
			throw new Exception(Token.ERRO_MSG_RETORNO_DESCONHECIDO_SERPRO);
		}

		String codRetornoDetran = retornoDetran.substring(37, 40);

		// condição inclusa com a 925 automatica.
		if (codRetornoDetran.trim().equals(Constantes.LIXO_RETORNO_SERPRO)) {

			String status925automatica = cacheManager.getCache(EParametro.NAME).get(EParametro.T925_AUTOMATICA.getCodigo()).toString();

			// verificar se a 925 esta ligada.
			if (status925automatica.equals(ELigaDesliga.LIGADO.getCodigo())) {

				try {

					Consulta925 consulta925 = new Consulta925();
					consulta925.setNumIdentCV(cve.getNumIdentCV());
					SaidaBean saidaBean = consulta925(operador, cnpjResponsavel, consulta925);

					// Caso consulta 925 retorne uma 233 realizada com sucesso atualiza a transação.
					transacaoNova.setDataTransacao(Util.converteIntegersData(saidaBean.getResultado().getDataRegistro(), saidaBean.getResultado().getHoraRegistro()));

					transacaoNova.setStatus(EStatusItemTransacao.PROCESSADO.getStatus());
					transacaoNova.setRetorno("000");
					transacaoNova.setRetornoSERPRO(retornoDetran);

					transacaoRepository.save(transacaoNova);

					// logBusiness.enviarEmailLixoSerpro(true, envioDetran, retornoDetran,
					// t925Ev01.getTransacaoNova().getParteFixa() +
					// t925Ev01.getTransacaoNova().getParteVariavel(),
					// t925Ev01.getTransacaoNova().getReturnSerpro(), true);

					retorno = Boolean.TRUE;

				} catch (Exception e) {

					transacaoNova.setStatus(EStatusItemTransacao.PROCESSADO_COM_ERRO.getStatus());
					transacaoNova.setRetorno("LIX");
					transacaoNova.setRetornoSERPRO(retornoDetran);

					transacaoRepository.save(transacaoNova);

				}

			} else {
				// eEnvio de email caso 925 automatica esteja desligada.
				// logBusiness.enviarEmailLixoSerpro(true, envioDetran, retornoDetran, null,
				// null, false);
			}

		} else if (!retornoErro.equals("00")) {
			// logBusiness.gravaLog(cve.getTransacaoNova(), StatusTransacao.ERRO, "Falha na
			// comunicação com o SERPRO: " + retornoErro);
			throw new Exception(Token.ERRO_MSG_GENERICO_SERPRO_OFFLINE);

			// se o serviço do DETRAN retornar sucesso (0)
		} else if (codRetornoDetran.equals("000")) {

			ComunicacaoVendaRetorno02 contextBeanRT02 = new ComunicacaoVendaRetorno02(retornoDetran);

			transacaoNova.setDataTransacao(Util.converteIntegersData(contextBeanRT02.getDataRegistro(), contextBeanRT02.getHoraRegistro()));
			transacaoNova.setDataEnvio(new Date());
			transacaoNova.setStatus(EStatusItemTransacao.PROCESSADO.getStatus());
			transacaoNova.setRetorno("000");
			transacaoNova.setRetornoSERPRO(retornoDetran);

			transacaoRepository.save(transacaoNova);

			try {

				if (cliente.getTipoPagamento().equals(ETipoPagamento.PREPAGO.getSigla())) {
					// confirmarTransacaoPrePaga(cve.getIdCompromisso());
					// seamSession.setTotalTransacoes(seamSession.getTotalTransacoes() - 1);
				}
			} catch (Exception e) {
				// log.warn(getLogUserIdentity() + "Falha ao invocar o servico de prepago: " +
				// e.getMessage());
			}

			retorno = Boolean.TRUE;

		} else {

			transacaoNova.setStatus(EStatusItemTransacao.PROCESSADO_COM_ERRO.getStatus());
			transacaoNova.setRetorno(codRetornoDetran);
			transacaoNova.setRetornoSERPRO(retornoDetran);
			transacaoRepository.save(transacaoNova);

			// gravando log da transa��o
			// logBusiness.gravaLog(cve.getTransacaoNova(), StatusTransacao.ERRO, "Erro
			// retornado pelo APLIC: " + codRetornoDetran);

			/*
			 * ##################### MELHORIAS COMVEN 7 ##################### Deve ser verificado se o retorno do DETRAN possui um codigo de bloqueio cadastrado com o limite de execuções para que seja registrado +1 no contador de bloqueio de transação
			 */
			TransacaoBloqueio transacaoNovaBloqueio = new TransacaoBloqueio(transacaoNova.getId(), Integer.parseInt(codRetornoDetran), cve.getPlacaUnica(), cve.getRenavam(), cve.getNumIdentProprietario(), usuario.getLogin());

			Optional<ComvenErrorCode> comvenErrorCode = comvenErrorCodeRepository.findComvenErrorCodeByCodigoErro(Integer.parseInt(codRetornoDetran));

			if (comvenErrorCode.isPresent()) {

				// o codigo de erro retornado existe na lista de bloqueio
				transacaoNovaBloqueio.setDataBloqueio(new Date());
				transacaoBloqueioRepository.save(transacaoNovaBloqueio);

			}

			// logBusiness.gravarLog(ETipoAcao.INCLUSAO.getTipo(), transacaoNovaBloqueio,
			// null);

			// log.warn(getLogUserIdentity() + ">> Transação " +
			// transacaoNovaBloqueio.getIdTransacao() + " (CVE) registrada no contador de
			// bloqueio do erro " + codRetornoDetran);

			// throw new Exception(Token.ERRO_MSG_RETORNO_DESCONHECIDO_SERPRO);

			// throw new BusinessException(ReturnMessage233.getMessage(new
			// Integer(codRetornoDetran)));
		}

		return retorno;
	}

	public Boolean trataRetornoCancelamento(CancelamentoVenda tCancelCve, ComunicacaoVenda cve, String retornoDetran, String envioDetran, Usuario usuario, String cnpjResponsavel) throws Exception {

		Boolean retorno =  Boolean.FALSE;

		// tratando retorno do DETRAN
		if (retornoDetran != null)
			retornoDetran = retornoDetran.substring(1, retornoDetran.length());
		else
			throw new Exception(Token.ERRO_MSG_GENERICO);

		// Condição inclusa com a 925 automatica
		if (retornoDetran.substring(37, 40).trim().equalsIgnoreCase(Constantes.LIXO_RETORNO_SERPRO)) {

			String transacao925Automatica = cacheManager.getCache(EParametro.NAME).get(EParametro.T925_AUTOMATICA.getCodigo()).get().toString();

			// verificar se a 925 está ligada.
			if (transacao925Automatica.equals("1")) {

				// se o serviço retornar lixo do SERPRO execulta a 925 automatica

				try {

					Consulta925 consulta925 = new Consulta925();
					consulta925.setNumIdentCV(tCancelCve.getNumIdentCV());
					SaidaBean saidaBean = consulta925(usuario.getLogin(), cnpjResponsavel, consulta925);

					if (saidaBean.getResultado().getDataCancelamento() != null && !saidaBean.getResultado().getDataCancelamento().equals("")) {

						tCancelCve.getTransacao().setDataTransacao(Util.converteIntegersData(saidaBean.getResultado().getDataRegistro(), saidaBean.getResultado().getHoraRegistro()));
						tCancelCve.getTransacao().setStatus(EStatusItemTransacao.PROCESSADO.getStatus());
						tCancelCve.getTransacao().setRetorno("000");
						tCancelCve.getTransacao().setRetornoSERPRO(retornoDetran);

						transacaoRepository.save(tCancelCve.getTransacao());

						cve.getTransacao().setStatus(EStatusItemTransacao.CANCELADO.getStatus());
						transacaoRepository.save(cve.getTransacao());

					} else {

						tCancelCve.getTransacao().setStatus(EStatusItemTransacao.PROCESSADO_COM_ERRO.getStatus());
						tCancelCve.getTransacao().setRetorno("LIX");
						tCancelCve.getTransacao().setRetornoSERPRO(retornoDetran);

						transacaoRepository.save(tCancelCve.getTransacao());

					}

				} catch (Exception e) {

					tCancelCve.getTransacao().setStatus(EStatusItemTransacao.PROCESSADO_COM_ERRO.getStatus());
					tCancelCve.getTransacao().setRetorno("LIX");
					tCancelCve.getTransacao().setRetornoSERPRO(retornoDetran);

					transacaoRepository.save(tCancelCve.getTransacao());

					throw new Exception("Retorno desconhecido do DENATRAN");
				}

			}

		} else if (!retornoDetran.substring(32, 34).equals("00")) {
			throw new Exception(Token.ERRO_MSG_GENERICO_SERPRO_OFFLINE);
		} else if (retornoDetran.substring(37, 40).equals("000")) {

			CancelamentoVendaRetorno02 retornoSucesso = new CancelamentoVendaRetorno02(retornoDetran);
			retornoSucesso.setId(tCancelCve.getId());
			retornoSucesso.setDiaJuliano(tCancelCve.getDiaJuliano());

			tCancelCve.getTransacao().setDataTransacao(Util.converteIntegersData(retornoSucesso.getDataCancelamento(), retornoSucesso.getHoraCancelamento()));
			tCancelCve.getTransacao().setDataEnvio(new Date());
			tCancelCve.getTransacao().setRetorno("000");
			tCancelCve.getTransacao().setRetornoSERPRO(retornoDetran);
			tCancelCve.getTransacao().setStatus(EStatusItemTransacao.PROCESSADO.getStatus());

			// inserindo retorno de sucesso
			cancelamentoRetorno02Repository.save(retornoSucesso);
			transacaoRepository.save(tCancelCve.getTransacao());

			cve.getTransacao().setStatus(EStatusItemTransacao.CANCELADO.getStatus());
			transacaoRepository.save(cve.getTransacao());

			retorno = Boolean.TRUE;

		} else {

			CancelamentoVendaRetorno01 retornoErro = new CancelamentoVendaRetorno01();
			retornoErro.setCodigoRetorno(Integer.parseInt(retornoDetran.substring(37, 40)));
			retornoErro.setId(tCancelCve.getId());
			retornoErro.setDiaJuliano(tCancelCve.getDiaJuliano());

			tCancelCve.getTransacao().setStatus(EStatusItemTransacao.PROCESSADO_COM_ERRO.getStatus());
			tCancelCve.getTransacao().setRetorno(retornoDetran.substring(37, 40));
			tCancelCve.getTransacao().setRetornoSERPRO(retornoDetran);

			cancelamentoRetorno01Repository.save(retornoErro);
			transacaoRepository.save(tCancelCve.getTransacao());

			TransacaoBloqueio transacaoBloqueio = new TransacaoBloqueio(cve.getTransacao().getId(), Integer.parseInt(retornoDetran.substring(37, 40)), cve.getPlacaUnica(), cve.getRenavam(), cve.getNumIdentProprietario(), usuario.getLogin());

			transacaoBloqueio.setDataBloqueio(new Date());

			transacaoBloqueioRepository.save(transacaoBloqueio);

			throw new Exception("Erro retornado pelo DENATRAN: " + retornoErro.getCodigoRetorno());
		}

		return retorno;
	}

	public Consulta925Retorno02 trataRetornoConsultaOnline(Consulta925 tConsulta, ComunicacaoVenda cve, String retornoDetran) throws Exception {

		// tratando retorno do DETRAN
		if (retornoDetran != null)
			retornoDetran = retornoDetran.substring(1, retornoDetran.length());
		else
			throw new Exception(Token.ERRO_MSG_GENERICO);

		if (retornoDetran.substring(32, 34).equals("26"))
			throw new Exception(Token.ERRO_MSG_GENERICO_SERPRO_OFFLINE);

		// se o servico do DETRAN retornar sucesso (0)
		if (retornoDetran.substring(37, 40).equals("000")) {

			Consulta925Retorno02 retornoSucesso = new Consulta925Retorno02(retornoDetran);
			retornoSucesso.setId(tConsulta.getId());
			retornoSucesso.setDiaJuliano(tConsulta.getDiaJuliano());

			tConsulta.getTransacao().setDataEnvio(new Date());
			tConsulta.getTransacao().setRetornoSERPRO(retornoDetran);
			tConsulta.getTransacao().setRetorno(retornoDetran.substring(37, 40));
			retornoSucesso.setTransacao(tConsulta.getTransacao());

			consulta925Retorno02Repository.save(retornoSucesso);

			tConsulta.getTransacao().setStatus(EStatusItemTransacao.PROCESSADO.getStatus());
			transacaoRepository.save(tConsulta.getTransacao());

			return retornoSucesso;

		} else {

			Consulta925Retorno01 retornoErro = new Consulta925Retorno01(retornoDetran);
			retornoErro.setId(tConsulta.getId());
			retornoErro.setDiaJuliano(tConsulta.getDiaJuliano());

			retornoErro.setId(tConsulta.getId());
			retornoErro.setTransacao(tConsulta.getTransacao());
			consulta925Retorno01Repository.save(retornoErro);

			tConsulta.getTransacao().setStatus(EStatusItemTransacao.PROCESSADO_COM_ERRO.getStatus());
			tConsulta.getTransacao().setRetornoSERPRO(retornoDetran);
			tConsulta.getTransacao().setRetorno(retornoDetran.substring(37, 40));
			transacaoRepository.save(tConsulta.getTransacao());

			throw new Exception("Erro retornado pelo DENATRAN: " + retornoErro.getCodigoRetorno());

		}

	}

	public Transacao gravaTransacao(Short diaJuliano, Long idSequenciaDiaria, Long idTransacao, String parteFixa, String parteVariavel, Cliente clienteAutenticacao, Cliente clienteResponsavel, Usuario usuario, String tipoOperacao, Integer idCompromisso, String seloReconhecimento, String demonstracao, Long ticketVolante) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		Endereco endereco = enderecoRepository.findById(clienteResponsavel.getIdEndereco()).get();

		// UsuarioCliente usuarioCliente =
		// usuarioBusiness.get(seamSession.getIdUsuario(), cliente.getIdCliente());

		Transacao transacao = new Transacao();

		transacao.setId(idTransacao);
		transacao.setDiaJuliano(diaJuliano);
		transacao.setParteFixa(parteFixa);
		transacao.setParteVariavel(parteVariavel);
		transacao.setUfTransacao(endereco.getUf());
		transacao.setIdSeqDiaria(idSequenciaDiaria);
		transacao.setDataTransacao(sdf.parse(sdf.format(new Date())));
		transacao.setDataInclusao(sdf.parse(sdf.format(new Date())));
		transacao.setIdCLiente(clienteResponsavel.getIdCliente());
		transacao.setDataEnvio(Calendar.getInstance().getTime());
		transacao.setIdUsuario(usuario.getId());
		transacao.setOperacao((tipoOperacao == null) ? null : tipoOperacao.toString());
		transacao.setNumeroCompromissoPre(idCompromisso);
		transacao.setStatus(EStatusItemTransacao.AGUARDANDO_ENVIO.getStatus());
		transacao.setSeloProcuracao(seloReconhecimento != null ? Util.formataAlphaNumerico(Util.removerAcentos(seloReconhecimento)) : seloReconhecimento);
		transacao.setFlgDemo(demonstracao);
		transacao.setFgOrigem(EOrigem.WEBSERVICE.getCodigo());

		if (clienteAutenticacao.getTipoInstituicao().equals(ETipoCliente.PARCEIRO.getCodigo())) {
			transacao.setIdParceiro(clienteAutenticacao.getIdCliente());
		}

		// acesso Volante
		if (ticketVolante != null && !ticketVolante.equals("")) {
			transacao.setIdTicketVolante(ticketVolante);
		}

		transacaoRepository.save(transacao);
		transacaoRepository.flush();

		// gravando log de criacao da transacao
		// logBusiness.gravaLog(transacao, StatusTransacao.CRIADA, null);

		return transacao;

	}

	private ComunicacaoVenda preencheValoresDefault(ComunicacaoVenda cve) {

		// atribuindo valores padroes para a chamada
		cve.setTipoDocCartorio(2);

		if (cve.getNumIdentCartorio() == null || cve.getNumIdentCartorio().equals("")) {
			cve.setNumIdentCartorio("06293184000101");
			cve.setNomeCartorio("FEBRANOR452");
			cve.setNomeLogradouroCartorio("SRTVS QD701 LT 05 BL A SL 601");
			cve.setNumeroImovelCartorio("604");
			cve.setComplementoImovelCartorio("");
			cve.setBairroImovelCartorio("ASA SUL");
			cve.setCodMunicipioImovelCartorio(9701);
			cve.setCepImovelCartorio("6455000");
			cve.setUfImovelCartorio("DF");
			cve.setDddTelCartorio(61);
			cve.setNumTelCartorio(33231555l);
		}

		return cve;
	}

	private ComunicacaoVenda loadCodigoMunicipio(ComunicacaoVenda cve) {

		Integer idMunicipioComprador = municipioCepRepository.getCodigoMunicipioByCep(Integer.parseInt(cve.getCepImovelComprador()));

		cve.setCodMunicipioImovelComprador(idMunicipioComprador);
		cve.setCodMunicipioLocalVenda(idMunicipioComprador);

		return cve;
	}

	public Boolean verificarHorarioBloqueio() throws Exception {

		List<HorarioBloqueio> lstHorarioBloqueioResult = new ArrayList<HorarioBloqueio>();

		GregorianCalendar gc = new GregorianCalendar();
		Integer dayOfWeek = gc.get(Calendar.DAY_OF_WEEK);

		List<HorarioBloqueio> lstHorarioBloqueio = horarioBloqueioRepository.getHorarioBloqueioByDiaSemanaAndFlgOnline(dayOfWeek, "S");

		if (lstHorarioBloqueio == null)
			return Boolean.FALSE;

		String hour;
		String min;

		for (int i = 0; i < lstHorarioBloqueio.size(); i++) {
			hour = lstHorarioBloqueio.get(i).getHoraInicio().substring(0, 2);

			min = lstHorarioBloqueio.get(i).getHoraInicio().substring(2, 4);

			lstHorarioBloqueio.get(i).setDHoraInicio(Double.valueOf(hour + "." + min));

			hour = lstHorarioBloqueio.get(i).getHoraFim().substring(0, 2);

			min = lstHorarioBloqueio.get(i).getHoraFim().substring(2, 4);

			lstHorarioBloqueio.get(i).setDHoraFim(Double.valueOf(hour + "." + min));

			lstHorarioBloqueioResult.add(lstHorarioBloqueio.get(i));
		}

		SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
		SimpleDateFormat sdfMin = new SimpleDateFormat("mm");

		hour = "";
		min = "";

		Date dateCurrent = new Date();

		hour = sdfHour.format(dateCurrent);
		min = sdfMin.format(dateCurrent);

		Double dHourSytem = new Double(hour + "." + min);

		for (int i = 0; i < lstHorarioBloqueio.size(); i++) {
			Double dHourFimBloqueio = lstHorarioBloqueio.get(i).getDHoraFim();
			Double dHourInicioBloqueio = lstHorarioBloqueio.get(i).getDHoraInicio();

			if (dHourFimBloqueio.doubleValue() == 0)
				dHourFimBloqueio = 24.0;

			if (dHourInicioBloqueio.doubleValue() <= dHourSytem.doubleValue() && dHourFimBloqueio.doubleValue() >= dHourSytem.doubleValue()) {
				return true;
			}
		}

		return Boolean.FALSE;
	}

	// implementado com o melhorias 5
	public Boolean verificarCveBloqueada(ComunicacaoVenda cve) throws Exception {

		Optional<VwTransacoesBloqueadas> vwTransacoesBloqueadas = vwTransacaoBloqueioRepository.getVwTransacoesBloqueadasByNumIdentProprietarioAndPlacaAndRenavam(cve.getNumIdentProprietario(), cve.getPlacaUnica(), new Long(cve.getRenavam()));

		if (vwTransacoesBloqueadas.isPresent())
			return Boolean.TRUE;

		return Boolean.FALSE;
	}

	private String geraParteVariavel(ComunicacaoVenda cve, String tipo, Long codSegurancaCancelamento, String caracteresPermitidos) throws NumberFormatException, Exception {

		StringBuilder strVariavel = new StringBuilder("");

		// placa unica
		strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getPlacaUnica()), " ", 7, 2, caracteresPermitidos));

		// RENAVAM
		strVariavel.append(Util.preencheCom(cve.getRenavam() + "", "0", 11, 1, caracteresPermitidos));

		if (tipo.equals("925")) {
			// numero de identificacao do proprietario
			strVariavel.append(Util.preencheCom(cve.getTipoDocCartorio() + "", "0", 1, 1, caracteresPermitidos));

			// nome do proprietario
			strVariavel.append(Util.preencheCom(cve.getNumIdentCartorio(), " ", 14, 2, caracteresPermitidos));

			// ano fabricacao
			strVariavel.append(Util.preencheCom(cve.getNumIdentCV() + "", "0", 12, 1, caracteresPermitidos));

		} else {
			// numero de identificacao do proprietario
			strVariavel.append(Util.preencheCom(Util.removeMascara(cve.getNumIdentProprietario()), " ", 14, 2, caracteresPermitidos));

			// nome do proprietario (O nome do proprietario nao deve ter caracteres especiais removidos)
			strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getNomeProprietario()), " ", 40, 2, null));

			// ano fabricacao
			strVariavel.append(Util.preencheCom(cve.getAnoFabricacao() + "", "0", 4, 1, caracteresPermitidos));

			// ano modelo
			strVariavel.append(Util.preencheCom(cve.getAnoModelo() + "", "0", 4, 1, caracteresPermitidos));

			// UF emissao
			strVariavel.append(Util.preencheCom(cve.getUfEmissao(), " ", 2, 2, caracteresPermitidos));

			// numero CRV
			strVariavel.append(Util.preencheCom(cve.getNumeroCRV() + "", "0", 12, 1, caracteresPermitidos));

			// data emissao CRV
			strVariavel.append(Util.preencheCom(cve.getDataEmissao() + "", "0", 8, 1, caracteresPermitidos));

			// numero da via
			strVariavel.append(Util.preencheCom(cve.getNumeroVia() + "", "0", 2, 1, caracteresPermitidos));

			if (codSegurancaCancelamento == null) {
				// codigo seguranca
				strVariavel.append(Util.preencheCom((cve.getCodigoSegurancaCRV() == null ? "" : cve.getCodigoSegurancaCRV() + ""), "0", 11, 1, caracteresPermitidos));
			} else {
				// codigo seguranca
				strVariavel.append(Util.preencheCom((codSegurancaCancelamento == null ? "" : codSegurancaCancelamento + ""), "0", 11, 1, caracteresPermitidos));
			}

			if (tipo.equals("234")) {
				// tipo documento cartorio
				strVariavel.append(Util.preencheCom(cve.getTipoDocCartorio() + "", "0", 1, 1, caracteresPermitidos));

				// numero identificacao cartorio
				strVariavel.append(Util.preencheCom(Util.removeMascara(cve.getNumIdentCartorio()), " ", 14, 2, caracteresPermitidos));

				// numero identificacao CV
				strVariavel.append(Util.preencheCom(cve.getNumIdentCV() + "", "0", 12, 1, caracteresPermitidos));

				// motivo cancelamento
				strVariavel.append("1");
			} else {
				// tipo documento comprador
				strVariavel.append(Util.preencheCom(cve.getTipoDocComprador() + "", "0", 1, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// numero identificacao comprador
				strVariavel.append(Util.preencheCom(Util.removeMascara(cve.getNumIdentComprador()), " ", 14, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// nome do comprador
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getNomeComprador()), " ", 40, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// nome logradouro comprador
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getNomeLogradouroComprador()), " ", 30, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// numero imovel comprador
				strVariavel.append(Util.preencheCom(cve.getNumeroImovelComprador(), " ", 5, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// complemento imovel comprador
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getComplementoImovelComprador()), " ", 20, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// bairro imovel comprador
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getBairroImovelComprador()), " ", 20, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// codigo municipio imovel comprador
				strVariavel.append(Util.preencheCom(cve.getCodMunicipioImovelComprador() + "", "0", 4, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// UF imovel comprador
				strVariavel.append(Util.preencheCom(cve.getUfImovelComprador(), " ", 2, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// CEP imovel comprador
				strVariavel.append(Util.preencheCom(cve.getCepImovelComprador(), "0", 8, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// ****************** Dados da venda *****************************

				// codigo municipio local venda
				strVariavel.append(Util.preencheCom(cve.getCodMunicipioLocalVenda() + "", "0", 4, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// data venda
				strVariavel.append(Util.preencheCom(cve.getDataVenda() + "", " ", 8, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// ****************** Dados do cartorio *****************************

				// tipo documento cartorio
				strVariavel.append(Util.preencheCom(Constantes.TIPO_DOCUMENTO_CNPJ + "", "0", 1, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// numero Identificacao cartorio
				strVariavel.append(Util.preencheCom(Util.removeMascara(cve.getNumIdentCartorio()), " ", 14, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// nome cartorio
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getNomeCartorio()), " ", 40, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// nome logradouro cartorio
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getNomeLogradouroCartorio()), " ", 30, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// numero imovel cartorio
				strVariavel.append(Util.preencheCom(cve.getNumeroImovelCartorio(), " ", 5, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// complemento imovel cartorio
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getComplementoImovelCartorio()), " ", 20, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// bairro cartorio
				strVariavel.append(Util.preencheCom(Util.formataStringParaAplic(cve.getBairroImovelCartorio()), " ", 20, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// Codigo municipio cartorio
				strVariavel.append(Util.preencheCom(cve.getCodMunicipioImovelCartorio() + "", "0", 4, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// UF cartorio
				strVariavel.append(Util.preencheCom(cve.getUfImovelCartorio(), " ", 2, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// CEP cartorio
				strVariavel.append(Util.preencheCom(cve.getCepImovelCartorio(), "0", 8, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// DDD telefone cartorio
				strVariavel.append(Util.preencheCom(cve.getDddTelCartorio() + "", "0", 2, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// numero telefone cartorio
				strVariavel.append(Util.preencheCom(cve.getNumTelCartorio() + "", "0", 9, Util.DIRECAO_ESQUERDA, caracteresPermitidos));

				// numero indentificacao CV
				if (cve.getNumIdentCV() == null) {
					strVariavel.append(Util.preencheCom("000000000000", "0", 12, Util.DIRECAO_ESQUERDA, caracteresPermitidos));
				} else {
					strVariavel.append(Util.preencheCom(cve.getNumIdentCV() + "", "0", 12, Util.DIRECAO_ESQUERDA, caracteresPermitidos));
				}
				
				// tipo CVE
				// Integer tipoCVE = cveRepository.getTipoCVE(cve.getOrigemTransacao());
				Integer tipoCVE = 1; // a pedido do Gilson foi colocado hard coded
				strVariavel.append(Util.preencheCom(tipoCVE + "", "0", 1, Util.DIRECAO_DIREITA, caracteresPermitidos));

				// numero CNPJ IF
				// String numeroCnpjIf = (tipoCVE.intValue() == 1) ? "0" : cve.getNumeroCnpjIF();
				// strVariavel.append(Util.preencheCom(cve.getNumeroCnpjIF(), "0", 14, Util.DIRECAO_DIREITA, caracteresPermitidos));
				strVariavel.append(Util.preencheCom("", "0", 14, Util.DIRECAO_DIREITA, caracteresPermitidos));
			}
		}

		return strVariavel.toString();
	}

}
