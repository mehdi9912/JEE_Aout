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

import be.project.javabeans.VehiculeType;

public class VehiculeTypeDAO implements DAO<VehiculeType> {
	
	private static  String apiUrl;
	private Client client;
	private WebResource resource;
	
	private static URI getBaseUri() {
		return UriBuilder.fromUri(apiUrl).build();
	}
	
	public VehiculeTypeDAO() {
		ClientConfig config=new DefaultClientConfig();
		client = Client.create(config);
		apiUrl=getApiUrl();
		resource=client.resource(getBaseUri());
	}


	@Override
	public boolean insert(VehiculeType obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("type", obj.getType());
		ClientResponse res=resource
				.path("vehiculetype")
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
				.path("vehiculetype")
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
	public boolean update(VehiculeType obj) {
		boolean success=false;
		String key=getApiKey();
		MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
		parameters.add("type",obj.getType());
		ClientResponse res=resource
				.path("vehiculetype")
				.path("update")
				.path(String.valueOf(obj.getId()))
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
	public VehiculeType find(String id) {
		String key=getApiKey();
		String responseJSON=resource
				.path("vehiculetype")
				.path(id)
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		VehiculeType vehiculeType=null;
		ObjectMapper mapper=new ObjectMapper();
		try {
			vehiculeType=(VehiculeType) mapper.readValue(responseJSON, VehiculeType.class);
			return vehiculeType;
		} catch (Exception e) {
			System.out.println("error = "+e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<VehiculeType> findAll() {
		ArrayList<VehiculeType> vehicules=new ArrayList<VehiculeType>();
		String key=getApiKey();
		String responseJSON=resource
				.path("vehiculetype")
				.path("all")
				.header("key",key)
				.accept(MediaType.APPLICATION_JSON)
				.get(String.class);
		
		JSONArray jsonArray=new JSONArray(responseJSON);
		VehiculeType vehiculeType;
		try {
			for(int i=0;i<jsonArray.length();i++) {
				JSONObject obj=(JSONObject) jsonArray.get(i);
				int id = obj.getInt("id");
				String type = obj.getString("type");
				vehiculeType = new VehiculeType(id, type);
				vehicules.add(vehiculeType);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans vehiculetypeDAO du client : "+e.getMessage());
		}
		return vehicules;
	}

}
