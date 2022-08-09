package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.Admin;

public class AdminDAO implements DAO<Admin> {
	
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public AdminDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(Admin obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Admin obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Admin find(String serialNumber) {
		String key=getApiKey();
		String responseJSON=resource
				.path("admin")
				.path(serialNumber)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		Admin admin=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			admin=(Admin) mapper.readValue(responseJSON, Admin.class);
			return admin;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ArrayList<Admin> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
