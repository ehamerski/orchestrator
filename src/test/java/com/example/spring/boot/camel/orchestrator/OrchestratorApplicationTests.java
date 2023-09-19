package com.example.spring.boot.camel.orchestrator;

import org.apache.logging.log4j.util.Strings;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrchestratorApplicationTests {

	@Test
	void contextLoads() {
		OrchestratorApplication.main(new String[0]);
		Assertions.assertTrue(Strings.isNotBlank(System.getProperty(OrchestratorApplication.APPLICATION_ID_PROPERTY_NAME)));
	}

}
