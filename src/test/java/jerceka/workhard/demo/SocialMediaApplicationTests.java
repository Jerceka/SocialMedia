package jerceka.workhard.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocialMediaApplicationTests {
	@Autowired
	Ser service;
	@Test
	void checkLogin() {
	assertTrue(service.checkLogin("Ahmet", "pasword"));
	}
	@Test
	void checkFriendship() {
		assertTrue(service.checkFriendship(30, 31));
		assertFalse(service.checkFriendship(30, 30));
	}
}
