package br.com.comven.corews.transacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.CancelamentoVendaRetorno01;

/**
 * This interface allows us to save and retrieve Users
 */
public interface CancelamentoRetorno01Repository extends CrudRepository<CancelamentoVendaRetorno01, Long> {

    //Optional<List<transacaoDialog>> findtransacaoDialogBytransacaoConfigAndUserOrderByDataAsc(final transacaoConfig id, final transacaoUser user);

}
