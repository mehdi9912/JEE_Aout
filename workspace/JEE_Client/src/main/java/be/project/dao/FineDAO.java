package be.project.dao;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.project.enumerations.FineStatus;
import be.project.javabeans.Fine;
import be.project.javabeans.InfractionType;
import be.project.javabeans.Policeman;
import be.project.javabeans.VehiculeType;

public class FineDAO implements DAO<Fine> {
	
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public FineDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(Fine obj) {
		boolean success=false;
		String key=getApiKey();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");  
		String formatDateTime = obj.getTimestamp().format(format);
		String DateTime =formatDateTime.replace("T", " ");
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("infractionId",  String.valueOf(obj.getInfractionType().getId()));
		parameters.add("vehiculeId",  String.valueOf(obj.getVehiculeType().getId()));
		parameters.add("firstname", obj.getCivilianFirstname());
		parameters.add("lastname", obj.getCivilianLastname());
		parameters.add("comment", obj.getComment());
		parameters.add("licensePlate", obj.getLicensePlate());
		parameters.add("policemanSerialNumber", obj.getPoliceman().getSerialNumber());
		parameters.add("timestamp", DateTime );
		parameters.add("totalPrice", String.valueOf(obj.getTotalPrice()));
		ClientResponse res=resource
				.path("fine")
				.path("create")
				.header("key",key)
				.post(ClientResponse.class,parameters)
				;
		int httpResponseCode=res.getStatus();
		if(httpResponseCode == 201) {
			success=true;
		}
		return success;
	}

	@Override
	public boolean delete(String id) {
		boolean success=false;
		String key=getApiKey();
		ClientResponse res=resource
				.path("fine")
				.path(id)
				.header("key",key)
				.delete(ClientResponse.class)
				;
		int httpResponseCode=res.getStatus();
		if(httpResponseCode == 204) {
			success=true;
		}
		return success;
	}

	@Override
	public boolean update(Fine obj) {
		// TODO Auto-generated method stub
				return false;
	}

	@Override
	public Fine find(String id) {
		String key=getApiKey();
		String responseJSON=resource
				.path("fine")
				.path(id)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Fine fine=null;
		ObjectMapper mapper=new ObjectMapper();
		VehiculeType vehicule;
		InfractionType infraction;
		Policeman policeman;
		try {
			JSONObject json = new JSONObject(responseJSON);
			String firstname="";
			String lastname="";
			String licensePlate="";
			int fineId = json.getInt("fineId");
			JSONObject timestampJSON =(JSONObject) json.getJSONObject("timestamp");
			LocalDateTime timestamp =(LocalDateTime)LocalDateTime.of(timestampJSON.getInt("year"), timestampJSON.getInt("monthValue"),
					timestampJSON.getInt("dayOfMonth"),timestampJSON.getInt("hour"), timestampJSON.getInt("minute"));
			double price = json.getDouble("totalPrice");
			if(!json.isNull("civilianFirstname") && !json.isNull("civilianLastname") ) {
				firstname = json.getString("civilianFirstname");
				lastname = json.getString("civilianLastname");
			}
			String comment = json.getString("comment");
			FineStatus status = FineStatus.valueOf(json.getString("status"));
			if(!json.isNull("licensePlate")) {
				licensePlate = json.getString("licensePlate");
			}
			//vehicule
			vehicule =(VehiculeType) mapper.readValue(json.get("vehiculeType").toString(), VehiculeType.class);
			infraction =(InfractionType) mapper.readValue(json.get("infractionType").toString(), InfractionType.class);
			policeman =(Policeman) mapper.readValue(json.get("policeman").toString(), Policeman.class);
			fine = new Fine(fineId,timestamp,price,firstname,lastname,comment,status, vehicule, infraction, policeman, licensePlate);
			return fine;
		} catch (Exception e) {
			System.out.println("error = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Fine> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Fine> getAllFinesBySupervisedAgents(String serialNumber) {
		ArrayList<Fine> fines=new ArrayList<Fine>();
		String key=getApiKey();
		String responseJSON=resource
				.path("fine")
				.path("supervised")
				.path(serialNumber)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		JSONArray jsonArray=new JSONArray(responseJSON);
		ObjectMapper mapper=new ObjectMapper();
		Fine fine =null;
		VehiculeType vehicule;
		InfractionType infraction;
		Policeman policeman;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj=(JSONObject) jsonArray.get(i);
				String firstname="";
				String lastname="";
				String licensePlate="";
				int id = obj.getInt("fineId");
				JSONObject timestampJSON =(JSONObject) obj.getJSONObject("timestamp");
				LocalDateTime timestamp =(LocalDateTime)LocalDateTime.of(timestampJSON.getInt("year"), timestampJSON.getInt("monthValue"),
						timestampJSON.getInt("dayOfMonth"),timestampJSON.getInt("hour"), timestampJSON.getInt("minute"));
				double price = obj.getDouble("totalPrice");
				if(!obj.isNull("civilianFirstname") && !obj.isNull("civilianLastname") ) {
					firstname = obj.getString("civilianFirstname");
					lastname = obj.getString("civilianLastname");
				}
				String comment = obj.getString("comment");
				FineStatus status = FineStatus.valueOf(obj.getString("status"));
				if(!obj.isNull("licensePlate")) {
					licensePlate = obj.getString("licensePlate");
				}
				//vehicule
				vehicule =(VehiculeType) mapper.readValue(obj.get("vehiculeType").toString(), VehiculeType.class);
				infraction =(InfractionType) mapper.readValue(obj.get("infractionType").toString(), InfractionType.class);
				policeman =(Policeman) mapper.readValue(obj.get("policeman").toString(), Policeman.class);
				fine = new Fine(id,timestamp,price,firstname,lastname,comment,status, vehicule, infraction, policeman, licensePlate);
				fines.add(fine);
				}
		} catch (Exception e) {
			System.out.println("Erreur dans fineDAO du client : "+e.getMessage());
		}
		return fines;
	}
	
