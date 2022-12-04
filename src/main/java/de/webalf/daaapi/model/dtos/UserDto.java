package de.webalf.daaapi.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

/**
 * @author Alf
 * @since 10.10.2021
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Builder
public class UserDto {
	@Schema(requiredMode = REQUIRED, name = "steam id")
	long id;
	@Schema(requiredMode = REQUIRED)
	String mods;
}
