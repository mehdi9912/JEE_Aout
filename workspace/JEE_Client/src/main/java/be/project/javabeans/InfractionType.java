package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.InfractionTypeDAO;

public class InfractionType implements Serializable {

	private static final long serialVersionUID = 6343929248285388292L;
	
	private int id;
	private String type;
	private double amount;

	public InfractionType() {

	}
	
	public InfractionType(String type, double amount) {
		this.type = type;
		this.amount = amount;
	}
	
	public InfractionType(int id, String type, double amount) {
		this(type, amount);
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public static ArrayList<InfractionType> getAllInfractions() {
		ArrayList<InfractionType> infractions = new ArrayList<InfractionType>();
		InfractionTypeDAO infractionTypeDAO = new InfractionTypeDAO();
		infractions = infractionTypeDAO.findAll();
		return infractions;
	}

	public static InfractionType getInfraction(String id) {
		InfractionTypeDAO infractionTypeDAO = new InfractionTypeDAO();
		return infractionTypeDAO.find(id);
	}

	public boolean update() {
		boolean success=false;
		InfractionTypeDAO infractionTypeDAO =new InfractionTypeDAO();
		success=infractionTypeDAO.update(this);
		return success;
	}

	public static boolean delete(String id) {
		boolean success=false;
		InfractionTypeDAO infractionTypeDAO =new InfractionTypeDAO();
		success=infractionTypeDAO.delete(id);
		return success;
	}

	public boolean insert() {
		boolean success=false;
		InfractionTypeDAO infractionTypeDAO =new InfractionTypeDAO();
		success=infractionTypeDAO.insert(this);
		return success;
	}

	
}
