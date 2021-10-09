package de.webalf.daaapi.service;

import de.webalf.daaapi.assembler.ServerConfigAssembler;
import de.webalf.daaapi.exception.BusinessRuntimeException;
import de.webalf.daaapi.exception.ResourceNotFoundException;
import de.webalf.daaapi.model.ServerConfig;
import de.webalf.daaapi.model.dtos.ServerConfigPostDto;
import de.webalf.daaapi.repository.ServerConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ServerConfigService {
	private final ServerConfigRepository serverConfigRepository;

	/**
	 * Returns the string representation of the {@link ServerConfig} marked as next ({@link ServerConfig#isNext()})
	 *
	 * @return string representation of next server config
	 */
	public String getCurrentServer() {
		return serverConfigRepository.findByNextIsTrue()
				.map(ServerConfig::toString)
				.orElseThrow(() -> {
					throw BusinessRuntimeException.builder().title("No next server url configured.").build();
				});
	}

	/**
	 * Creates a new {@link ServerConfig}. If a config with same values already exists the existing config is returned.
	 *
	 * @param serverConfig to create
	 * @return created server config
	 */
	public ServerConfig createServerConfig(ServerConfigPostDto serverConfig) {
		return serverConfigRepository.findByServerIpIgnoreCaseAndPortAndPassword(serverConfig.getServerIp(), serverConfig.getPort(), serverConfig.getPassword())
				.orElseGet(() -> serverConfigRepository.save(ServerConfigAssembler.fromDto(serverConfig)));
	}

	/**
	 * Sets the given {@link ServerConfig}, found by id, as next ({@link ServerConfig#isNext()}) and removes the old next marker.
	 *
	 * @param id of the next marked server config
	 * @return marked server config
	 */
	public ServerConfig setNextServerConfig(long id) {
		final ServerConfig newNext = serverConfigRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

		serverConfigRepository.findByNextIsTrue().ifPresent(oldNext -> oldNext.setNext(false));
		newNext.setNext(true);

		return newNext;
	}
}
