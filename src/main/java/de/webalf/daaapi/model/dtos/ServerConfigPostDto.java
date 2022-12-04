package de.webalf.daaapi.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * @author Alf
 * @since 09.10.2021
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Builder
public class ServerConfigPostDto {
	@NotBlank
	@Schema(requiredMode = REQUIRED, name = "server ip", example = "127.0.0.1")
	String serverIp;

	@Min(value = 0, message = "Negative port is not allowed.")
	@Schema(requiredMode = REQUIRED, minimum = "0", maximum = "65535", example = "2302")
	int port;

	@Schema(nullable = true, example = "GanzSicheresPasswort")
	String password;
}
