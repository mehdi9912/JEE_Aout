package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.ChiefDAO;

public class Chief extends User implements Serializable {

	private static final long serialVersionUID = -1694550749974747638L;

	private PoliceArea policeArea;
	
	public Chief() {

	}
	public Chief(String firstname, String lastname, String password, PoliceArea policeArea) {
		super(firstname, lastname, password);
		this.policeArea= policeArea;
	}
	public Chief(String serialNumber, String firstname, String lastname, String password, PoliceArea policeArea) {
		super(serialNumber, firstname, lastname, password);
		this.policeArea = policeArea;
	}
	
	public PoliceArea getPoliceArea() {
		return policeArea;
	}
	
	public void setPoliceArea(PoliceArea policeArea) {
		this.policeArea = policeArea;
	}

	public static ArrayList<Chief> getAllChiefs() {
		ArrayList<Chief> chiefs = new ArrayList<Chief>();
		ChiefDAO chiefDAO = new ChiefDAO();
		chiefs = chiefDAO.findAll();
		return chiefs;
	}

}
