package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.Insurance;

public class InsuranceDAO implements DAO<Insurance> {
	
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public InsuranceDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}

	@Override
	public boolean insert(Insurance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Insurance obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Insurance find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Insurance> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkInsuranceOrder(String licensePlate) {
		String key=getApiKey();
		String responseJSON=resource
				.path("insurance")
				.path(licensePlate)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		JSONObject jsonResponse = new JSONObject(responseJSON);
		if(jsonResponse.has("inOrder")) {
			if(jsonResponse.getString("inOrder") !=null) {
				String inOrder=jsonResponse.getString("inOrder");
				if(inOrder.equals("false")) {
					return false;
				}
			}
		}
		return true;
	}
}
