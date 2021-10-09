package de.webalf.daaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Alf
 * @since 09.10.2021
 */
@SpringBootApplication
@EnableSwagger2
public class DaaApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaaApiApplication.class, args);
	}

}
