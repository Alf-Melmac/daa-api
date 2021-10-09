package de.webalf.daaapi.assembler;

import de.webalf.daaapi.model.User;
import de.webalf.daaapi.model.dtos.UserDto;
import lombok.experimental.UtilityClass;

/**
 * @author Alf
 * @since 10.10.2021
 */
@UtilityClass
public final class UserAssembler {
	public static UserDto toDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.mods(user.getMods())
				.build();
	}
}
