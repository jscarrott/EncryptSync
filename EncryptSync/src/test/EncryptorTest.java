package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.InvalidCipherTextException;
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
	public void testEncryptFile() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchProviderException {
		Encryptor testE = new Encryptor();
		Decryptor testD = new Decryptor();
		User testUser = new User("John", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testUser.generatePasswordKey("test123");
		testE.encryptFile(testUser);
		try {
			testD.decryptFile(testUser);
		} catch (CryptoException e) {
			System.out.println("Refrence File has been corrupted or modified");
			e.printStackTrace();
		}
	}

	@Test
	public void testGenerateCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException {
		Encryptor testE = new Encryptor();
		testE.generateCipher();
	}

}
