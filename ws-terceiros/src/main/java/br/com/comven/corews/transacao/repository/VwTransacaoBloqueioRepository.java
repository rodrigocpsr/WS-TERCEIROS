package br.com.comven.corews.transacao.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.VwTransacoesBloqueadas;

/**
 * This interface allows us to save and retrieve Users
 */
public interface VwTransacaoBloqueioRepository extends CrudRepository<VwTransacoesBloqueadas, Long> {

    //Optional<List<transacaoDialog>> findtransacaoDialogBytransacaoConfigAndUserOrderByDataAsc(final transacaoConfig id, final transacaoUser user);
	
	Optional<VwTransacoesBloqueadas> getVwTransacoesBloqueadasByNumIdentProprietarioAndPlacaAndRenavam(String numIdentProprietario, String placa, Long revanam);
	

}
