package br.com.comven.corews.transacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.comven.corews.transacao.domain.Cliente;

/**
 * This interface allows us to save and retrieve Users
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
    Optional<Cliente> findClienteByCnpj(final String cnpj);
}
