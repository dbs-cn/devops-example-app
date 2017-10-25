package com.dxc.rest.service;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.dxc.rest.response.MyResponse;

@Path("/person")
public class PersonService {

	@GET
	@Path("/{param}")
	public Response getPerson(@PathParam("param") String msg) throws IOException {
		MyResponse myResponse = new MyResponse();
		return Response.status(200).entity(myResponse.getResponse(msg)).build();

	}

}
