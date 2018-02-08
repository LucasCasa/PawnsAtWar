package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.ResourceDTO;
import ar.edu.itba.paw.webapp.DTOs.ResourceInfoDTO;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("resources")
@Controller
public class ResourcesController {

  @Autowired
  private UserService us;

  @Autowired
  private EmpireService es;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getResources() {
    User user = AuthenticatedUser.getUser(us);
    List<ResourceDTO> resourceDTOS = new ArrayList<>();
    es.getResources(user).forEach(r -> resourceDTOS.add(new ResourceDTO(r, es.getRate(user, r.getType()))));
    return Response.ok().entity(new ResourceInfoDTO(es.getMaxStorage(user), resourceDTOS)).build();
  }
}
