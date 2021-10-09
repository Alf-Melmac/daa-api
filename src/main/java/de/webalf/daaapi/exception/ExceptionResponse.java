package de.webalf.daaapi.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Getter
@Setter
@Builder
public class ExceptionResponse {
	private String errorMessage;
	private String requestedURI;
}