	public boolean updateStatus(Fine obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("id",String.valueOf(obj.getFineId()));
		parameters.add("status", String.valueOf(obj.getStatus()));

		ClientResponse res=resource
				.path("fine")
				.path("updatestatus")
				.path(String.valueOf(obj.getFineId()))
				.header("key",key)
				.put(ClientResponse.class,parameters)
				;
		int httpResponseCode=res.getStatus();
		if(httpResponseCode == 204) {
			success=true;
		}
		return success;
	}

	public ArrayList<Fine> getAllValidatedFines() {
		ArrayList<Fine> fines=new ArrayList<Fine>();
		String key=getApiKey();
		String responseJSON=resource
				.path("fine")
				.path("validated")
				.path("all")
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		JSONArray jsonArray=new JSONArray(responseJSON);
		ObjectMapper mapper=new ObjectMapper();
		Fine fine =null;
		VehiculeType vehicule;
		InfractionType infraction;
		Policeman policeman;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj=(JSONObject) jsonArray.get(i);
				String firstname="";
				String lastname="";
				String licensePlate="";
				int id = obj.getInt("fineId");
				JSONObject timestampJSON =(JSONObject) obj.getJSONObject("timestamp");
				LocalDateTime timestamp =(LocalDateTime)LocalDateTime.of(timestampJSON.getInt("year"), timestampJSON.getInt("monthValue"),
						timestampJSON.getInt("dayOfMonth"),timestampJSON.getInt("hour"), timestampJSON.getInt("minute"));
				double price = obj.getDouble("totalPrice");
				if(!obj.isNull("civilianFirstname") && !obj.isNull("civilianLastname") ) {
					firstname = obj.getString("civilianFirstname");
					lastname = obj.getString("civilianLastname");
				}
				String comment = obj.getString("comment");
				FineStatus status = FineStatus.valueOf(obj.getString("status"));
				if(!obj.isNull("licensePlate")) {
					licensePlate = obj.getString("licensePlate");
				}
				//vehicule
				vehicule =(VehiculeType) mapper.readValue(obj.get("vehiculeType").toString(), VehiculeType.class);
				infraction =(InfractionType) mapper.readValue(obj.get("infractionType").toString(), InfractionType.class);
				policeman =(Policeman) mapper.readValue(obj.get("policeman").toString(), Policeman.class);
				fine = new Fine(id,timestamp,price,firstname,lastname,comment,status, vehicule, infraction, policeman, licensePlate);
				fines.add(fine);
				}
		} catch (Exception e) {
			System.out.println("Erreur dans fineDAO du client : "+e.getMessage());
		}
		return fines;
	}

}
