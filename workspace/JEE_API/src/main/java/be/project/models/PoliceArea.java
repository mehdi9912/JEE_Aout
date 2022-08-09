package be.project.models;

import java.io.Serializable;
import java.util.ArrayList;

import be.project.dao.PoliceAreaDAO;


public class PoliceArea implements Serializable {

	private static final long serialVersionUID = -153153155850776934L;
	
	private int id;
	private String name;

	public PoliceArea() {

	}
	
	public PoliceArea(String name) {
		this.name = name;
	}
	
	public PoliceArea(int id, String name) {
		this(name);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAreaName() {
		return name;
	}
	
	public void setAreaName(String name) {
		this.name = name;
	}

	public static ArrayList<PoliceArea> getAllPoliceAreas() {
		ArrayList<PoliceArea> policeAreas = new ArrayList<PoliceArea>();
		PoliceAreaDAO policeAreaDAO = new PoliceAreaDAO();
		policeAreas = policeAreaDAO.findAll();
		return policeAreas;
	}

}
