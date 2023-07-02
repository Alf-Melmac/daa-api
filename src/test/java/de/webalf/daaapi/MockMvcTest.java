package de.webalf.daaapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alf
 * @since 02.07.2023
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class MockMvcTest {
	@Autowired
	protected MockMvc mvc;

	@Value("${daa-api.auth.token}")
	protected String authToken;
}
