package de.webalf.daaapi.configuration.authentication;

import de.webalf.daaapi.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Component
@Slf4j
public class TokenAuthProvider implements AuthenticationProvider {
	@Value("${daa-api.auth.token}")
	private String authToken;

	@Override
	public Authentication authenticate(Authentication auth) {
		return auth;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return (DaaApiAuthentication.class.isAssignableFrom(aClass));
	}

	void assertAccess(String apiToken) throws ForbiddenException {
		if (!authToken.equals(apiToken)) {
			log.warn("Received request with invalid token {}", apiToken);
			throw new ForbiddenException("Invalid token '" + apiToken + "'");
		}
	}
}
