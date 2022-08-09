package be.project.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import be.project.models.Collector;
import be.project.models.PoliceArea;


@Path("/collector")
public class CollectorAPI extends API {

	public CollectorAPI() {
		// TODO Auto-generated constructor stub
	}
	
	
	@GET
	@Path("{serialNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCollector(@PathParam("serialNumber") String serialNumber,
			@HeaderParam("key") String key) {
		String apiKey=getApiKey();
		if(key!=null) {
			if(key.equals(apiKey)) {
				Collector collector=Collector.getCollector(serialNumber);
				return Response.status(Status.OK).entity(collector).build();
			}
		}
		return Response.status(Status.UNAUTHORIZED).build();
	}
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response createCollector(
			@FormParam("firstname") String firstname,
			@FormParam("lastname") String lastname,
			@FormParam("password") String password,
			@FormParam("police_area_id") int policeAreaId,
			@HeaderParam("key") String key)
	{
		Collector collector;
		PoliceArea policeArea = new PoliceArea();
		policeArea.setId(policeAreaId);
		String apiKey=getApiKey();
		if(key.equals(apiKey)) {
			try {
				collector = new Collector(firstname,lastname, password, policeArea);
				String userSerialNumber=collector.createCollector();
				if(!userSerialNumber.isEmpty()) {
					return Response
							.status(Status.CREATED)
							.build();
				}else {
					return Response.status(Status.SERVICE_UNAVAILABLE).build();
				}
		
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return Response.status(Status.UNSUPPORTED_MEDIA_TYPE).build();
			}
		}else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}
	
}