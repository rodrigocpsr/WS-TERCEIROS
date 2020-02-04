package br.com.comven.corews.transacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.Consulta925;

/**
 * This interface allows us to save and retrieve Users
 */
public interface Consulta925Repository extends CrudRepository<Consulta925, Long> {

    //Optional<List<transacaoDialog>> findtransacaoDialogBytransacaoConfigAndUserOrderByDataAsc(final transacaoConfig id, final transacaoUser user);

}
