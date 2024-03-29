package de.webalf.daaapi.exception;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessRuntimeException extends RuntimeException {
	@Builder
	private BusinessRuntimeException(@NotEmpty String title, Throwable cause) {
		super(title, cause);
	}
}
