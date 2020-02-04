package br.com.comven.corews.transacao.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import br.com.comven.corews.transacao.client.prepago.cpf.WSControleIntCOMVEN;
import br.com.comven.corews.transacao.domain.Parametro;
import br.com.comven.corews.transacao.repository.ParametroRepository;

@Component
public class StaticContextInitializer {

	@Autowired
	ParametroRepository parametroRepository;
	
    @Autowired
    private CacheManager cacheManager;

    @PostConstruct
    public void init() {
    	update();
    	WSControleIntCOMVEN.setWSUrl(cacheManager);
    }
    
    private void update() {
		for (Parametro parametro : parametroRepository.findAll()) {
			cacheManager.getCache("parametro").put(parametro.getCodigo(), parametro.getValor());
		}
	}
}
