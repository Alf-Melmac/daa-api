package de.webalf.daaapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.webalf.daaapi.MockMvcTest;
import de.webalf.daaapi.assembler.ServerConfigAssembler;
import de.webalf.daaapi.model.ServerConfig;
import de.webalf.daaapi.model.dtos.ServerConfigPostDto;
import de.webalf.daaapi.repository.ServerConfigRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.Locale;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Alf
 * @since 02.07.2023
 */
class ServerControllerTest extends MockMvcTest {
	public static final String PATH = "/api/v1/server";
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	public static final String SERVER_IP = "testIp";
	public static final int PORT = 1234;
	public static final String PASSWORD = "password";

	@Autowired
	private ServerConfigRepository serverConfigRepository;

	@ParameterizedTest
	@ValueSource(strings = {"", "/all"})
	void getEndpointsSecured(String endpoint) throws Exception {
		mvc.perform(get(PATH + endpoint))
				.andExpect(status().isForbidden());
	}

	@Test
	void getNoServerConfig() throws Exception {
		mvc.perform(get(PATH).header("daa-api-auth-token", authToken))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.errorMessage").value("No next server url configured."));
	}

	@Test
	void getServerConfig() throws Exception {
		final ServerConfig serverConfig = saveTestConfig();
		final ServerConfig nextServerConfig = saveTestConfig(true);

		assertThat(serverConfigRepository.findAll()).containsExactlyInAnyOrder(serverConfig, nextServerConfig);
		assertNextServerConfig(nextServerConfig);
	}

	private void assertNextServerConfig(@NonNull ServerConfig nextServerConfig) throws Exception {
		mvc.perform(get(PATH).header("daa-api-auth-token", authToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(TEXT_PLAIN_VALUE + ";charset=UTF-8"))
				.andExpect(content().string(nextServerConfig.toString()));
	}

	@Test
	void getEmptyServerConfigs() throws Exception {
		mvc.perform(get(PATH + "/all").header("daa-api-auth-token", authToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().json("[]"));
	}

	@Test
	void getServerConfigs() throws Exception {
		final ServerConfig serverConfig = saveTestConfig();
		mvc.perform(get(PATH + "/all").header("daa-api-auth-token", authToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(content().json("[" + OBJECT_MAPPER.writeValueAsString(ServerConfigAssembler.toDto(serverConfig)) + "]"));
	}

	private ServerConfig saveTestConfig() {
		return saveTestConfig(false);
	}

	private ServerConfig saveTestConfig(boolean next) {
		return serverConfigRepository.save(ServerConfig.builder().serverIp(SERVER_IP + UUID.randomUUID()).next(next).build());
	}

	private ServerConfig saveTestConfig(String serverIp, int port, String password) {
		return serverConfigRepository.save(ServerConfig.builder().serverIp(serverIp).port(port).password(password).build());
	}

	@Test
	void postServerConfigSecured() throws Exception {
		mvc.perform(post(PATH))
				.andExpect(status().isForbidden());
	}

	@Test
	void postServerConfigValidates() throws Exception {
		mvc.perform(post(PATH).header("daa-api-auth-token", authToken)
						.contentType(APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errorMessage").value("serverIp is invalid. Missing mandatory field?"));
	}

	@Test
	void postServerConfig() throws Exception {
		mvc.perform(post(PATH).header("daa-api-auth-token", authToken)
						.contentType(APPLICATION_JSON)
						.content(OBJECT_MAPPER.writeValueAsString(ServerConfigPostDto.builder().serverIp(SERVER_IP).port(PORT).password(PASSWORD).build())))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.serverIp").value(SERVER_IP))
				.andExpect(jsonPath("$.port").value(PORT))
				.andExpect(jsonPath("$.password").value(PASSWORD));

		assertServerConfig(SERVER_IP, PORT, PASSWORD);
	}

	@Test
	void postServerConfigDoesNotDuplicate() throws Exception {
		final ServerConfig serverConfig = saveTestConfig();

		mvc.perform(post(PATH).header("daa-api-auth-token", authToken)
						.contentType(APPLICATION_JSON)
						.content(OBJECT_MAPPER.writeValueAsString(ServerConfigPostDto.builder().serverIp(serverConfig.getServerIp()).port(serverConfig.getPort()).password(serverConfig.getPassword()).build())))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.serverIp").value(serverConfig.getServerIp()))
				.andExpect(jsonPath("$.port").value(serverConfig.getPort()))
				.andExpect(jsonPath("$.password").value(serverConfig.getPassword()));

		assertServerConfig(serverConfig.getServerIp(), serverConfig.getPort(), serverConfig.getPassword());
	}

	@Test
	void postServerConfigWithExistingDifferentCase() throws Exception {
		final ServerConfig serverConfig = saveTestConfig(SERVER_IP.toLowerCase(Locale.ROOT), PORT, PASSWORD);

		mvc.perform(post(PATH).header("daa-api-auth-token", authToken)
						.contentType(APPLICATION_JSON)
						.content(OBJECT_MAPPER.writeValueAsString(ServerConfigPostDto.builder().serverIp(serverConfig.getServerIp().toUpperCase(Locale.ROOT)).port(serverConfig.getPort()).password(serverConfig.getPassword()).build())))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.serverIp").value(serverConfig.getServerIp()))
				.andExpect(jsonPath("$.port").value(serverConfig.getPort()))
				.andExpect(jsonPath("$.password").value(serverConfig.getPassword()));

		assertServerConfig(serverConfig.getServerIp(), serverConfig.getPort(), serverConfig.getPassword());
	}

	private void assertServerConfig(String serverIp, int port, String password) {
		assertThat(serverConfigRepository.findAll())
				.hasSize(1)
				.extracting(ServerConfig::getServerIp, ServerConfig::getPort, ServerConfig::getPassword)
				.containsExactly(tuple(serverIp, port, password));
	}

	@Test
	void putServerConfigActiveSecured() throws Exception {
		mvc.perform(put(PATH + "/1/active"))
				.andExpect(status().isForbidden());
	}

	@Test
	void putServerConfigActiveWithoutConfig() throws Exception {
		mvc.perform(put(PATH + "/999/active").header("daa-api-auth-token", authToken))
				.andExpect(status().isNotFound());
	}

	@Test
	void putServerConfigActive() throws Exception {
		final ServerConfig serverConfig = saveTestConfig();
		final ServerConfig nextServerConfig = saveTestConfig(true);
		assertNextServerConfig(nextServerConfig);

		mvc.perform(put(PATH + "/" + serverConfig.getId()+ "/active").header("daa-api-auth-token", authToken))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.serverIp").value(serverConfig.getServerIp()))
				.andExpect(jsonPath("$.port").value(serverConfig.getPort()))
				.andExpect(jsonPath("$.password").value(serverConfig.getPassword()));
		assertNextServerConfig(serverConfig);
	}
}