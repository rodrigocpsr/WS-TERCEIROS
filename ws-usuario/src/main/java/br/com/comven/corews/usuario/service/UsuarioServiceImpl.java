package br.com.comven.corews.usuario.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.comven.corews.usuario.domain.Usuario;
import br.com.comven.corews.usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository usuarioRepository;

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public Usuario getById(Long id) {
		
		System.out.println("entrou");
		return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("The requested id [" + id +"] does not exist."));
		
	}
	
	
}
