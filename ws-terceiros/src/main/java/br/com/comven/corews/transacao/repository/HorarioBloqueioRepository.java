package br.com.comven.corews.transacao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.comven.corews.transacao.domain.HorarioBloqueio;

/**
 * This interface allows us to save and retrieve Users
 */
public interface HorarioBloqueioRepository extends CrudRepository<HorarioBloqueio, Long> {
	
	List<HorarioBloqueio> getHorarioBloqueioByDiaSemanaAndFlgOnline(Integer diaSemana, String flgOnline);
	
}
