package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.AlertDTO;
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

@Path("alerts")
@Controller
public class AlertController {

  @Autowired
  UserService us;

  @Autowired
  AlertService as;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAlerts() {
    User user = AuthenticatedUser.getUser(us);
    List<AlertDTO> alerts = new ArrayList<>();
    as.getByUser(user).forEach(a -> alerts.add(new AlertDTO(a)));
    return Response.ok().entity(alerts).build();
  }
}
