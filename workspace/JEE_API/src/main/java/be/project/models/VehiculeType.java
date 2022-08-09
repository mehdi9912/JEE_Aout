package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.VehiculeTypeDAO;
import be.project.models.VehiculeType;

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

	public static boolean delete(String id) {
		VehiculeTypeDAO vehiculeDAO=new VehiculeTypeDAO();
		boolean success=vehiculeDAO.delete(id);
		return success;
	}

	public int update() {
		VehiculeTypeDAO vehiculeDAO=new VehiculeTypeDAO();
		int updateCode=vehiculeDAO.update(this);
		return updateCode;
	}

	public int insert() {
		VehiculeTypeDAO vehiculeDAO=new VehiculeTypeDAO();
		String idString =vehiculeDAO.insert(this);
		int id = Integer.valueOf(idString);
		return id;
	}

	public static VehiculeType getVehicule(String id) {
		VehiculeTypeDAO vehiculeTypeDAO = new VehiculeTypeDAO();
		return vehiculeTypeDAO.find(id);
	}

}
