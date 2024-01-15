package com.phincon.microservice.crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		assertThrows(IllegalArgumentException.class, () -> Application.main(null));
	}

}
