package de.webalf.daaapi.configuration.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
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
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
				// no session management required
				.sessionManagement(sessionManagement -> sessionManagement
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(SWAGGER_WHITELIST).permitAll()
						.anyRequest().authenticated()
				)

				// disable Cross Site Request Forgery token
				// we do not rely on cookie based auth and are completely stateless, so we are safe
				.csrf(CsrfConfigurer::disable)

				// authentication for token based authentication
				.authenticationProvider(tokenAuthProvider)
				.addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class);

		return http.build();
	}
}
