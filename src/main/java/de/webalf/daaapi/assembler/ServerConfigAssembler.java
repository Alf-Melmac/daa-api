package de.webalf.daaapi.assembler;

import de.webalf.daaapi.model.ServerConfig;
import de.webalf.daaapi.model.dtos.ServerConfigDto;
import lombok.experimental.UtilityClass;

/**
 * @author Alf
 * @since 09.10.2021
 */
@UtilityClass
public final class ServerConfigAssembler {
	public static ServerConfig fromDto(ServerConfigDto dto) {
		if (dto == null) {
			return null;
		}

		return ServerConfig.builder()
				.id(dto.getId())
				.serverIp(dto.getServerIp())
				.port(dto.getPort())
				.password(dto.getPassword())
				.build();
	}

	public static ServerConfigDto toDto(ServerConfig serverConfig) {
		return ServerConfigDto.builder()
				.id(serverConfig.getId())
				.serverIp(serverConfig.getServerIp())
				.port(serverConfig.getPort())
				.password(serverConfig.getPassword())
				.build();
	}
}
