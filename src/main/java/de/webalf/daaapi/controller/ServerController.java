package de.webalf.daaapi.controller;

import de.webalf.daaapi.assembler.ServerConfigAssembler;
import de.webalf.daaapi.model.dtos.ServerConfigDto;
import de.webalf.daaapi.model.dtos.ServerConfigPostDto;
import de.webalf.daaapi.service.ServerConfigService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static de.webalf.daaapi.controller.Urls.API;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

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

	@GetMapping(produces = TEXT_PLAIN_VALUE)
	@ApiResponses({
			@ApiResponse(code = 200, message = "", response = String.class, examples = @Example(@ExampleProperty(value = "arma.deutsche-arma-allianz.de:2302:GanzSicheresPasswort", mediaType = TEXT_PLAIN_VALUE))),
			@ApiResponse(code = 400, message = "No next server url configured.")
	})
	public String getServerConfig() {
		log.trace("getServerConfig");
		return serverConfigService.getCurrentServer();
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ApiResponse(code = 400, message = "[fields] is/are invalid. Missing mandatory field?", examples = @Example(@ExampleProperty(value = "{\n" +
			"  \"errorMessage\": \"serverIp is invalid. Missing mandatory field?\",\n" +
			"  \"requestedURI\": \"" + API + "/server\"\n" +
			"}", mediaType = APPLICATION_JSON_VALUE)))
	public ServerConfigDto postServerConfig(@Valid @RequestBody ServerConfigPostDto serverConfig) {
		log.trace("postServerConfig");
		return ServerConfigAssembler.toDto(serverConfigService.createServerConfig(serverConfig));
	}

	@PutMapping(value = "/{serverConfigId}/active", produces = APPLICATION_JSON_VALUE)
	@ApiResponse(code = 404, message = "Resource not found")
	public ServerConfigDto putServerConfigActive(@PathVariable long serverConfigId) {
		log.trace("putServerConfigActive");
		return ServerConfigAssembler.toDto(serverConfigService.setNextServerConfig(serverConfigId));
	}
}
