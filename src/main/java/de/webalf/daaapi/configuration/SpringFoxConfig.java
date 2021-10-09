package de.webalf.daaapi.configuration;

import de.webalf.daaapi.configuration.authentication.TokenAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

import static de.webalf.daaapi.controller.Urls.API;
import static springfox.documentation.spi.DocumentationType.OAS_30;

/**
 * @author Alf
 * @since 09.10.2021
 */
@Configuration
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SpringFoxConfig {
	private final TokenAuthFilter tokenAuthFilter;

	@Bean
	public Docket api() {
		final String authTokenName = tokenAuthFilter.getAuthTokenName();
		return new Docket(OAS_30)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.ant(API + "/**"))
				.build()
				.apiInfo(new ApiInfo("DAA Web API", "API für die Deutsche Arma Allianz", "v1", "urn:tos", new Contact("Alf", "https://webalf.de", "daa-api@webalf.de"), "Attribution-NonCommercial 4.0 International", "https://raw.githubusercontent.com/Alf-Melmac/daa-api/master/LICENSE", new ArrayList<>()))
				.useDefaultResponseMessages(false)
				.securitySchemes(List.of(new ApiKey(authTokenName, "daa-api.auth.token", "header")))
				.securityContexts(List.of(
						SecurityContext.builder()
								.securityReferences(List.of(new SecurityReference(authTokenName, List.of(new AuthorizationScope("global", "accessEverything")).toArray(new AuthorizationScope[1]))))
								.operationSelector(oc -> true)
								.build())
				);
	}
}
