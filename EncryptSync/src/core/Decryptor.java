package core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Decryptor {

	/**
	 * Take a user and encrypts everything in the unecrpyted directory to
	 * the encrypted directory and
	 * looks for preexisting initialisation
	 * @throws NoSuchProviderException 
	 * */
	
	public Decryptor(){
		Security.addProvider(new BouncyCastleProvider());
	}
	public void decryptFile(User user) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, NoSuchProviderException, InvalidCipherTextException, CryptoException{
		File ivread = new File(user.configDirectory + "\\" + user.name + ".iv");
		
		FileInputStream in = new FileInputStream(ivread);
		byte[] iv = new byte[(int) ivread.length()];
		in.read(iv);
		in.close();
	
		if(!(user.encryptedDirectory.containedFiles.size() == 0)){
			for(int counter = 0; counter < user.encryptedDirectory.containedFiles.size(); counter++){
				if(!user.encryptedDirectory.containedFiles
						.get(counter).isDirectory()){
					Cipher cipher = generateCipher();
					cipher.init(Cipher.DECRYPT_MODE, user.passwordKey, new IvParameterSpec(iv));
					BufferedInputStream is = new BufferedInputStream(new FileInputStream(user.encryptedDirectory.containedFiles.get(counter)));
					CipherOutputStream os = new CipherOutputStream(new FileOutputStream(user.unencryptedDirectory.location.toAbsolutePath() +  "\\"+ user.encryptedDirectory.containedFiles.get(counter).getName() + ""), cipher);
					try {
						copy(is,os);
					} catch (RuntimeException e){
						System.out.println("eeee!");
					}
					try{
						cipher.doFinal();
						is.close();
					}
					catch (RuntimeException e){
						System.out.println("eeee!");
					} catch (Exception e) {
						System.out.println("eeee!");
					}
					os.close();
				}
			
			}
		}
		
		
	}
	/** Method used to decrypt the string file with the iv and accepted password key
	 * 
	 * @param user the user that contains the necessary directory objects
	 * @throws NoSuchProviderException 
	 */
	public void decryptChkFile(User user) throws NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchProviderException, InvalidCipherTextException{
		File chkFile = user.referenceFile;
		File ivread = new File(user.configDirectory + "\\" + user.name + ".iv");
		Cipher cipher = generateCipher();
		FileInputStream in = new FileInputStream(ivread);
		byte[] iv = new byte[(int) ivread.length()];
		in.read(iv);
		in.close();
		cipher.init(Cipher.DECRYPT_MODE, user.passwordKey, new IvParameterSpec(iv));
		boolean exists = ivread.exists();
		if (!exists){
			System.out.println("Cipher config file not found!");
		}
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(chkFile + ".out"));
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(new File("tempfile.txt")), cipher);
		try {
			copy(is,os);
		} catch (Exception e){
			System.out.println("eeee!");
		}
		is.close();
		os.close();
	}
	
	 private static void copy(InputStream is, OutputStream os) throws IOException {
		    int i;
		    byte[] b = new byte[1024];
		    while((i=is.read(b))!=-1) {
		        os.write(b, 0, i);
		    }

		}
	
	Cipher generateCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException{
		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
		return cipher;
	}
	
}
