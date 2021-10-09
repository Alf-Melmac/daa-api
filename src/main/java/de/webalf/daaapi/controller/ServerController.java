package de.webalf.daaapi.controller;

import de.webalf.daaapi.assembler.ServerConfigAssembler;
import de.webalf.daaapi.model.dtos.ServerConfigDto;
import de.webalf.daaapi.service.ServerConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static de.webalf.daaapi.controller.Urls.API;

/**
 * @author Alf
 * @since 09.10.2021
 */
@RequestMapping(API + "/server")
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ServerController {
	private final ServerConfigService serverConfigService;

	@GetMapping
	public String getServerConfig() {
		log.trace("getServerConfig");
		return serverConfigService.getCurrentServer();
	}

	@PostMapping
	public ServerConfigDto postServerConfig(@Valid @RequestBody ServerConfigDto serverConfig) {
		log.trace("postServerConfig");
		return ServerConfigAssembler.toDto(serverConfigService.createServerConfig(serverConfig));
	}

	@PutMapping("/{serverConfigId}/active")
	public ServerConfigDto putServerConfigActive(@PathVariable long serverConfigId) {
		log.trace("putServerConfigActive");
		return ServerConfigAssembler.toDto(serverConfigService.setNextServerConfig(serverConfigId));
	}
}
