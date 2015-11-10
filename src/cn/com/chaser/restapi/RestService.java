package cn.com.chaser.restapi;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/restService")
public class RestService {
	@GET
	@Path("/getInfo")
	@Produces(MediaType.TEXT_PLAIN)
	public String getWebServiceInfo() {
		return "Hello,RESTful web service1213!";
	}
	

	@GET
	@Path("/{parameter}")
	public Response respondMessage(@PathParam("parameter") String parameter,
			@DefaultValue("Nothing to say") @QueryParam("value") String value) {
		String out = "Hello from " + parameter + ":" + value;
		return Response.status(200).entity(out).build();
	}
	@POST @Consumes("application/json")
	@Path("/create")
	public void create(final vauleStruct input) {
	    System.out.println("param1 = " + input.param1);
	    System.out.println("param2 = " + input.param2);
	}
		  
	
}
