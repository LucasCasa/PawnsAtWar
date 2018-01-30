package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.paw.webapp.DTOs.MapDTO;
import ar.edu.itba.paw.webapp.data.Info;
import ar.edu.itba.paw.webapp.data.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("map")
@Controller
public class MapController {

	@Autowired
	SectorService ss;

	@GET
	@Path("/{x}/{y}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMap(@PathParam("x") final int x, @PathParam("y") final int y) {
		int xPrime = Validator.getValidPos(x-1);
		int yPrime = Validator.getValidPos(y-1);
		List<List<Sector>> elements = ss.getSector(new Point(xPrime,yPrime), Info.VIEW_RANGE);
		return Response.ok().entity(new MapDTO(elements, xPrime+1, yPrime+1)).build();
	}
}
