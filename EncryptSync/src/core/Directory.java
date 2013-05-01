package core;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Directory {
  /** Contains the path to the directory, a list of all the files in the directory and whether the directory has been encrypted. 
   * @field  location stores the path object of the directory
   * @field containedFiles an arrayList of all the files in the directory, can be updated using pollDIrectory()
   * @field isEncrypted true if the directory has been encrypted
   */
	Path location;
	List<File> containedFiles = new ArrayList<File>();
	public boolean isEncrypted = false;
	
	/** takes a string pathname and generates the directory from it.
	 * 
	 * @param path the string path name to set the directory 
	 */
	public Directory(String path){
		FileSystem fs = FileSystems.getDefault();
		try {
			location = fs.getPath(path);//Uses the string input to generate a correct path using the filesystem class
		} catch (Exception e) {
			System.out.println("Invalid path entered, user should not be created");
		}
		
		pollDirectory(); //generates the initial list of files in the directory.
		
	}
	/** updates the list of files in the directory.
	 * 
	 */
	public void pollDirectory(){
		containedFiles = Arrays.asList(location.toFile().listFiles());//updates the list of files in the directory.
	}
//getters and setters
	public Path getLocation() {
		return location;
	}

	public void setLocation(Path location) {
		this.location = location;
	}

	public List<File> getContainedFiles() {
		return containedFiles;
	}

	public void setContainedFiles(List<File> containedFiles) {
		this.containedFiles = containedFiles;
	}

	public boolean isEncrypted() {
		return isEncrypted;
	}

	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}
}
