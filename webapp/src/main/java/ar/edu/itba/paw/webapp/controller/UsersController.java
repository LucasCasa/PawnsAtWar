package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.beans.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("users")
@Controller
public class UsersController {

	@Autowired
	private UserService us;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		final List<UserDTO> users = new ArrayList<>();
		us.getAllUsers().forEach(user -> users.add(new UserDTO(user)));
		return Response.ok().entity(users).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") String id) {
		try {
			User user = us.findById(Integer.parseInt(id));
			if (user == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return Response.ok().entity(new UserDTO(user)).build();
		} catch (NumberFormatException NFe) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

}
