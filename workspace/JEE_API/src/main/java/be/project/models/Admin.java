package be.project.models;

import java.io.Serializable;

import be.project.dao.AdminDAO;


public class Admin extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8325304293354505264L;
	
	public Admin() {
		
	}
	
	public Admin(String serialNumber, String firstname, String lastname, String password) {
		super(serialNumber, firstname, lastname, password);
	}

	public static Admin getAdmin(String serialNumber) {
		Admin admin=null;
		AdminDAO adminDAO=new AdminDAO();
		admin=adminDAO.find(serialNumber);
		return admin;
	}

}
