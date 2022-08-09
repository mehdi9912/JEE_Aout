package be.project.javabeans;

import java.io.Serializable;

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

}
