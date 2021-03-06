package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Encryptor {
	/**
	 * Take a user and encrypts everything in the unecrpyted directory to
	 * the encrypted directory and
	 * looks for preexisting initialisation vector else creates one 
	 * @param user 	the user that contains the necessary directory objects 
	 * @throws NoSuchProviderException */
	
	public Encryptor(){
		Security.addProvider(new BouncyCastleProvider());
	}
	public void encryptFile(User user) throws InvalidKeyException,
			InvalidAlgorithmParameterException, NoSuchAlgorithmException,
			NoSuchPaddingException, IOException, NoSuchProviderException {
		
		//File ivread = new File(user.configDirectory + "\\" + user.name + ".iv");
		
		Cipher cipher = generateCipher();
		/*if (ivread.exists()) {
			FileInputStream in = new FileInputStream(ivread);
			byte[] iv = new byte[(int) ivread.length()];
			in.read(iv);
			cipher.init(Cipher.ENCRYPT_MODE, user.passwordKey,
					new IvParameterSpec(iv));
			in.close();
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, user.passwordKey);
			byte[] iv = cipher.getIV();
			FileOutputStream ivout = new FileOutputStream(user.configDirectory + "\\" + user.name + ".iv");
			ivout.write(iv);
			ivout.close();
		}*/
		for (int counter = 0; counter < user.unencryptedDirectory.containedFiles
				.size(); counter++) {// Iterates through directory and ignores a file if it is a directory
			if(!user.unencryptedDirectory.containedFiles.get(counter).isDirectory()){
				cipher.init(Cipher.ENCRYPT_MODE, user.passwordKey);
				byte[] iv = cipher.getIV();
				FileOutputStream ivout = new FileOutputStream(user.configDirectory + "\\" + user.unencryptedDirectory.containedFiles.get(counter).toPath().getFileName() +  user.name + "_iv");
				System.out.println(user.unencryptedDirectory.containedFiles.get(counter).toPath().getFileName());
				ivout.write(iv);
				ivout.close();
				BufferedInputStream is = new BufferedInputStream(
						new FileInputStream(	user.unencryptedDirectory.containedFiles.get(counter)));
				CipherOutputStream os = new CipherOutputStream(	new FileOutputStream(	user.encryptedDirectory.location.toAbsolutePath()	+ "\\"
										+ user.unencryptedDirectory.containedFiles	.get(counter).getName() + ""), cipher);
				copy(is, os);
				is.close();
				os.close();
									}
			
		}

	}
/** Method used to encrypt the string file with the iv and accepted password key
 * 
 * @param user the user that contains the necessary directory objects
 * @param inputFile
 * @throws NoSuchAlgorithmException
 * @throws NoSuchPaddingException
 * @throws InvalidKeyException
 * @throws InvalidAlgorithmParameterException
 * @throws IOException
 * @throws NoSuchProviderException 
 */
	public void encryptChkFile(User user, String inputFile)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IOException, NoSuchProviderException {// same as above but for singular file, for creating
							// consistant files to check against
		File ivread = new File(user.name + "_iv");
		boolean exists = ivread.exists();
		Cipher cipher = generateCipher();
		if (exists) {
			FileInputStream in = new FileInputStream(ivread);
			byte[] iv = new byte[(int) ivread.length()];
			in.read(iv);
			cipher.init(Cipher.ENCRYPT_MODE, user.passwordKey,
					new IvParameterSpec(iv));
			in.close();
		} else {
			cipher.init(Cipher.ENCRYPT_MODE, user.passwordKey);
			byte[] iv = cipher.getIV();
			FileOutputStream ivout = new FileOutputStream(user.configDirectory + "\\" + user.name + "_iv");
			ivout.write(iv);
			ivout.close();
		}
		final Charset ENCODING = StandardCharsets.UTF_8;
		File usersFile = user.referenceFile;// possibly gets sent
															// to the wrong
															// directory, write
															// test to check
		BufferedWriter ow = Files.newBufferedWriter(usersFile.toPath(),//output stream is all wrong should be using cpher output stream/
				ENCODING);
		ow.write(inputFile);
		ow.close();
		
		int i = cipher.getBlockSize();
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(user.referenceFile));
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(user.referenceFile+".out"), cipher);
		copy(is, os);
		is.close();
		os.close();

		// TODO insert code to create then encrypt a simple phrase e.g. 1234567
	}
/** copies input stream to output stream bitwise
 * 
 * @param is inputstream 
 * @param os output stream
 * @throws IOException
 */
	 private static void copy(InputStream is, OutputStream os) throws IOException {
		    int i;
		    byte[] b = new byte[1024];
		    while((i=is.read(b))!=-1) {
		        os.write(b, 0, i);
		    }

		}
	
/** generates the cipher for this encryptor
 * 
 * @return
 * @throws NoSuchAlgorithmException
 * @throws NoSuchPaddingException
 * @throws NoSuchProviderException 
 */
	public Cipher generateCipher() throws NoSuchAlgorithmException,
			NoSuchPaddingException, NoSuchProviderException {
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
		return cipher;
	}

}
