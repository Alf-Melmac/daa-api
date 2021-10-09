package de.webalf.daaapi.model.dtos;

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
	long id;
	String mods;
}
