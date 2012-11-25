package core;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

public class Encryptor {
	
	public void encryptFile(User user) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IOException{
		File ivread = new File(user.name + ".iv");
		boolean exists = ivread.exists();
		Cipher cipher = generateCipher();
		if(exists){
			FileInputStream in = new FileInputStream(ivread);
			byte[] iv = new byte[(int) ivread.length()];
			in.read(iv);
			cipher.init(Cipher.ENCRYPT_MODE, user.passwordKey, new IvParameterSpec(iv));
			in.close();
		}
		else{
			cipher.init(Cipher.ENCRYPT_MODE, user.passwordKey);
			byte[] iv = cipher.getIV();
			FileOutputStream ivout = new FileOutputStream(user.name + ".iv");
			ivout.write(iv);
			ivout.close();
		}
		for(int counter = 0; counter < user.inputDirectory.containedFiles.size(); counter++){
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(user.inputDirectory.containedFiles.get(counter)));
		CipherOutputStream os = new CipherOutputStream(new FileOutputStream(user.outputDirectory.location + user.inputDirectory.containedFiles.get(counter).getName() + ""), cipher);
		copy(is,os);
		is.close();
		os.close();
		}
		
	}
	
	private static void copy(InputStream is, OutputStream os) throws IOException {
		int i;
		byte[] b = new byte[1024];
		while((i=is.read(b))!=-1) {
			os.write(b, 0, i);
		}
	}
	
	Cipher generateCipher() throws NoSuchAlgorithmException, NoSuchPaddingException{
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		return cipher;
	}

}
