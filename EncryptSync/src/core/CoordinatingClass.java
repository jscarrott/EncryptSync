package core;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import javax.crypto.NoSuchPaddingException;

public class CoordinatingClass {

	private Collection<User> users;
	private Encryptor encryptor;
	private Decryptor decryptor;
	private File userListFile;
	/**Class to abstract the implementation of the core functionality away from the implementation of the user interface. Contains
	 * several methods to correctly implelment inputs from the ui.
	 * most methods self explanatory
	 * @throws IOException 
	 * @throws InvalidKeyException shouldn't happen unless the sha hash function fails to generate correct key format.
	 * @throws NoSuchAlgorithmException algorithm hardcoded so unlikely to happen
	 * @throws InvalidKeySpecException again hardcoded
	 * @throws NoSuchPaddingException ditto
	 * @throws InvalidAlgorithmParameterException
	 */
	public CoordinatingClass() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException {
		userListFile = new File("users.conf");//option to change this via the UI
		users = new ArrayList<User>();
		encryptor = new Encryptor();
		decryptor = new Decryptor();
		if(userListFile.exists()){
			readInUserList(userListFile);
		}
		
	}
	public List<User> getUsers(){
		List<User> cUsers = new ArrayList<User>(users);
		return cUsers;
	}
	/**Checks if user name already exists, then checks if user has already created a reference file and if not creates one with the new password
	 * 
	 * @param name
	 * @param in
	 * @param out
	 * @param passwordInput
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 */
	public User addNewUser(String name, String in, String out, String passwordInput) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException{
		User newUser = new User(name, in , out);
		for(User user : users){
			if(user.name.equals(newUser.name)){
				System.out.println("User with same name already exists.");
				return newUser;
			}
			
		}
		if(newUser.referenceFile.exists()){
			verifyKey(newUser, passwordInput);//Should this be implemented in the UI? Hmm easy enough to remove later.
		if(newUser.keyVerified == true){
			System.out.println("Succesfull login, password correct");
		}
		else {
			System.out.println("Unsuccesfull login password incorrect.");
		}
		
		}
		else {
			
			createKey(newUser, passwordInput);
			verifyKey(newUser, passwordInput);
			
		}
		users.add(newUser);
		return newUser;
	}
	/**adds new user but without logging the user in
	 * 
	 * @param name
	 * @param in
	 * @param out
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 */
	public User addNewUser(String name, String in, String out)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IOException {
		User newUser = new User(name, in, out);
		if (users.size() >= 0) {
			for (User user : users) {
				if (user.name.equals(newUser.name)) {
					System.out.println("User with same name already exists.");
					return newUser;
				}

			}
		}
		users.add(newUser);
		return newUser;
	}
	
	private void readInUserList(File usersFileLocation) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException{
		File usersFile  = usersFileLocation;//use correct directory
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(usersFile));
		Scanner inputUsers = new Scanner(is);
		inputUsers.useDelimiter(",");
		while(inputUsers.hasNextLine()){
			try {
				addNewUser(inputUsers.next().replaceAll("\\r\\n", "") , inputUsers.next() , inputUsers.next() );
			} catch (Exception e) {
				// TODO: handle exception An exception  caused by the scanner trying to read nothing is thrown, unsure how to deal with it.
			}
			
			
		}
		is.close();
		inputUsers.close();
	}
	
	public void saveUserListToFile() throws IOException{
		final Charset ENCODING = StandardCharsets.UTF_8;
		File usersFile  = userListFile;
		BufferedWriter ow = Files.newBufferedWriter(usersFile.toPath(), ENCODING);
		boolean first = true;
		for(User user : users){
			if(!first) ow.newLine();
			else first = false;
			ow.write(user.getName() + ",");
			ow.write(user.getUnencryptedDirectory().getLocation().toString() + ",");
			ow.write(user.getEncryptedDirectory().getLocation().toString() + "," );
		}
		ow.close();
	}
	public void removeUser(String userName){
		int counter = 0, index = 0;
		for(User user : users){
			
			if(userName.equals(user.getName())){
			 index = counter;
			}
			counter++;
		}
		users.remove(((ArrayList<User>) users).get(index));
	}
	
	public void createKey(User user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException{
		user.generatePasswordKey(password);
		encryptor.encryptChkFile(user, "abcdefghijklmnopqrstuvwxyz123");
	}
	
	/**verifies the inputted key for a user given the user name
	 * 
	 * @param userName
	 * @param passwordString
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 */
	public boolean loginUser(String userName, String passwordString) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException{
	for(User user : users){
		if(user.name.equals(userName)){
			if(verifyKey(user, passwordString)) return true;
			else System.out.println("Password Incorrect! Please try again.");
		}
	}
	System.out.println("No such User. Please try again.");
    return false;
	}
	/**verifies the inputted key for a user object
	 * 
	 * @param user
	 * @param passwordString
	 * @return bollean value to enable success checking
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 */
	public boolean loginUser(User user, String passwordString) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException{
				if(verifyKey(user, passwordString)) return true;
				else System.out.println("Password Incorrect! Please try again.");
	    return false;
		}

	public Encryptor getEncryptor() {
		return encryptor;
	}

	public Decryptor getDecryptor() {
		return decryptor;
	}
	
	/** Verifies the key by checking against a pre encrypted file.
	 * 
	 * @param user
	 * @param passwordInput
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws InvalidKeyException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 */
	
	public boolean verifyKey(User user, String passwordInput) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException{
		user.generatePasswordKey(passwordInput);
		decryptor.decryptChkFile(user);
		File chkFile  = new File("tempfile.txt");
		//DataInputStream is = new DataInputStream(new FileInputStream(chkFile));
		Scanner inputUsers = new Scanner(chkFile);
		if(inputUsers.next().equals("abcdefghijklmnop")){
			inputUsers.close();
			user.setKeyVerified(true);
			return true;
		}
		inputUsers.close();
		user.setKeyVerified(false);
		return false;
		
	}
	
}
