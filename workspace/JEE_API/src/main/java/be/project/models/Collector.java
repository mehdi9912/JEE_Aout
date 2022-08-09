package be.project.models;

import java.io.Serializable;

import be.project.dao.CollectorDAO;


public class Collector extends User implements Serializable {

	private static final long serialVersionUID = -7597849322554383647L;
	
	private PoliceArea policeArea;

	public Collector() {
		
	}
	
	public Collector(String firstname, String lastname, String password, PoliceArea policeArea) {
		super(firstname, lastname, password);
		this.policeArea=policeArea;
	}
	
	public Collector(String serialNumber, String firstname, String lastname, String password, PoliceArea policeArea) {
		super(serialNumber, firstname, lastname, password);
		this.policeArea=policeArea;
	}
	
	public PoliceArea getPoliceArea() {
		return policeArea;
	}
	
	public void setPoliceArea(PoliceArea policeArea) {
		this.policeArea = policeArea;
	}

	public static Collector getCollector(String serialNumber) {
		Collector collector=null;
		CollectorDAO collectorDAO=new CollectorDAO();
		collector=collectorDAO.find(serialNumber);
		return collector;
	}

	public String createCollector() {
		CollectorDAO collectorDAO = new CollectorDAO();
		return collectorDAO.insert(this);
	}

}
