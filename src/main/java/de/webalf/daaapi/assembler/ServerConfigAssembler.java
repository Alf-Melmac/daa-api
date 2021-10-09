package de.webalf.daaapi.assembler;

import de.webalf.daaapi.model.ServerConfig;
import de.webalf.daaapi.model.dtos.ServerConfigDto;
import de.webalf.daaapi.model.dtos.ServerConfigPostDto;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Alf
 * @since 09.10.2021
 */
@UtilityClass
public final class ServerConfigAssembler {
	public static ServerConfig fromDto(ServerConfigPostDto dto) {
		if (dto == null) {
			return null;
		}

		return ServerConfig.builder()
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

	public static List<ServerConfigDto> toDtoList(Iterable<? extends ServerConfig> serverConfigs) {
		return StreamSupport.stream(serverConfigs.spliterator(), false)
				.map(ServerConfigAssembler::toDto)
				.collect(Collectors.toUnmodifiableList());
	}
}
