package br.com.comven.corews.transacao.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.CancelamentoVenda;
import br.com.comven.corews.transacao.domain.ComunicacaoVenda;

/**
 * This interface allows us to save and retrieve Users
 */
public interface CancelamentoVendaRepository extends CrudRepository<CancelamentoVenda, Long> {

	Optional<CancelamentoVenda> getCancelamentoVendaByNumIdentCV(Long numIdentCV); 

}
