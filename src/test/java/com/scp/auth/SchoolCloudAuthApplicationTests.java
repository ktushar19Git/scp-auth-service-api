package com.scp.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("!dev")
class SchoolCloudAuthApplicationTests {

	@Test
	void contextLoads() {
	}

}
