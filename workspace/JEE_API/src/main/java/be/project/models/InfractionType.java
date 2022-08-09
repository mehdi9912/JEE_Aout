package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.InfractionTypeDAO;
import be.project.models.InfractionType;

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

	public int insert() {
		InfractionTypeDAO infractionTypeDAO=new InfractionTypeDAO();
		String idString =infractionTypeDAO.insert(this);
		int id = Integer.valueOf(idString);
		return id;
	}

	public int update() {
		InfractionTypeDAO infractionTypeDAO=new InfractionTypeDAO();
		int updateCode=infractionTypeDAO.update(this);
		return updateCode;
	}

	public static boolean delete(String id) {
		InfractionTypeDAO infractionTypeDAO=new InfractionTypeDAO();
		boolean success=infractionTypeDAO.delete(id);
		return success;
	}

	
}