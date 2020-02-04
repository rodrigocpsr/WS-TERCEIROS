package br.com.comven.corews.transacao.security;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;

public class TokenAuthenticationService {

	// EXPIRATION_TIME = 10 dias
	static final long EXPIRATION_TIME = 860_000_000;
	static final String SECRET = "d17f2933b20a267cdd7cf675d6f7b855";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	static Authentication getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(HEADER_STRING);
		
		if (!token.contains("Basic")) {

			if (token != null) {
				// faz parse do token
				String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
						.getSubject();
	
				if (user != null) {
					return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
				}
			}
		
		}
		return null;
	}

}
