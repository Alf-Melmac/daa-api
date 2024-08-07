package de.webalf.daaapi.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Alf
 * @since 10.10.2021
 */
@Entity
@Table(name = "arma_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
	@Id
	@Column(name = "id")
	//Workaround to ignore generated values
	private long id;

	@Column(name = "mods", columnDefinition = "text")
	@Setter
	private String mods;
}
