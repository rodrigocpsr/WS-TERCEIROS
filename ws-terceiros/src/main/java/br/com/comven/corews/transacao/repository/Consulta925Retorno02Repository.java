package br.com.comven.corews.transacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.Consulta925Retorno02;

/**
 * This interface allows us to save and retrieve Users
 */
public interface Consulta925Retorno02Repository extends CrudRepository<Consulta925Retorno02, Long> {

    //Optional<List<transacaoDialog>> findtransacaoDialogBytransacaoConfigAndUserOrderByDataAsc(final transacaoConfig id, final transacaoUser user);

}
