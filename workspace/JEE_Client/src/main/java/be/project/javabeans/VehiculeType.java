package be.project.javabeans;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.VehiculeTypeDAO;

public class VehiculeType implements Serializable {

	private static final long serialVersionUID = -1692352836950814981L;
	
	private int id;
	private String type;

	public VehiculeType() {

	}
	
	public VehiculeType(String type) {
		this.type = type;
	}
	
	public VehiculeType(int id, String type) {
		this(type);
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

	public static ArrayList<VehiculeType> getAllVehicules() {
		ArrayList<VehiculeType> vehiculeTypes = new ArrayList<VehiculeType>();
		VehiculeTypeDAO VehiculeTypeDAO = new VehiculeTypeDAO();
		vehiculeTypes = VehiculeTypeDAO.findAll();
		return vehiculeTypes;
	}

	public static VehiculeType getVehicule(String id) {
		VehiculeTypeDAO vehiculeTypeDAO = new VehiculeTypeDAO();
		return vehiculeTypeDAO.find(id);
	}

	public boolean update() {
		boolean success=false;
		VehiculeTypeDAO vehiculeTypeDAO =new VehiculeTypeDAO();
		success=vehiculeTypeDAO.update(this);
		return success;
	}

	public boolean insert() {
		boolean success=false;
		VehiculeTypeDAO vehiculeTypeDAO =new VehiculeTypeDAO();
		success=vehiculeTypeDAO.insert(this);
		return success;
	}

	public static boolean delete(String id) {
		boolean success=false;
		VehiculeTypeDAO vehiculeTypeDAO =new VehiculeTypeDAO();
		success=vehiculeTypeDAO.delete(id);
		return success;
	}


}
