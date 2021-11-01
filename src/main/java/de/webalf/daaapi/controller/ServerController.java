package de.webalf.daaapi.controller;

import de.webalf.daaapi.assembler.ServerConfigAssembler;
import de.webalf.daaapi.model.dtos.ServerConfigDto;
import de.webalf.daaapi.model.dtos.ServerConfigPostDto;
import de.webalf.daaapi.service.ServerConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
	@Operation(summary = "Get server configuration",
			description = "Retrieves the currently active server configuration.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK",
							content = @Content(
									mediaType = TEXT_PLAIN_VALUE,
									schema = @Schema(implementation = String.class),
									examples = {
											@ExampleObject(name = "explanation", value = "serverIp:port:password"),
											@ExampleObject(name = "example", value = "arma.deutsche-arma-allianz.de:2302:GanzSicheresPasswort")
									})),
					@ApiResponse(responseCode = "400", description = "No next server url configured.")
			})
	public String getServerConfig() {
		log.trace("getServerConfig");
		return serverConfigService.getCurrentServer();
	}

	@GetMapping(path = "/all", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Get all server configurations")
	public List<ServerConfigDto> getServerConfigs() {
		log.trace("getServerConfigs");
		return ServerConfigAssembler.toDtoList(serverConfigService.findAll());
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Post new server config",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ServerConfigPostDto.class)),
					required = true
			),
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK",
							content = @Content(
									mediaType = APPLICATION_JSON_VALUE,
									schema = @Schema(implementation = ServerConfigDto.class))),
					@ApiResponse(responseCode = "400", description = "Validation exception", content = @Content(
							mediaType = APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = String.class),
							examples = @ExampleObject(name = "example", value = "{\n" +
									"  \"errorMessage\": \"serverIp is invalid. Missing mandatory field?\",\n" +
									"  \"requestedURI\": \"" + API + "/server\"\n" +
									"}")
					))
			})
	public ServerConfigDto postServerConfig(@Valid @RequestBody ServerConfigPostDto serverConfig) {
		log.trace("postServerConfig");
		return ServerConfigAssembler.toDto(serverConfigService.createServerConfig(serverConfig));
	}

	@PutMapping(value = "/{serverConfigId}/active", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Put server config active",
			description = "Switches the active server configuration to the configuration with the passed ID.",
			responses = @ApiResponse(responseCode = "404", description = "Resource not found"))
	public ServerConfigDto putServerConfigActive(@PathVariable long serverConfigId) {
		log.trace("putServerConfigActive");
		return ServerConfigAssembler.toDto(serverConfigService.setNextServerConfig(serverConfigId));
	}
}
