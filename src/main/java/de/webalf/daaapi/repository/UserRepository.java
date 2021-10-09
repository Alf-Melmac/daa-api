package de.webalf.daaapi.repository;

import de.webalf.daaapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alf
 * @since 10.10.2021
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
