package be.project.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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

import be.project.javabeans.Chief;
import be.project.javabeans.Collector;
import be.project.javabeans.Policeman;
import be.project.javabeans.User;
import be.project.javabeans.PoliceArea;

public class UserDAO implements DAO<User> {
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	private static void saveApiKey(String apiKey) {
		Context ctx;
		try {
			ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			env.addToEnvironment("apiKey", apiKey);
		} catch (NamingException e) {
			System.out.println("Error save api key");
		}
	}
	
	
	public UserDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(User obj) {
		boolean success=false;
		String key=getApiKey();
		Policeman policeman=null;
		
		if(obj instanceof Policeman) {
			 policeman = (Policeman)obj;
		}
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("firstname", obj.getFirstname());
		parameters.add("lastname",obj.getLastname());
		parameters.add("password", obj.getPassword());
		if(policeman !=null) {
			parameters.add("police_area_id", String.valueOf(policeman.getPoliceArea().getId()));
			parameters.add("chief_id", String.valueOf(policeman.getChief().getSerialNumber()));
			parameters.add("userType", "PO");
		}
		ClientResponse res=resource
				.path("user")
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
				.path("user")
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
	public boolean update(User obj) {
		Policeman policeman=null;
		Chief chief=null;
		Collector collector=null;
		if(obj instanceof Policeman) {
			 policeman = (Policeman)obj;
		}
		if(obj instanceof Chief) {
			chief = (Chief)obj;
		}
		if(obj instanceof Collector) {
			collector = (Collector)obj;
		}
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("firstname",obj.getFirstname());
		parameters.add("lastname", obj.getLastname());
		parameters.add("password", obj.getPassword());
		if(collector !=null) {
			parameters.add("police_area_id", String.valueOf(collector.getPoliceArea().getId()));
		}
		if(chief !=null) {
			parameters.add("police_area_id", String.valueOf(chief.getPoliceArea().getId()));
		}
		if(policeman !=null) {
			parameters.add("police_area_id", String.valueOf(policeman.getPoliceArea().getId()));
			parameters.add("chief_id", String.valueOf(policeman.getChief().getSerialNumber()));
		}
		ClientResponse res=resource
				.path("user")
				.path("update")
				.path(obj.getSerialNumber())
				.header("key",key)
				.put(ClientResponse.class,parameters)
				;
		int httpResponseCode=res.getStatus();
		if(httpResponseCode == 204) {
			success=true;
		}
		return success;
	}

	@Override
	public User find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<User> findAll() {
		ArrayList<User> users=new ArrayList<User>();
		String key=getApiKey();
		String responseJSON=resource
				.path("user")
				.path("all")
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		JSONArray jsonArray=new JSONArray(responseJSON);
		ObjectMapper mapper=new ObjectMapper();
		User user = null;
		PoliceArea policeaArea ;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj=(JSONObject) jsonArray.get(i);
				String serialNumber = obj.getString("serialNumber").toUpperCase();
				policeaArea =(PoliceArea) mapper.readValue(obj.get("policeArea").toString(), PoliceArea.class);
				
				if(serialNumber.substring(0,2).equals("CO")) {
					Collector collector = new Collector();
					collector.setSerialNumber(serialNumber);
					collector.setLastname(obj.getString("lastname"));
					collector.setFirstname(obj.getString("firstname"));
					collector.setPoliceArea(policeaArea);
					user = collector;
				}
				if(serialNumber.substring(0,2).equals("CH")) {
					Chief chief= new Chief();
					chief.setSerialNumber(serialNumber);
					chief.setLastname(obj.getString("lastname"));
					chief.setFirstname(obj.getString("firstname"));
					chief.setPoliceArea(policeaArea);
					user = chief;
				}
				if(serialNumber.substring(0,2).equals("PO")) {
					Policeman policeman = new Policeman();
					policeman.setSerialNumber(serialNumber);
					policeman.setLastname(obj.getString("lastname"));
					policeman.setFirstname(obj.getString("firstname"));
					policeman.setPoliceArea(policeaArea);
					user = policeman;
					
				}
				users.add(user);

			}
		} catch (Exception e) {
			System.out.println("Erreur dans userDAO du client : "+e.getMessage());
		}
		return users;
	}


	public boolean login(String serialNumber, String password) {
		boolean success=false;
		int status;
		MultivaluedMap<String,String> paramsPost=new MultivaluedMapImpl();
		paramsPost.add("serialNumber",serialNumber);
		paramsPost.add("password", password);
		ClientResponse res=resource
				.path("user")
				.path("login")
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class,paramsPost)
				;
		String response=res.getEntity(String.class);
		status=res.getStatus();
		JSONObject jsonResponse = new JSONObject(response);
		if(status==200) {
			//status ok, on verifie la reponse json
			if(jsonResponse.has("connected")) {
				if(jsonResponse.getString("connected") !=null) {
					String connected=jsonResponse.getString("connected");
					if(connected.equals("true")) {
						//récuperer api-key + ajout variable d'environnement
						MultivaluedMap<String, String> headers;
						headers=res.getHeaders();
						List<String> apiKey=headers.get("api-key");
						saveApiKey(apiKey.get(0));
						success=true;
					}
				}
			}
		
		}
		return success;
	}

}
