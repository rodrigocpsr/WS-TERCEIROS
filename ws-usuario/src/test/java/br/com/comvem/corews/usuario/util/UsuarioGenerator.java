package br.com.comvem.corews.usuario.util;

import br.com.comven.corews.usuario.domain.Usuario;

public class UsuarioGenerator {
	
	public static Usuario generateCommonUser() {
		
		Usuario usuario = new Usuario();
		
		usuario.setId(1L);
		usuario.setLogin("64292369780");
		usuario.setNome("Rodrigo da Silva");
		usuario.setSenha("1234");
		usuario.setEnabled(1);
		
		return usuario;
	}

}
