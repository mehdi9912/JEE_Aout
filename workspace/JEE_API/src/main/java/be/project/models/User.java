package be.project.models;

import java.util.ArrayList;

import be.project.dao.AdminDAO;
import be.project.dao.ChiefDAO;
import be.project.dao.CollectorDAO;
import be.project.dao.PolicemanDAO;
import be.project.dao.UserDAO;
import be.project.models.User;

public abstract class User{

	private String serialNumber;
	private String firstname;
	private String lastname;
	private String password;
	
	public User() {
		
	}
	public User(String firstname, String lastname, String password) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.password=password;
	}
	public User(String serialNumber, String firstname, String lastname, String password) {
		this.serialNumber=serialNumber;
		this.firstname=firstname;
		this.lastname=lastname;
		this.password=password;
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public static boolean login(String serialNumber, String password) {
		boolean success=false;
		
		if(!serialNumber.isEmpty() && !serialNumber.equals("") && !password.isEmpty() && !password.equals("")) {
			String userTypeId = serialNumber.substring(0,2);
			if(userTypeId.equals("AD")) {
				 AdminDAO adminDAO=new AdminDAO();
				 success= adminDAO.login(serialNumber, password); 
			}
			if(userTypeId.equals("CH")) {
				ChiefDAO chiefDAO=new ChiefDAO();
				success= chiefDAO.login(serialNumber, password);
			}
			if(userTypeId.equals("PO")) {
				PolicemanDAO policemanDAO=new PolicemanDAO();
				success = policemanDAO.login(serialNumber, password);
			}
			if(userTypeId.equals("CO")) {
				CollectorDAO collectorDAO=new CollectorDAO();
				success = collectorDAO.login(serialNumber, password);
			}
		}
		return success;
	}
	
	public static User getUser(String serialNumber) {
		User user=null;
		
		if(!serialNumber.isEmpty() && !serialNumber.equals("")) {
			String userTypeId = serialNumber.substring(0,2);
			if(userTypeId== "AD") {
				 AdminDAO adminDAO=new AdminDAO();
				 user= adminDAO.find(serialNumber); 
			}
			if(userTypeId== "CH") {
				ChiefDAO chiefDAO=new ChiefDAO();
				user= chiefDAO.find(serialNumber);
			}
			if(userTypeId== "PO") {
				PolicemanDAO policemanDAO=new PolicemanDAO();
				user = policemanDAO.find(serialNumber);
			}
			if(userTypeId== "CO") {
				CollectorDAO collectorDAO=new CollectorDAO();
				user = collectorDAO.find(serialNumber);
			}
		}
		return user;
	}
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		UserDAO userDAO = new UserDAO();
		users = userDAO.findAll();
		return users;
	}
	public static User getCorrectInstanceOfUser(String serialNumber) {
		User user = null;
		if(serialNumber.substring(0,2).equals("PO")) {
			Policeman policeman = new Policeman();
			policeman.setSerialNumber(serialNumber);
			user = (Policeman)policeman;
		}
		if(serialNumber.substring(0,2).equals("CO")) {
			Collector collector = new Collector();
			collector.setSerialNumber(serialNumber);
			user = (Collector)collector;
		}
		if(serialNumber.substring(0,2).equals("CH")) {
			Chief chief = new Chief();
			chief.setSerialNumber(serialNumber);
			user = (Chief)chief;
		}
		return user;
	}
	public int update() {
		UserDAO userDAO=new UserDAO();
		int updateCode=userDAO.update(this);
		return updateCode;
	}
	public static boolean delete(String serialNumber) {
		UserDAO userDAO=new UserDAO();
		boolean success=userDAO.delete(serialNumber);
		return success;
	}
	public String insertUser() {
		UserDAO userDAO=new UserDAO();
		return userDAO.insert(this);
	}
}