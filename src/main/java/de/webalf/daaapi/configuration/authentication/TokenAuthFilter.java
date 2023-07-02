package de.webalf.daaapi.configuration.authentication;

import de.webalf.daaapi.exception.ForbiddenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Component
@Slf4j
public class TokenAuthFilter extends OncePerRequestFilter {
	@Value("${daa-api.auth.token.name:daa-api-auth-token}")
	@Getter
	private String authTokenName;

	private final TokenAuthProvider tokenAuthProvider;
	private final HandlerExceptionResolver resolver;

	@Autowired
	public TokenAuthFilter(TokenAuthProvider tokenAuthProvider, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
		this.tokenAuthProvider = tokenAuthProvider;
		this.resolver = resolver;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
		// check if header contains auth token
		String authToken = request.getHeader(authTokenName);

		// if there is an auth token, create an Authentication object
		if (authToken != null) {
			log.info("{} API request to '{}' with token '{}' from: {}", request.getMethod(), request.getRequestURL(), authToken, request.getHeader("user-agent"));
			try {
				tokenAuthProvider.assertAccess(authToken);
			} catch (ForbiddenException ex) {
				resolver.resolveException(request, response, null, ex);
				return;
			}

			Authentication auth = new DaaApiAuthentication(authToken);
			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		// forward the request
		filterChain.doFilter(request, response);
	}
}