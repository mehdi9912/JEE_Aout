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

import be.project.javabeans.Collector;

public class CollectorDAO implements DAO<Collector> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public CollectorDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(Collector obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("firstname", obj.getFirstname());
		parameters.add("lastname",obj.getLastname());
		parameters.add("password", obj.getPassword());
		parameters.add("police_area_id", String.valueOf(obj.getPoliceArea().getId()));

		ClientResponse res=resource
				.path("collector")
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
	public boolean update(Collector obj) {
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
	public Collector find(String serialNumber) {
		String key=getApiKey();
		String responseJSON=resource
				.path("collector")
				.path(serialNumber)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Collector collector=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			collector=(Collector) mapper.readValue(responseJSON, Collector.class);
			return collector;
		} catch (Exception e) {
			System.out.println("error = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Collector> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
