package com.flytrack;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlytrackApplicationTests {
	@MockBean
    private EmailService emailService;
	@Test
	void contextLoads() {
	}

}
