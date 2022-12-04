package de.webalf.daaapi.configuration.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EndpointConfig {
	private final TokenAuthFilter tokenAuthFilter;
	private final TokenAuthProvider tokenAuthProvider;

	private static final String[] SWAGGER_WHITELIST = {
			// -- swagger ui
			"/swagger-ui/**",
			"/swagger-ui.html",
			"/v3/api-docs/**"
	};

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers(SWAGGER_WHITELIST);
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
				// no session management required
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()

				.authorizeHttpRequests(authorize -> authorize
//						.requestMatchers(SWAGGER_WHITELIST).permitAll()
								.anyRequest().authenticated()
				)

				// disable Cross Site Request Forgery token
				// we do not rely on cookie based auth and are completely stateless, so we are safe
				.csrf().disable()

				// authentication for token based authentication
				//FIXME provider never called and swagger endpoints completely blocked
				.authenticationProvider(tokenAuthProvider)
				.addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class);

		return http.build();
	}
}
