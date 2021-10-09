package de.webalf.daaapi.repository;

import de.webalf.daaapi.model.ServerConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Repository
public interface ServerConfigRepository extends JpaRepository<ServerConfig, Long> {
	Optional<ServerConfig> findByNextIsTrue();

	Optional<ServerConfig> findByServerIpIgnoreCaseAndPortAndPassword(String serverIp, int port, String password);

}
