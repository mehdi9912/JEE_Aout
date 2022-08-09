package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.PolicemanDAO;



public class Policeman extends User implements Serializable {

	private static final long serialVersionUID = -44518173082330908L;
	
	private Chief chief;
	private PoliceArea policeArea;
	private ArrayList<Fine> fines;

	public Policeman() {

	}
	
	public Policeman(String firstname, String lastname, String password, PoliceArea policeArea, Chief chief) {
		super(firstname, lastname, password);
		this.policeArea=policeArea;
		this.chief=chief;
	}
	public Policeman(String serialNumber, String firstname, String lastname, String password,PoliceArea policeArea, Chief chief) {
		super(serialNumber, firstname, lastname, password);
		this.policeArea=policeArea;
		this.chief=chief;
	}
	public Policeman(String serialNumber, String firstname, String lastname, String password, PoliceArea policeArea,Chief chief, ArrayList<Fine> fines) {
		this(serialNumber, firstname, lastname, password, policeArea, chief);
		this.setFines(fines);
	}
	
	
	public Chief getChief() {
		return chief;
	}
	
	public void setChief(Chief chief) {
		this.chief = chief;
	}
	
	public PoliceArea getPoliceArea() {
		return policeArea;
	}
	
	public void setPoliceArea(PoliceArea policeArea) {
		this.policeArea = policeArea;
	}
	
	public ArrayList<Fine> getFines() {
		return fines;
	}

	public void setFines(ArrayList<Fine> fines) {
		this.fines = fines;
	}

	public void addFine(Fine fine) {
		if(fine!=null) {
			this.getFines().add(fine);
		}
	}

	public static Policeman getPoliceman(String serialNumber) {
		Policeman policeman=null;
		PolicemanDAO policemanDAO=new PolicemanDAO();
		policeman=policemanDAO.find(serialNumber);
		return policeman;
	}
}