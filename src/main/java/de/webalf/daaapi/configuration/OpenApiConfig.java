package de.webalf.daaapi.configuration;

import de.webalf.daaapi.configuration.authentication.TokenAuthFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OpenApiConfig {
	private final TokenAuthFilter tokenAuthFilter;
	private static final String SECURITY_SCHEME_KEY = "API Auth Token";

	@Bean
	public OpenAPI api() {
		final String authTokenName = tokenAuthFilter.getAuthTokenName();
		return new OpenAPI()
				.info(new Info()
						.title("DAA Web API")
						.description("API für die Deutsche Arma Allianz")
						.version("v1")
						.contact(new io.swagger.v3.oas.models.info.Contact()
								.name("Alf")
								.email("daa-api@webalf.de"))
						.license(new License()
								.name("MIT License")
								.url("https://github.com/Alf-Melmac/daa-api/blob/master/LICENSE"))
				)
				.components(new Components()
						.addSecuritySchemes(SECURITY_SCHEME_KEY, new SecurityScheme().name(authTokenName).type(SecurityScheme.Type.APIKEY).in(HEADER)))
				.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_KEY));
	}

	@Bean
	public OperationCustomizer customizeOperation() {
		return (operation, handlerMethod) ->
				operation.responses(operation.getResponses()
						.addApiResponse("403", new ApiResponse().description("Forbidden")));
	}
}
