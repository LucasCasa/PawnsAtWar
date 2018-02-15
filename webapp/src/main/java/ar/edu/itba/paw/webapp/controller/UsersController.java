package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.*;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Path("users")
@Controller
public class UsersController {

  @Autowired
  private UserService us;

  @Autowired
  private EmpireService es;

  @Autowired
  private SectorService ss;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUsers() {
    final List<UserDTO> users = new ArrayList<>();
    us.getAllUsers().forEach(user -> users.add(new UserDTO(user)));
    return Response.ok().entity(users).build();
  }

  @GET
  @Path("/{userName}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUsernamesByOccurence(@PathParam("userName") String userName){
    final List<UserDTO> users = new ArrayList<>();
    us.getUsernames(userName).forEach(user -> users.add(new UserDTO(new User(user, null, null))));
    return Response.ok().entity(new UserListDTO(users)).build();
  }

  /*@GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUser(@PathParam("id") int id) {
    User user = us.findById(id);
    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok().entity(new UserDTO(user)).build();
  }*/

  @GET
  @Path("/score")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getScores() {
    final Set<UserScoreDTO> scores = new TreeSet<>((UserScoreDTO a, UserScoreDTO b) -> (int)((b.getScore() - a.getScore() == 0)? b.getUsername().compareTo(a.getUsername()) : b.getScore() - a.getScore()));
    us.getAllUsers().forEach(u -> scores.add(new UserScoreDTO(u, ss.getCastle(u), es.calculateScore(u))));
    return Response.ok().entity(scores.toArray()).build();
  }

  @GET
  @Path("/score/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserScore(@PathParam("id") int id) {
    User user = us.findById(id);
    if (user == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    return Response.ok().entity(new UserScoreDTO(user,ss.getCastle(user), es.calculateScore(user))).build();
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
    us.setLocale(user, createDTO.getLocale());
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @PUT
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response startAgain(){
    User user = AuthenticatedUser.getUser(us);
    if(us.restoreUser(user)){
      return Response.status(Response.Status.NO_CONTENT).build();
    } else {
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("NO_PLACE")).build();
    }
  }
}
