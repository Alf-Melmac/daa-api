package de.webalf.daaapi.model.dtos;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Alf
 * @since 09.10.2021
 */
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@SuperBuilder
public class ServerConfigDto extends AbstractIdEntityDto {
	@NotBlank
	String serverIp;

	@Min(value = 0, message = "Negative port is not allowed.")
	int port;

	String password;
}
