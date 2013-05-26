package core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name = "user")

@XmlType(propOrder = { "name", "configDirectory", "unencryptedDirectory", "encryptedDirectory", "referenceFile" , "fileNameList"})



public class XMLUser {

	String unencryptedDirectory; // input directory for unencrypted files
	String encryptedDirectory; // output directory for encrypted files
	String referenceFile;// file location for the test file used to verify the key
	String configDirectory;
	String name;
	FileNameList  fileNameList;
	public FileNameList getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(FileNameList fileNameList) {
		this.fileNameList = fileNameList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnencryptedDirectory() {
		return unencryptedDirectory;
	}
	public void setUnencryptedDirectory(String unencryptedDirectory) {
		this.unencryptedDirectory = unencryptedDirectory;
	}
	public String getEncryptedDirectory() {
		return encryptedDirectory;
	}
	public void setEncryptedDirectory(String encryptedDirectory) {
		this.encryptedDirectory = encryptedDirectory;
	}
	public String getReferenceFile() {
		return referenceFile;
	}
	public void setReferenceFile(String referenceFile) {
		this.referenceFile = referenceFile;
	}
	public String getConfigDirectory() {
		return configDirectory;
	}
	public void setConfigDirectory(String configDirectory) {
		this.configDirectory = configDirectory;
	}
}
