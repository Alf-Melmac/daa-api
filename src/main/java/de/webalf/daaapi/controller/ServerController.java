package de.webalf.daaapi.controller;

import de.webalf.daaapi.assembler.ServerConfigAssembler;
import de.webalf.daaapi.model.dtos.ServerConfigDto;
import de.webalf.daaapi.model.dtos.ServerConfigPostDto;
import de.webalf.daaapi.service.ServerConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
							responseCode = "200", description = "OK",
							content = @Content(
									examples = {
											@ExampleObject(name = "explanation", value = "serverIp:port:password"),
											@ExampleObject(name = "example", value = "127.0.0.1:2302:GanzSicheresPasswort")
									})),
					@ApiResponse(
							responseCode = "400",
							description = "No next server url configured.",
							content = @Content(
									examples = {
											@ExampleObject(value = """
													    {
													    "errorMessage": "No next server url configured.",
													    "requestedURI": "/api/v1/server"
													}""")
									}
							))
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
			responses = {
					@ApiResponse(responseCode = "200", description = "OK"),
					@ApiResponse(
							responseCode = "400",
							description = "Validation exception",
							content = @Content(
									examples = @ExampleObject(value = """
											{
											  "errorMessage": "serverIp is invalid. Missing mandatory field?",
											  "requestedURI": "/api/v1/server"
											}""")
							))
			})
	public ServerConfigDto postServerConfig(@Valid @RequestBody ServerConfigPostDto serverConfig) {
		log.trace("postServerConfig");
		return ServerConfigAssembler.toDto(serverConfigService.createServerConfig(serverConfig));
	}

	@PutMapping(value = "/{serverConfigId}/active", produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Put server config active",
			description = "Switches the active server configuration to the configuration with the passed ID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "OK"),
					@ApiResponse(
							responseCode = "404",
							description = "Resource not found",
							content = @Content(
									examples = @ExampleObject(value = """
											{
											    "timestamp": "2022-01-01T00:00:00.000+00:00",
											    "status": 404,
											    "error": "Not Found",
											    "path": "/api/v1/server/20/active"
											}""")
							))})
	public ServerConfigDto putServerConfigActive(@PathVariable long serverConfigId) {
		log.trace("putServerConfigActive");
		return ServerConfigAssembler.toDto(serverConfigService.setNextServerConfig(serverConfigId));
	}
}
