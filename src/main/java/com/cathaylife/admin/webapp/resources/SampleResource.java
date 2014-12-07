package com.cathaylife.admin.webapp.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

@Path("/sample")
@Component
public class SampleResource {

	@GET
	public Response sample(){
		return Response.ok("Sample", MediaType.APPLICATION_JSON_TYPE).build();
	}
	
}
