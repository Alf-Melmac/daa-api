package de.webalf.daaapi.configuration.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Component
@NoArgsConstructor
public class DaaApiAuthentication implements Authentication {
	@Getter
	private String credentials;
	@Getter
	private final Collection<? extends GrantedAuthority> authorities = Collections.emptyList();

	public DaaApiAuthentication(String token) {
		credentials = token;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean b) {
		/*no op*/
	}

	@Override
	public String getName() {
		return null;
	}
}
