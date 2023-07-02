package de.webalf.daaapi.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alf
 * @since 02.07.2023
 */
class ServerConfigTest {
	private static final String IP = "ip";
	private static final int PORT = 1234;
	private static final String PASSWORD = "password";

	@ParameterizedTest
	@MethodSource
	void testToString(String password, String expected) {
		assertThat(ServerConfig.builder().serverIp(IP).port(PORT).password(password).build()).hasToString(expected);
	}

	private static Stream<Arguments> testToString() {
		final String standard = IP + ":" + PORT;
		return Stream.of(
				Arguments.of(null, standard),
				Arguments.of("", standard),
				Arguments.of(" ", standard),
				Arguments.of("  ", standard),
				Arguments.of(PASSWORD, standard + ":" + PASSWORD)
		);
	}
}