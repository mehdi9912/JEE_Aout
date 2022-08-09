package be.project.models;

import java.io.Serializable;

import be.project.dao.InsuranceDAO;

public class Insurance implements Serializable {

	private static final long serialVersionUID = 925729999887269370L;
	
	private String licensePlate;
	private boolean inOrder;

	public Insurance() {

	}
	
	public Insurance(String licensePlate,  boolean inOrder) {
		this.licensePlate = licensePlate;
		this.inOrder = inOrder;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public boolean isInOrder() {
		return inOrder;
	}

	public void setInOrder(boolean inOrder) {
		this.inOrder = inOrder;
	}
	
	public static boolean getInsuranceOrder(String licensePlate) {
		InsuranceDAO insuranceDAO=new InsuranceDAO();
		return insuranceDAO.getInsuranceOrder(licensePlate);
	}

	
}
