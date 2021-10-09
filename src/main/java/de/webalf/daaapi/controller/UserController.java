package de.webalf.daaapi.controller;

import de.webalf.daaapi.assembler.UserAssembler;
import de.webalf.daaapi.model.dtos.UserDto;
import de.webalf.daaapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static de.webalf.daaapi.controller.Urls.API;

/**
 * @author Alf
 * @since 10.10.2021
 */
@RequestMapping(API + "/users")
@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {
	private final UserService userService;

	@PutMapping("{userId}")
	public UserDto putUserMods(@PathVariable long userId, @RequestBody String mods) {
		return UserAssembler.toDto(userService.updateModsOrCreateUser(userId, mods));
	}
}
