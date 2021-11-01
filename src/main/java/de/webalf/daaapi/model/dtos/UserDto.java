package de.webalf.daaapi.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * @author Alf
 * @since 10.10.2021
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Builder
public class UserDto {
	@ApiModelProperty(required = true, value = "steam id")
	long id;
	@ApiModelProperty(required = true)
	String mods;
}
