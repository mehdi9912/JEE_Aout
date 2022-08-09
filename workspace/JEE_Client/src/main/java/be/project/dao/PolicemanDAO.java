package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import be.project.javabeans.Policeman;

public class PolicemanDAO implements DAO<Policeman> {
	

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public PolicemanDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(Policeman obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("firstname", obj.getFirstname());
		parameters.add("lastname",obj.getLastname());
		parameters.add("password", obj.getPassword());
		parameters.add("police_area_id", String.valueOf(obj.getPoliceArea().getId()));
		parameters.add("chief_id", String.valueOf(obj.getChief().getSerialNumber()));
		parameters.add("userType", "PO");
	
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
	public boolean update(Policeman obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("firstname",obj.getFirstname());
		parameters.add("lastname", obj.getLastname());
		parameters.add("password", obj.getPassword());
		parameters.add("police_area_id", String.valueOf(obj.getPoliceArea().getId()));
		parameters.add("chief_id", String.valueOf(obj.getChief().getSerialNumber()));
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
	public Policeman find(String serialNumber) {
		String key=getApiKey();
		String responseJSON=resource
				.path("policeman")
				.path(serialNumber)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Policeman policeman=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			policeman=(Policeman) mapper.readValue(responseJSON, Policeman.class);
			return policeman;
		} catch (Exception e) {
			System.out.println("error = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Policeman> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
