package br.com.comven.corews.transacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.ComunicacaoVendaDetalhe;

/**
 * This interface allows us to save and retrieve Users
 */
public interface ComunicacaoVendaDetalheRepository extends CrudRepository<ComunicacaoVendaDetalhe, Long> {

    

}
