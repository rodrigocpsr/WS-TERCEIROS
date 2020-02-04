package br.com.comven.corews.transacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.ComunicacaoVenda;

/**
 * This interface allows us to save and retrieve Users
 */
public interface ComunicacaoVendaRepository extends CrudRepository<ComunicacaoVenda, Long> {

    //Optional<List<transacaoDialog>> findtransacaoDialogBytransacaoConfigAndUserOrderByDataAsc(final transacaoConfig id, final transacaoUser user);
	
	@Query(value = "select IDTIPOCVE from tipo_cve WHERE SISTEMA = 'CIF' AND SIGLA_TIPO_CVE = ?1", nativeQuery = true)
	Integer getTipoCVE(String sigla);
	
	Optional<ComunicacaoVenda> getComunicacaoVendaByNumIdentCV(Long numIdentCV); 

}
