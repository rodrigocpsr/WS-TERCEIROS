package br.com.comven.corews.usuario.security;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//ConfigurationSource configurationSource = ConfigurationSource.

		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, "/autenticacao").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilterBefore(new JWTLoginFilter("/autenticacao", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		.httpBasic();
			
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	 
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select cnpj,ws_key,1 from cliente where cnpj = ?")
		.authoritiesByUsernameQuery("select cnpj,'U' from cliente where cnpj = ?")
		.passwordEncoder(passwordEncoder);
		
	}
	
	@Bean
    CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		List<String> lstExposedHeaders = new ArrayList<String>();
		
		lstExposedHeaders.add("Authorization");
		config.setExposedHeaders(lstExposedHeaders);
		
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return source;
    }

}