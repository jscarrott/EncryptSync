package core;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SyncInitialiser {
	
	private User user;
	private Map<File, Long> unEnDates;
	private Map<File, Long> enDates;
	private ArrayList<File> outDatedFiles; 
	/**
	 * Works out which unencrypted files have become outdated since an encryption has last been run
	 * @param user
	 */
	
	public SyncInitialiser(User user){
		this.user = user;
		unEnDates = getUnencryptedFileDates();
		enDates = getEncryptedFileDates();
	}
	/**
	 * gets that lastModified dates on all files in the unencrypted directory
	 * @return
	 */
	
	Map<File, Long> getUnencryptedFileDates(){
		Directory unDir = user.getUnencryptedDirectory();
		ArrayList<File> files = new ArrayList<>(unDir.getContainedFiles());
		Map<File, Long> fileDateMap = new TreeMap<File, Long>();
		for(File file : files){
			Long date = file.lastModified();
			fileDateMap.put(file, date);
		}
		return fileDateMap;
	}
	
	/** 
	 * get all the last modified dates from the files in the encrypted directory
	 * @return
	 */
	Map<File, Long> getEncryptedFileDates(){
		Directory enDir = user.getEncryptedDirectory();
		ArrayList<File> files = new ArrayList<>(enDir.getContainedFiles());
		Map<File, Long> fileDateMap = new TreeMap<File, Long>();
		for(File file : files){
			Long date = file.lastModified();
			fileDateMap.put(file, date);
		}
		return fileDateMap;
	}
	
	/**
	 * works out which files need replacing with the encrypted versions.
	 * @return list of encrypted files that need to replace the unencrypted files.
	 */
	ArrayList<File> generateNewerEncryptedFileList(){
		Directory enDir = user.getEncryptedDirectory();
		ArrayList<File> enFiles = new ArrayList<>(enDir.getContainedFiles());
		for(File file : enFiles){
			if(enDates.containsKey(file) || unEnDates.containsKey(file)){
				if(enDates.get(file) > unEnDates.get(file)){
					outDatedFiles.add(file);
				}
				}
			else if (enDates.containsKey(file) ) {
				outDatedFiles.add(file);
				
			}
			
		}
		return outDatedFiles;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the unEnDates
	 */
	public Map<File, Long> getUnEnDates() {
		return unEnDates;
	}

	/**
	 * @param unEnDates the unEnDates to set
	 */
	public void setUnEnDates(Map<File, Long> unEnDates) {
		this.unEnDates = unEnDates;
	}

	/**
	 * @return the enDates
	 */
	public Map<File, Long> getEnDates() {
		return enDates;
	}

	/**
	 * @param enDates the enDates to set
	 */
	public void setEnDates(Map<File, Long> enDates) {
		this.enDates = enDates;
	}

}
