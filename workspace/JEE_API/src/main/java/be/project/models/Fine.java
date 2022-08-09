package be.project.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import be.project.dao.FineDAO;
import be.project.enumerations.FineStatus;

public class Fine implements Serializable {

	private static final long serialVersionUID = 3972439220407397298L;
	
	private int id;
	private LocalDateTime timestamp;
	private double totalPrice;
	private String civilianFirstname;
	private String civilianLastname;
	private String comment;
	private FineStatus status;
	private VehiculeType vehiculeType;
	private InfractionType infractionType;
	private Policeman policeman;
	private String licensePlate;

	public Fine() {

	}
	
	public Fine(LocalDateTime timestamp, double totalPrice, String comment,VehiculeType vehiculeType, InfractionType infractionType, Policeman policeman) {
		this.timestamp = timestamp;
		this.totalPrice = totalPrice;
		this.comment = comment;
		this.vehiculeType = vehiculeType;
		this.infractionType = infractionType;
		this.policeman= policeman;
	}
	
	public Fine(LocalDateTime timestamp, double totalPrice, String comment,VehiculeType vehiculeType, InfractionType infractionType,Policeman policeman, String licensePlate) {
		this(timestamp,  totalPrice,  comment, vehiculeType, infractionType, policeman);
		this.licensePlate = licensePlate;
	}
	
	public Fine(LocalDateTime timestamp, double totalPrice, String civilianFirstname, String civilianLastname, String comment,VehiculeType vehiculeType, InfractionType infractionType, Policeman policeman) {
		this(timestamp,  totalPrice,  comment, vehiculeType, infractionType, policeman);
		this.civilianFirstname = civilianFirstname;
		this.civilianLastname = civilianLastname; 
	}
	
	public Fine(LocalDateTime timestamp, double totalPrice, String civilianFirstname, String civilianLastname, String comment,VehiculeType vehiculeType, InfractionType infractionType,Policeman policeman, String licensePlate) {
		this(timestamp,  totalPrice, civilianFirstname, civilianLastname, comment, vehiculeType, infractionType, policeman);
		this.licensePlate = licensePlate;
	}
	
	public Fine(int id, LocalDateTime timestamp, double totalPrice, String civilianFirstname, String civilianLastname, String comment, FineStatus status, VehiculeType vehiculeType, InfractionType infractionType, Policeman policeman, String licensePlate) {
		this(timestamp, totalPrice, civilianFirstname, civilianLastname, comment, vehiculeType, infractionType, policeman, licensePlate);
		this.id = id;
		this.status = status;
	}
	
	public int getFineId() {
		return id;
	}

	public void setFineId(int fineId) {
		this.id = fineId;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCivilianFirstname() {
		return civilianFirstname;
	}

	public void setCivilianFirstname(String civilianFirstname) {
		this.civilianFirstname = civilianFirstname;
	}

	public String getCivilianLastname() {
		return civilianLastname;
	}

	public void setCivilianLastname(String civilianLastname) {
		this.civilianLastname = civilianLastname;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public FineStatus getStatus() {
		return status;
	}

	public void setStatus(FineStatus status) {
		this.status = status;
	}

	public VehiculeType getVehiculeType() {
		return vehiculeType;
	}

	public void setVehiculeType(VehiculeType vehiculeType) {
		this.vehiculeType = vehiculeType;
	}

	public InfractionType getInfractionType() {
		return infractionType;
	}

	public void setInfractionType(InfractionType infractionType) {
		this.infractionType = infractionType;
	}

	public static ArrayList<Fine> getAllFines() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Policeman getPoliceman() {
		return policeman;
	}

	public void setPoliceman(Policeman policeman) {
		this.policeman = policeman;
	}
	
	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public static ArrayList<Fine> getAllFinesBySupervisedAgents(String serialNumber) {
		ArrayList<Fine> fines = new ArrayList<Fine>();
		FineDAO fineDAO = new FineDAO();
		fines = fineDAO.findAllBySupervisedAgents(serialNumber);
		return fines;
	}

	public int insert() {
		FineDAO fineDAO=new FineDAO();
		String idString =fineDAO.insert(this);
		int id = Integer.valueOf(idString);
		return id;
	}

	public int updateStatus() {
		FineDAO fineDAO=new FineDAO();
		int updateCode=fineDAO.updateStatus(this);
		return updateCode;
	}

	public static Fine getFine(String id) {
		FineDAO fineDAO = new FineDAO();
		return fineDAO.find(id);
	}

	public static boolean delete(String id) {
		boolean success=false;
		FineDAO fineDAO = new FineDAO();
		success= fineDAO.delete(id);
		return success;
	}

	public static ArrayList<Fine> getAllValidatedFines() {
		ArrayList<Fine> fines = new ArrayList<Fine>();
		FineDAO fineDAO = new FineDAO();
		fines = fineDAO.findAllValidatedFines();
		return fines;
	}

	


}