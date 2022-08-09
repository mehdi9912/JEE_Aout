package be.project.javabeans;

import java.util.ArrayList;

import be.project.dao.AdminDAO;
import be.project.dao.ChiefDAO;
import be.project.dao.CollectorDAO;
import be.project.dao.PolicemanDAO;
import be.project.dao.UserDAO;

public abstract class User{

	private String serialNumber;
	private String firstname;
	private String lastname;
	private String password;
	
	public User() {
		
	}
	public User( String firstname, String lastname, String password) {
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
	
	public static boolean login(String serialNumber,String password) {
		boolean success=false;
		UserDAO userDAO;
		if(!serialNumber.isEmpty() && !serialNumber.equals("") && !password.isEmpty() && !password.equals("")) {
			userDAO=new UserDAO();
			success=userDAO.login(serialNumber, password);
		}
		
		return success;
	}
	
	public static User getUser(String serialNumber) {
		User user=null;
		
		if(!serialNumber.isEmpty() && !serialNumber.equals("")) {
			String serialIdentifier = serialNumber.substring(0,2).toUpperCase();
			if(serialIdentifier.equals("AD")) {
				 AdminDAO adminDAO=new AdminDAO();
				 user= adminDAO.find(serialNumber); 
			}
			if(serialIdentifier.equals("CH")) {
				ChiefDAO chiefDAO=new ChiefDAO();
				user= chiefDAO.find(serialNumber);
			}
			if(serialIdentifier.equals("PO")) {
				PolicemanDAO policemanDAO=new PolicemanDAO();
				user = policemanDAO.find(serialNumber);
			}
			if(serialIdentifier.equals("CO")) {
				CollectorDAO collectorDAO=new CollectorDAO();
				user = collectorDAO.find(serialNumber);
			}
		}
		return user;
	}
	
	public boolean serialNumberIsValid() {
		if(this.serialNumber != null && !this.serialNumber.isEmpty()) {
			String serialIdentifier = this.serialNumber.substring(0,2).toUpperCase();
			if(serialIdentifier.equals("AD") || serialIdentifier.equals("PO") || serialIdentifier.equals("CH") || serialIdentifier.equals("CO")) {
				return true;
			}
		}
		return false;	
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
	public static ArrayList<User> getAllUsers() {
		ArrayList<User> users = new ArrayList<User>();
		UserDAO userDAO = new UserDAO();
		users = userDAO.findAll();
		return users;
	}
	public boolean updateUser() {
		boolean success=false;
		UserDAO userDAO=new UserDAO();
		success=userDAO.update(this);
		return success;
	}
	public static boolean deleteUser(String serialNumber) {
		boolean success=false;
		if(!serialNumber.isEmpty() && !serialNumber.equals("")) {
			String serialIdentifier = serialNumber.substring(0,2).toUpperCase();
			if(serialIdentifier.equals("CH")) {
				System.out.println("est bien un chef on appel la chief dao");
				ChiefDAO chiefDAO=new ChiefDAO();
				success= chiefDAO.delete(serialNumber);
			}
			if(serialIdentifier.equals("PO")) {
				PolicemanDAO policemanDAO=new PolicemanDAO();
				success = policemanDAO.delete(serialNumber);
			}
			if(serialIdentifier.equals("CO")) {
				CollectorDAO collectorDAO=new CollectorDAO();
				success = collectorDAO.delete(serialNumber);
			}
		}
		return success;
	}
	public boolean insertUser() {
		boolean success=false;
		if(this instanceof Chief) {
			ChiefDAO chiefDAO=new ChiefDAO();
			success= chiefDAO.insert((Chief)this);
		}
		if(this instanceof Policeman){
			PolicemanDAO policemanDAO=new PolicemanDAO();
			success = policemanDAO.insert((Policeman)this);
		}
		if(this instanceof Collector) {
			CollectorDAO collectorDAO=new CollectorDAO();
			success = collectorDAO.insert((Collector)this);
		}
		return success;
	}
}
