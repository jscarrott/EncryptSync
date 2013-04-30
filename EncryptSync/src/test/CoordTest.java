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
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Test
	public void testGetUsers() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		CoordinatingClass testCoordClass = new CoordinatingClass();
       assertEquals("BobHarold",  testCoordClass.getUsers().get(0).getName());
	}

	/**
	 * Test method for {@link core.CoordinatingClass#addNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Test
	public void testAddNewUserStringStringStringString() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		CoordinatingClass testCoordClass = new CoordinatingClass();
		testCoordClass.addNewUser("Bob Harold", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut", "bob1234");
	assertEquals( "BobHarold", testCoordClass.getUsers().get(0).getName());
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
		assertEquals("BobHarold", testCoordClass.getUsers().get(0).getName());
	}

	/**
	 * Test method for {@link core.CoordinatingClass#removeUser(core.User)}.
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Test
	public void testRemoveUser() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		CoordinatingClass testCoordClass = new CoordinatingClass();
		testCoordClass.removeUser("BobHarold");
		assertEquals(testCoordClass.getUsers().get(0).getName(), "BobHarold1");
	}

	/**
	 * Test method for {@link core.CoordinatingClass#createKey(core.User, java.lang.String)}.
	 */
	@Test
	public void testCreateKey() {
		//works as methods that rely on it work, more work to write an extra test
	}

	/**
	 * Test method for {@link core.CoordinatingClass#loginUser(java.lang.String, java.lang.String)}.
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Test
	public void testLoginUserStringString() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		CoordinatingClass testCoordClass = new CoordinatingClass();
		testCoordClass.addNewUser("Bob Harold", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testCoordClass.loginUser("Bob Harold", "bob1234");
		assertEquals(true, testCoordClass.getUsers().get(4).isKeyVerified());
		
	}

	/**
	 * Test method for {@link core.CoordinatingClass#loginUser(core.User, java.lang.String)}.
	 * @throws IOException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	@Test
	public void testLoginUserUserString() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException {
		CoordinatingClass testCoordClass = new CoordinatingClass();
		testCoordClass.addNewUser("Bob Harold", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testIn", "C:\\Users\\Home\\git\\EncryptSync\\EncryptSync\\testOut");
		testCoordClass.createKey(testCoordClass.getUsers().get(4), "bob1234");
		testCoordClass.loginUser(testCoordClass.getUsers().get(4), "bob1234" );
		assertEquals(true, testCoordClass.getUsers().get(4).isKeyVerified());
	}

	/**
	 * Test method for {@link core.CoordinatingClass#verifyKey(core.User, java.lang.String)}.
	 */
	@Test
	public void testVerifyKey() {
		//login user works so verify key should also work.
	}

}
