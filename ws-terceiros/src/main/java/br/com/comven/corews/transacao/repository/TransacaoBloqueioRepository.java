package br.com.comven.corews.transacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.TransacaoBloqueio;

/**
 * This interface allows us to save and retrieve Users
 */
public interface TransacaoBloqueioRepository extends CrudRepository<TransacaoBloqueio, Long> {


}
