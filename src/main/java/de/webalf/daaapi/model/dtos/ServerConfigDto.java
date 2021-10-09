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
	@ApiModelProperty(example = "arma.deutsche-arma-allianz.de")
	String serverIp;

	@Min(value = 0, message = "Negative port is not allowed.")
	@ApiModelProperty(example = "2302")
	int port;

	@ApiModelProperty(example = "GanzSicheresPasswort")
	String password;
}
