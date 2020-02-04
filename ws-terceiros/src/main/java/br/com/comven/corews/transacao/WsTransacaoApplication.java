package br.com.comven.corews.transacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class WsTransacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsTransacaoApplication.class, args);
	}
}
