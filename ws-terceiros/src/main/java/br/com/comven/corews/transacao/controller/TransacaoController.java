package br.com.comven.corews.transacao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.comven.corews.transacao.bean.SaidaBean;
import br.com.comven.corews.transacao.domain.CancelamentoVenda;
import br.com.comven.corews.transacao.domain.ComunicacaoVenda;
import br.com.comven.corews.transacao.domain.Consulta925;
import br.com.comven.corews.transacao.service.TransacaoService;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

	private final TransacaoService transacaoService;
	private Logger logger = LoggerFactory.getLogger(TransacaoController.class);

	@Autowired
	public TransacaoController(final TransacaoService transacaoService) {
		this.transacaoService = transacaoService;
	}

	@PostMapping("/cve")
	public ResponseEntity<SaidaBean> comunicarVenda(HttpServletRequest request, @RequestBody ComunicacaoVenda bean) throws Exception {
		
		String operador = request.getHeader("operador");
		String cnpjResponsavel = request.getHeader("cnpj-responsavel");
		
		SaidaBean saida;
		
		logger.info("----------------------------------");
		logger.info("CVE");
		logger.info("----------------------------------");
		logger.info(bean.toString());
		
		
		try {
			saida = transacaoService.comunicarVendaVeiculo233(operador, cnpjResponsavel,  bean);
			ResponseEntity<SaidaBean> retornoTransacao =  new ResponseEntity<SaidaBean>(saida, HttpStatus.OK);
			return retornoTransacao;
		}catch(ValidationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.BAD_REQUEST);
			return retornoTransacao;
		}catch(AuthenticationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.UNAUTHORIZED);
			return retornoTransacao;
		} finally {
			logger.info("----------------------------------");
			logger.info("");
			logger.info("");
		}
	}
	
	@PostMapping("/cancelamento")
	public ResponseEntity<SaidaBean> cancelarVenda(HttpServletRequest request, @RequestBody CancelamentoVenda bean) throws Exception {
		
		String operador = request.getHeader("operador");
		String cnpjResponsavel = request.getHeader("cnpj-responsavel");
		
		SaidaBean saida;
		
		logger.info("----------------------------------");
		logger.info("CANCELAMENTO");
		logger.info("----------------------------------");
		logger.info(bean.toString());
		
		try {
			saida = transacaoService.cancelarVendaVeiculo234(operador, cnpjResponsavel, bean);
			ResponseEntity<SaidaBean> retornoTransacao =  new ResponseEntity<SaidaBean>(saida, HttpStatus.OK);
			return retornoTransacao;
		} catch(ValidationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.BAD_REQUEST);
			return retornoTransacao;
		} catch(AuthenticationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.UNAUTHORIZED);
			return retornoTransacao;
		} finally {
			logger.info("----------------------------------");
			logger.info("");
			logger.info("");
		}
		
		
	}
	
	@PostMapping("/consulta")
	public  ResponseEntity<SaidaBean> consulta(HttpServletRequest request, @RequestBody Consulta925 bean) throws Exception {
		
		String operador = request.getHeader("operador");
		String cnpjResponsavel = request.getHeader("cnpj-responsavel");
		
		SaidaBean saida;
		
		logger.info("----------------------------------");
		logger.info("CONSULTA");
		logger.info("----------------------------------");
		logger.info(bean.toString());
		
		try {
			saida = transacaoService.consulta925(operador, cnpjResponsavel, bean);
			ResponseEntity<SaidaBean> retornoTransacao =  new ResponseEntity<SaidaBean>(saida, HttpStatus.OK);
			return retornoTransacao;
		}catch(ValidationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.BAD_REQUEST);
			return retornoTransacao;
		}catch(AuthenticationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.UNAUTHORIZED);
			return retornoTransacao;
		}finally {
			logger.info("----------------------------------");
			logger.info("");
			logger.info("");
		}
		
	}
	
	@PostMapping("/prepago/consulta")
	public  ResponseEntity<SaidaBean> consultaPre(HttpServletRequest request) throws Exception {
		
		String operador = request.getHeader("operador");
		String cnpjResponsavel = request.getHeader("cnpj-responsavel");
		
		SaidaBean saida;
		
		logger.info("----------------------------------");
		logger.info("CONSULTA PREPAGO");
		logger.info("----------------------------------");
		
		
		try {
			saida = transacaoService.consultaPrepago(operador, cnpjResponsavel);
			ResponseEntity<SaidaBean> retornoTransacao =  new ResponseEntity<SaidaBean>(saida, HttpStatus.OK);
			return retornoTransacao;
		}catch(ValidationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.BAD_REQUEST);
			return retornoTransacao;
		}catch(AuthenticationException e) {
			saida = new SaidaBean(e.getMessage());
			ResponseEntity<SaidaBean> retornoTransacao = new ResponseEntity<SaidaBean>(saida, HttpStatus.UNAUTHORIZED);
			return retornoTransacao;
		} finally {
			logger.info("----------------------------------");
		}
		
	}
	
}
