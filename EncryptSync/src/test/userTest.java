package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.User;

public class userTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		User testUser = new User("John", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync");
		assertEquals("John", testUser.getName());
		assertEquals( "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync", testUser.getUnencryptedDirectory().getLocation().toString());
		assertEquals( "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync", testUser.getEncryptedDirectory().getLocation().toString());	
		
	
	}

}
