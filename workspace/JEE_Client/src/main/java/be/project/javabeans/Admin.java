package be.project.javabeans;

import java.io.Serializable;

public class Admin extends User implements Serializable {

	private static final long serialVersionUID = 1555758898112988479L;

	public Admin() {
		
	}
	
	public Admin(String serialNumber, String firstname, String lastname, String password) {
		super(serialNumber, firstname, lastname, password);
	}

}
