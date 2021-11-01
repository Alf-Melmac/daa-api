package de.webalf.daaapi.model.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author Alf
 * @since 09.10.2021
 */
@EqualsAndHashCode(callSuper = true)
@Value
@SuperBuilder
public class ServerConfigDto extends AbstractIdEntityDto {
	@NotBlank
	@ApiModelProperty(required = true, example = "arma.deutsche-arma-allianz.de", position = 1)
	String serverIp;

	@Min(value = 0, message = "Negative port is not allowed.")
	@ApiModelProperty(required = true, allowableValues = "range[0, infinity]", example = "2302", position = 2)
	int port;

	@ApiModelProperty(allowEmptyValue = true, example = "GanzSicheresPasswort", position = 3)
	String password;
}
