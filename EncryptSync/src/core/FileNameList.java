package core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class FileNameList {

	private ArrayList<String> fileNames;
	@XmlElementWrapper(name = "FileNameList")
	@XmlElement(name = "FileName")

	public ArrayList<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(ArrayList<String> fileNames) {
		this.fileNames = fileNames;
	}
	
	
}
