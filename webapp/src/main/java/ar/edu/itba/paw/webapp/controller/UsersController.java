package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.ErrorDTO;
import ar.edu.itba.paw.webapp.DTOs.UserCreateDTO;
import ar.edu.itba.paw.webapp.DTOs.UserDTO;
import ar.edu.itba.paw.webapp.DTOs.UserScoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("users")
@Controller
public class UsersController {

  @Autowired
  private UserService us;

  @Autowired
  private EmpireService es;

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
  public Response getUser(@PathParam("id") int id) {
    User user = us.findById(id);
    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok().entity(new UserDTO(user)).build();
  }

  @GET
  @Path("/score")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getScores() {
    final List<UserScoreDTO> scores = new ArrayList<>();
    us.getAllUsers().forEach(u -> scores.add(new UserScoreDTO(u, es.calculateScore(u))));
    return Response.ok().entity(scores).build();
  }

  @GET
  @Path("/score/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserScore(@PathParam("id") int id) {
    User user = us.findById(id);
    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok().entity(new UserScoreDTO(user, es.calculateScore(user))).build();
  }

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createUser(UserCreateDTO createDTO) {
    if (us.exists(createDTO.getUsername())) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("USER_ALREADY_EXIST")).build();
    }
    User user = us.create(createDTO.getUsername(), createDTO.getPassword(), createDTO.getEmail());
    if (user == null) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("NO_CASTLE_SPACE")).build();
    }
    return Response.status(Response.Status.NO_CONTENT).build();
  }
}
