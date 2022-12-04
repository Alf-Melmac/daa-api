package de.webalf.daaapi.model.dtos;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Alf
 * @since 09.10.2021
 */
@MappedSuperclass
@Data
@RequiredArgsConstructor
@SuperBuilder
public abstract class AbstractIdEntityDto {
	@Id
	@NotNull
	protected long id;
}
