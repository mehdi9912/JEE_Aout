package be.project.dao;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import be.project.javabeans.PoliceArea;

public class PoliceAreaDAO implements DAO<PoliceArea> {

	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public PoliceAreaDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}
	@Override
	public boolean insert(PoliceArea obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PoliceArea obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PoliceArea find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PoliceArea> findAll() {
		ArrayList<PoliceArea> policeAreas=new ArrayList<PoliceArea>();
		String key=getApiKey();
		String responseJSON=resource
				.path("policearea")
				.path("all")
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		JSONArray jsonArray=new JSONArray(responseJSON);
		PoliceArea policeaArea =null;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj=(JSONObject) jsonArray.get(i);
				int id = obj.getInt("id");
				String name = obj.getString("areaName");
				policeaArea = new PoliceArea();
				policeaArea.setId(id);
				policeaArea.setAreaName(name);
				policeAreas.add(policeaArea);
				}
		} catch (Exception e) {
			System.out.println("Erreur dans PoliceAreaDAO du client : "+e.getMessage());
		}
		return policeAreas;
	}
}
