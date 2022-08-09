package be.project.models;

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

	public static Chief getChief(String serialNumber) {
		Chief chief=null;
		ChiefDAO chiefDAO=new ChiefDAO();
		chief=chiefDAO.find(serialNumber);
		return chief;
	}

	public static ArrayList<Chief> getAllChiefs() {
		ArrayList<Chief> chiefs = new ArrayList<Chief>();
		ChiefDAO chiefDAO = new ChiefDAO();
		chiefs = chiefDAO.findAll();
		return chiefs;
	}

	public String createChief() {
		ChiefDAO chiefDAO=new ChiefDAO();
		return chiefDAO.insert(this);
	}

}
