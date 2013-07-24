package core;

import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

import javax.crypto.NoSuchPaddingException;

public class DirectoryWatcher {

	
	private WatchService watcher;
	private Path dir;
	private User currentUser;
	private Encryptor encryptor;

	public DirectoryWatcher(String directoryPath, User user, Encryptor encryptor) throws IOException {
		watcher = FileSystems.getDefault().newWatchService();
		FileSystem fs = FileSystems.getDefault();
		dir = fs.getPath(directoryPath);
		
		try {
		    WatchKey key = dir.register(watcher,  ENTRY_CREATE,	 ENTRY_DELETE,	 ENTRY_MODIFY);
		} catch (IOException x) {
		    System.err.println(x);
		}
		currentUser = user;
		this.encryptor = encryptor;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void performActions() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException{
		for(;;){
			WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
              
                if(kind == ENTRY_CREATE){
                	WatchEvent<Path> ev = (WatchEvent<Path>)event;
                	Path fileName = ev.context();
                	encryptor.encryptSingleFile(currentUser, fileName.toAbsolutePath().toString());
                
                }
                if(kind == ENTRY_MODIFY){
                	WatchEvent<Path> ev = (WatchEvent<Path>)event;
                	Path fileName = ev.context();
                	encryptor.encryptSingleFile(currentUser, fileName.toAbsolutePath().toString());
                }
                if(kind == ENTRY_DELETE){
                	WatchEvent<Path> ev = (WatchEvent<Path>)event;
                	Path fileName = ev.context();
                	Path encryptedFile = FileSystems.getDefault().getPath(currentUser.getEncryptedDirectoryString() + "\\" + fileName.getFileName());
                	encryptedFile.toFile().delete();
                }
            }
            
		}
	}
	
	
	
}
