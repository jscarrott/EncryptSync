package core;

public class User {

	String passwordKey;
	String name;
	Directory inputDirectory;
	Directory outputDirectory;
	
	User(String nameIn, String in, String out){
		name = nameIn;
		inputDirectory = new Directory( in);
		outputDirectory = new Directory( out);
	}

	public String getPasswordKey() {
		return passwordKey;
	}

	public void setPasswordKey(String passwordKey) {
		this.passwordKey = passwordKey;
	}
}
