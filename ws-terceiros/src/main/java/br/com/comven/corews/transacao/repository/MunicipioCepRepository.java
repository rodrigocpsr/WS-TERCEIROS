package br.com.comven.corews.transacao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.MunicipioCEP;

/**
 * This interface allows us to save and retrieve Users
 */
public interface MunicipioCepRepository extends CrudRepository<MunicipioCEP, Long> {

    //Optional<List<transacaoDialog>> findtransacaoDialogBytransacaoConfigAndUserOrderByDataAsc(final transacaoConfig id, final transacaoUser user);
	
	@Query(value = "select MAX(FK_MUNICIPIO) from municipio_cep where FAIXA_CEP_INICIAL <= ?1 and FAIXA_CEP_FINAL >= ?1 GROUP BY FK_MUNICIPIO", nativeQuery = true)
	Integer getCodigoMunicipioByCep(Integer cep);
	

}
