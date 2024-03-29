package de.webalf.daaapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Entity
@Table(name = "server", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ServerConfig extends AbstractIdEntity {
	@Column(name = "server_ip", nullable = false, updatable = false)
	@NotBlank
	private String serverIp;

	@Column(name = "port", nullable = false, updatable = false)
	private int port;

	@Column(name = "password", updatable = false)
	private String password;

	@Column(name = "next", nullable = false)
	@Setter
	//At any time there should be only one configuration which is marked as next
	private boolean next = false;

	@Override
	public String toString() {
		String ret = serverIp + ":" + port;
		if (!(password == null || password.isBlank())) {
			ret += ":" + password;
		}
		return ret;
	}
}
