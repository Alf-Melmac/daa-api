package de.webalf.daaapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Alf
 * @since 09.10.2021
 */
@MappedSuperclass
@Getter
@RequiredArgsConstructor
@SuperBuilder
public abstract class AbstractIdEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	protected long id;
}
