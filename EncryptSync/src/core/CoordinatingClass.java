package core;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.InvalidCipherTextException;

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
	 * @throws JAXBException 
	 */
	public CoordinatingClass() throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, JAXBException {
		userListFile = new File("users.xml");//option to change this via the UI
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
	 * @throws NoSuchProviderException 
	 */
	public User addNewUser(String name, String in, String out, String passwordInput) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException, NoSuchProviderException{
		User newUser = new User(name, in , out);
		for(User user : users){
			if(user.name.equals(newUser.name)){
				System.out.println("User with same name already exists.");
				return newUser;
			}
			
		}
		if(newUser.referenceFile.exists()){
			try {
				verifyKey(newUser, passwordInput);
			} catch (InvalidCipherTextException e) {
			  System.out.println("Refrence File has been corrupted or modified");
				e.printStackTrace();
			} //Should this be implemented in the UI? Hmm easy enough to remove later.
		if(newUser.keyVerified){
			System.out.println("Succesfull login, password correct");
		}
		else {
			System.out.println("Unsuccesfull login password incorrect.");
		}
		
		}
		else {
			
			createKey(newUser, passwordInput);
			try {
				verifyKey(newUser, passwordInput);
			} catch (InvalidCipherTextException e) {
				System.out.println("Refrence File has been corrupted or modified");
				e.printStackTrace();
			}
			
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
	/** Reads in the text file that contains a XML list of all known users
	 * 
	 * @param usersFileLocation Where the config file is stored
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws JAXBException 
	 */
	private void readInUserList(File usersFileLocation) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, JAXBException{
		/*File usersFile  = usersFileLocation;//use correct directory
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
		*/
		
		String Config_XML = userListFile.toString();
		JAXBContext context = JAXBContext.newInstance(XMLUserList.class);
		Unmarshaller um = context.createUnmarshaller();
		XMLUserList  readInXMLUserList = (XMLUserList) um.unmarshal(new FileReader(Config_XML));
		for(XMLUser  xmlUser : readInXMLUserList.getUserList()){
			addNewUser(xmlUser.name, xmlUser.unencryptedDirectory, xmlUser.encryptedDirectory);
			for( User user : users){
				if(user.getName().equals(xmlUser.getName())){
					user.setMostRecentEncryptedFileNames(getLastKnownEncryptedFiles(user));
				}
			}
		}
		
	}
	/** Saves the users to file in a XML format
	 * 
	 * @throws IOException
	 * @throws JAXBException 
	 */
	public void saveUserListToFile() throws IOException, JAXBException{
		/*final Charset ENCODING = StandardCharsets.UTF_8;
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
		ow.close();*/
		
		String Config_XML = userListFile.toString();
		
		XMLUserList userList = new XMLUserList();
		ArrayList<XMLUser> userArray = new ArrayList<XMLUser>();
		//XMLUser buffUser = new XMLUser();
		for(User user : users){
			XMLUser buffUser = new XMLUser();
			buffUser.setConfigDirectory(user.getConfigDirectory().toString());
			buffUser.setUnencryptedDirectory(user.getUnencryptedDirectory().getLocation().toString());
			buffUser.setEncryptedDirectory(user.getEncryptedDirectory().getLocation().toString());
			buffUser.setName(user.getName());
			user.getEncryptedDirectory().pollDirectory();
			
			FileNameList NameList = new FileNameList();
			user.setMostRecentEncryptedFileNames(updateKnownEncryptedFileList(user));
			NameList.setFileNames(user.getMostRecentEncryptedFileNames());
			buffUser.setFileNameList(NameList);
			userArray.add(buffUser);
		}
		userList.setUserList(userArray);
		
		 // create JAXB context and instantiate marshaller
	    JAXBContext context = JAXBContext.newInstance(XMLUserList.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
	    
	 // Write to System.out
	    m.marshal(userList, System.out);
	    
	    // Write to File
	    m.marshal(userList, new File(Config_XML));
	    
	}
	
	/** removes user from users collection. -  
	 * 
	 * @param userName
	 */
	//TODO: call save to file in order to remove user permanently?  
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
	/** generates a password key and assigns it to the user then generates a reference file so the password can be checked later
	 * 
	 * @param user user to have key generated
	 * @param password ditto
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchProviderException 
	 */
	public void createKey(User user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchProviderException{
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
	 * @throws NoSuchProviderException 
	 */
	public boolean loginUser(String userName, String passwordString) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException, NoSuchProviderException{
	for(User user : users){
		if(user.name.equals(userName)){
			try {
				if(verifyKey(user, passwordString)) return true;
				else System.out.println("Password Incorrect! Please try again.");
			} catch (InvalidCipherTextException e) {
				System.out.println("Refrence File has been corrupted or modified");
				e.printStackTrace();
			}
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
	 * @throws NoSuchProviderException 
	 */
	public boolean loginUser(User user, String passwordString) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, IOException, NoSuchProviderException{
				try {
					if(verifyKey(user, passwordString)) return true;
					else System.out.println("Password Incorrect! Please try again.");
				} catch (InvalidCipherTextException e) {
					System.out.println("Refrence File has been corrupted or modified");
					e.printStackTrace();
				}
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
	 * @throws NoSuchProviderException 
	 * @throws InvalidCipherTextException 
	 */
	
	public boolean verifyKey(User user, String passwordInput) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException, InvalidCipherTextException{
		user.generatePasswordKey(passwordInput);
		decryptor.decryptChkFile(user);
		File chkFile  = new File("tempfile.txt");
		Scanner inputUsers = new Scanner(chkFile);
		if(inputUsers.next().equals("abcdefghijklmnopqrstuvwxyz123")){
			inputUsers.close();
			user.setKeyVerified(true);
			return true;
		}
		inputUsers.close();
		user.setKeyVerified(false);
		return false;
		
	}
	
	
	public ArrayList<String> getLastKnownEncryptedFiles(User user) throws JAXBException, FileNotFoundException{
		String Config_XML = userListFile.toString();
		JAXBContext context = JAXBContext.newInstance(XMLUserList.class);
		Unmarshaller um = context.createUnmarshaller();
		XMLUserList  readInXMLUserList = (XMLUserList) um.unmarshal(new FileReader(Config_XML));
		for(XMLUser  xmlUser : readInXMLUserList.getUserList()){
			if(user.getName().equals(xmlUser.getName()) ){
				if(xmlUser.getFileNameList() != null){
					return xmlUser.getFileNameList().getFileNames();
				}
				
			}
		  
		}
		return new ArrayList<>();
	}
	
	public ArrayList<String> updateKnownEncryptedFileList(User user){
		ArrayList<String> knownEncryptedFileList = new ArrayList<>();
		for(File file : user.getEncryptedDirectory().getContainedFiles()){
			if(!file.isDirectory()){//TODO itereate throught 
				File buffFile = new File(user.getConfigDirectory() + "\\" + file.getName() +  user.name + "_iv" );
				if(buffFile.getAbsoluteFile().exists()){
					knownEncryptedFileList.add(file.getName());
				}
				
				
				
				
			}
		}
		return knownEncryptedFileList;
	}
	
	public void encryptFiles(User userProfile) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, NoSuchProviderException{
		getEncryptor().encryptFile(userProfile);
	}
	
	public void decryptFiles(User userProfile) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, NoSuchProviderException, BadPaddingException, IllegalBlockSizeException{
		
			getDecryptor().decryptFilesWithIVPresent(userProfile);
		
	}
	
	/** runs the directory method pollDirectory()
	 * 
	 * @param user
	 */
	public void updateFilesInDirectories(User user){
		
		user.getEncryptedDirectory().pollDirectory();
		user.getUnencryptedDirectory().pollDirectory();
	}
	
}
