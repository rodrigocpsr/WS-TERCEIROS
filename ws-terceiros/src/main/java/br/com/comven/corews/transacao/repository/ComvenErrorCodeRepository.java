package br.com.comven.corews.transacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.comven.corews.transacao.domain.ComvenErrorCode;

/**
 * This interface allows us to save and retrieve Users
 */
public interface ComvenErrorCodeRepository extends JpaRepository<ComvenErrorCode, Long> {

	 Optional<ComvenErrorCode> findComvenErrorCodeByCodigoErro(final Integer codigoErro);

}
