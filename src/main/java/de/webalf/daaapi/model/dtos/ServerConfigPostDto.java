package de.webalf.daaapi.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Alf
 * @since 09.10.2021
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
@Builder
public class ServerConfigPostDto {
	@NotBlank
	@ApiModelProperty(required = true, example = "arma.deutsche-arma-allianz.de", position = 0)
	String serverIp;

	@Min(value = 0, message = "Negative port is not allowed.")
	@ApiModelProperty(required = true, allowableValues = "range[0, infinity]", example = "2302", position = 1)
	int port;

	@ApiModelProperty(allowEmptyValue = true, example = "GanzSicheresPasswort", position = 2)
	String password;
}
