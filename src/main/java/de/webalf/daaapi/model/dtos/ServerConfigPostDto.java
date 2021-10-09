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
	@ApiModelProperty(example = "arma.deutsche-arma-allianz.de")
	String serverIp;

	@Min(value = 0, message = "Negative port is not allowed.")
	@ApiModelProperty(example = "2302")
	int port;

	@ApiModelProperty(example = "GanzSicheresPasswort")
	String password;
}
