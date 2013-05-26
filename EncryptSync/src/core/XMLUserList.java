package core;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "EncryptSync")
public class XMLUserList {

	// XmLElementWrapper generates a wrapper element around XML representation
	  
	  
	  private ArrayList<XMLUser> userList;
	  @XmlElementWrapper(name = "UserList")
	  // XmlElement sets the name of the entities
	  @XmlElement(name = "user")
	public ArrayList<XMLUser> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<XMLUser> userList) {
		this.userList = userList;
	}
	  
}
