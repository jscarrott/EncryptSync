package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

import org.junit.Before;
import org.junit.Test;

import core.Decryptor;
import core.Encryptor;
import core.User;

public class EncryptorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEncryptFile() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException {
		Encryptor testE = new Encryptor();
		Decryptor testD = new Decryptor();
		User testUser = new User("John", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testUser.generatePasswordKey("test123");
		testE.encryptFile(testUser);
		testD.decryptFile(testUser);
	}

	@Test
	public void testGenerateCipher() {
		fail("Not yet implemented");
	}

}
