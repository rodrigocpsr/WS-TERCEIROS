package br.com.comven.corews.usuario.security;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	protected JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		final String authorization = request.getHeader("Authorization");
		
	    if (authorization != null && authorization.startsWith("Basic")) {
	    
	    	// Authorization: Basic base64credentials
	        String base64Credentials = authorization.substring("Basic".length()).trim();
	        String tempCredentials = new String(Base64.getDecoder().decode(base64Credentials),Charset.forName("UTF-8"));
	        final String[] values = tempCredentials.split(":",2);

	        AccountCredentials credentials = new AccountCredentials();	        
	        
	        credentials.setUsername(values[0]);
	        credentials.setPassword(values[1]);
	        
	        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getUsername(), credentials.getPassword(), Collections.emptyList()));
	        
	    }
	    
	    return null;

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {

		TokenAuthenticationService.addAuthentication(response, auth.getName());
	}

}
