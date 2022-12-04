package de.webalf.daaapi.service;

import de.webalf.daaapi.model.User;
import de.webalf.daaapi.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alf
 * @since 10.10.2021
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
	private final UserRepository userRepository;

	public User updateModsOrCreateUser(long userId, @NotBlank String mods) {
		final User user = userRepository.findById(userId)
				.orElseGet(() -> userRepository.save(User.builder().id(userId).build()));
		user.setMods(mods);
		return user;
	}
}
