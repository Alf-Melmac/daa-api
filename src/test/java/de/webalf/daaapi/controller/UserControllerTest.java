package de.webalf.daaapi.controller;

import de.webalf.daaapi.MockMvcTest;
import de.webalf.daaapi.model.User;
import de.webalf.daaapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Alf
 * @since 02.07.2023
 */
class UserControllerTest extends MockMvcTest {
	public static final String PATH = "/api/v1/users";

	@Autowired
	private UserRepository userRepository;

	@Test
	void putUserModsSecured() throws Exception {
		mvc.perform(put(PATH + "/1"))
				.andExpect(status().isForbidden());
	}

	@ParameterizedTest
	@EmptySource
	void putUserModsRejectsEmptyMods(String content) throws Exception {
		final long id = new Random().nextLong();
		mvc.perform(put(PATH + "/" + id).header("daa-api-auth-token", authToken)
						.contentType(TEXT_PLAIN)
						.content(content))
				.andExpect(status().isBadRequest());
	}

	@Test
	void putUserMods() throws Exception {
		final long id = new Random().nextLong();
		final String content = "mod1,mod2";
		mvc.perform(put(PATH + "/" + id).header("daa-api-auth-token", authToken)
						.contentType(TEXT_PLAIN)
						.content(content))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(id))
				.andExpect(jsonPath("$.mods").value(content));
	}

	@Test
	void putUserModsUpdates() throws Exception {
		final User user = User.builder().mods("old mods").build();
		userRepository.save(user);

		final String newContent = "mod1,mod2";

		mvc.perform(put(PATH + "/" + user.getId()).header("daa-api-auth-token", authToken)
						.contentType(TEXT_PLAIN)
						.content(newContent))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(user.getId()))
				.andExpect(jsonPath("$.mods").value(newContent));

		assertThat(userRepository.findById(user.getId())).get().extracting(User::getMods).isEqualTo(newContent);
	}
}