package de.webalf.daaapi.controller;

import de.webalf.daaapi.assembler.UserAssembler;
import de.webalf.daaapi.model.dtos.UserDto;
import de.webalf.daaapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static de.webalf.daaapi.controller.Urls.API;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

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

	@PutMapping(value = "{userId}", consumes = TEXT_PLAIN_VALUE, produces = APPLICATION_JSON_VALUE)
	@Operation(summary = "Put user mods", description = "Creates an user or updates the mods of an existing user found using the userId.")
	public UserDto putUserMods(@PathVariable long userId, @RequestBody String mods) {
		log.trace("putUserMods");
		return UserAssembler.toDto(userService.updateModsOrCreateUser(userId, mods));
	}
}
