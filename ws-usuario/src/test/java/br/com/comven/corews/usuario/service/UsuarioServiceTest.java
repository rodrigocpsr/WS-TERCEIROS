package br.com.comven.corews.usuario.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.comvem.corews.usuario.util.UsuarioGenerator;
import br.com.comven.corews.usuario.domain.Usuario;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

	@Autowired
	@MockBean
	private UsuarioService usuarioService;

	@Test
	public void getUsuarioByIdTest() {
		
		System.out.println("teste");
		
		try {
			
			given(usuarioService.getById(1l)).willReturn(UsuarioGenerator.generateCommonUser());
			
			Usuario usuario = usuarioService.getById(1l);
			
			System.out.println(usuario);
			
			usuario = usuarioService.getById(usuario.getId());
			
			assertNotNull(usuario);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
	}

}
