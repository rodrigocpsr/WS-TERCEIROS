package br.com.comven.corews.usuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.comven.corews.usuario.domain.Usuario;

/**
 * This interface allows us to save and retrieve Users
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	@Query("SELECT u FROM Usuario u WHERE u.login = :login and u.senha = :senha")
    Optional<Usuario> autenticar(@Param("login") final String login, @Param("senha") final String senha);

}
