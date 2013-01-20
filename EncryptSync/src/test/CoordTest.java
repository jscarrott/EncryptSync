/**
 * 
 */
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

import core.CoordinatingClass;

/**
 * @author John Scarrott
 *
 */
public class CoordTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		//CoordinatingClass testCoordClass = new CoordinatingClass();
		
	}

	/**
	 * Test method for {@link core.CoordinatingClass#getUsers()}.
	 */
	@Test
	public void testGetUsers() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CoordinatingClass#addNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddNewUserStringStringStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CoordinatingClass#addNewUser(java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Test
	public void testAddNewUserStringStringString() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		CoordinatingClass testCoordClass = new CoordinatingClass();
		testCoordClass.addNewUser("Bob Harold", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
	assertEquals( "BobHarold", testCoordClass.getUsers().get(0).getName());
		
	}

	/**
	 * Test method for {@link core.CoordinatingClass#saveUserListToFile(java.io.File)}.
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Test
	public void testSaveUserListToFile() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		CoordinatingClass testCoordClass = new CoordinatingClass();
		testCoordClass.addNewUser("BobHarold", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testCoordClass.addNewUser("BobHarold1", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testCoordClass.addNewUser("BobHarold2", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testCoordClass.addNewUser("BobHarold3", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testCoordClass.saveUserListToFile();
		assertEquals("bobharold", testCoordClass.getUsers().get(0).getName());
	}

	/**
	 * Test method for {@link core.CoordinatingClass#removeUser(core.User)}.
	 */
	@Test
	public void testRemoveUser() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CoordinatingClass#createKey(core.User, java.lang.String)}.
	 */
	@Test
	public void testCreateKey() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CoordinatingClass#loginUser(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLoginUserStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CoordinatingClass#loginUser(core.User, java.lang.String)}.
	 */
	@Test
	public void testLoginUserUserString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link core.CoordinatingClass#verifyKey(core.User, java.lang.String)}.
	 */
	@Test
	public void testVerifyKey() {
		fail("Not yet implemented");
	}

}
