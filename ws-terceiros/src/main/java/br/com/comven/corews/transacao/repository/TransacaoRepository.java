package br.com.comven.corews.transacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.comven.corews.transacao.domain.Transacao;

/**
 * This interface allows us to save and retrieve Users
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    //Optional<List<transacaoDialog>> findtransacaoDialogBytransacaoConfigAndUserOrderByDataAsc(final transacaoConfig id, final transacaoUser user);
	
	@Query(value = "SELECT SEQ_TRANSACAO.NEXTVAL FROM DUAL", nativeQuery = true)
	Long getNextIdTransacao();

}
