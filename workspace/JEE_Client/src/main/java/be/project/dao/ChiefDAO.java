package be.project.dao;

import java.net.URI;
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
import be.project.javabeans.Chief;

public class ChiefDAO implements DAO<Chief> {
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public ChiefDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(Chief obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("firstname", obj.getFirstname());
		parameters.add("lastname",obj.getLastname());
		parameters.add("password", obj.getPassword());
		parameters.add("police_area_id", String.valueOf(obj.getPoliceArea().getId()));
		
		ClientResponse res=resource
				.path("chief")
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
	public boolean update(Chief obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("firstname",obj.getFirstname());
		parameters.add("lastname", obj.getLastname());
		parameters.add("password", obj.getPassword());
		parameters.add("police_area_id", String.valueOf(obj.getPoliceArea().getId()));
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
	public Chief find(String serialNumber) {
		String key=getApiKey();
		String responseJSON=resource
				.path("chief")
				.path(serialNumber)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Chief chief=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			chief=(Chief) mapper.readValue(responseJSON, Chief.class);
			return chief;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<Chief> findAll() {
		ArrayList<Chief> chiefs=new ArrayList<Chief>();
		String key=getApiKey();
		String responseJSON=resource
				.path("chief")
				.path("all")
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		JSONArray jsonArray=new JSONArray(responseJSON);
		Chief chief = null;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj=(JSONObject) jsonArray.get(i);
				String serialNumber = obj.getString("serialNumber");
				String firstname = obj.getString("firstname");
				String lastname = obj.getString("lastname");
				chief = new Chief();
				chief.setSerialNumber(serialNumber);
				chief.setFirstname(firstname);
				chief.setLastname(lastname);
				chiefs.add(chief);
				}
		} catch (Exception e) {
			System.out.println("Erreur dans ChiefDAO du client : "+e.getMessage());
		}
		return chiefs;
	}

}
