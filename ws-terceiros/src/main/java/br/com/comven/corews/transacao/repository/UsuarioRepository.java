package br.com.comven.corews.transacao.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.Usuario;

/**
 * This interface allows us to save and retrieve Users
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
    Optional<Usuario> findUsuarioByLogin(final String login);
}
