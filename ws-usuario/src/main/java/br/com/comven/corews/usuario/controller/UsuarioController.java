package br.com.comven.corews.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.comven.corews.usuario.domain.Usuario;
import br.com.comven.corews.usuario.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private final UsuarioService usuarioService;

	@Autowired
	public UsuarioController(final UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/{userId}")
	public Usuario getUserById(@PathVariable("userId") final Long userId) {
		return usuarioService.getById(userId);
	}
	
}
