package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.DTOs.BuildDTO;
import ar.edu.itba.paw.webapp.DTOs.BuildingDTO;
import ar.edu.itba.paw.webapp.DTOs.TileDTO;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import ar.edu.itba.paw.webapp.data.Info;
import ar.edu.itba.paw.webapp.data.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/buildings")
@Controller
public class BuildingController {

  @Autowired
  private SectorService ss;
  @Autowired
  private EmpireService es;
  @Autowired
  private UserService us;
  @Autowired
  private ScheduleService sh;
  @Autowired
  private AlertService as;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllBuildings() {
    User user = AuthenticatedUser.getUser(us);
    List<TileDTO> buildingDTOList = new ArrayList<>();
    ss.getAllBuildings(user).forEach(b -> buildingDTOList.add(new TileDTO(b)));
    return Response.ok().entity(buildingDTOList).build();
  }

  @GET
  @Path("/{x}/{y}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBuilding(@PathParam("x") final int x, @PathParam("y") final int y) {
    User user = AuthenticatedUser.getUser(us);
    Point p = new Point(x, y);
    Sector sector = ss.getSector(p);
    if (sector == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    Alert a = as.getAlertByPoint(p);
    boolean beingConstructed = sector.getUser().getId() == user.getId() && a != null && a.getType().equals("BUILD");
    return Response.ok().entity(new BuildingDTO(sector, user.getId(), es.validCastlePosition(p), ss.getCastlePrice(user), beingConstructed)).build();
  }

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response build(BuildDTO buildDTO) {
    User user = AuthenticatedUser.getUser(us);
    Point p = new Point(buildDTO.getPosition().getX(), buildDTO.getPosition().getY());
    if (!Validator.validBoardPosition(p)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Sector s = ss.getSector(p);
    if (!user.equals(s.getUser())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    if (s.getType() != Info.TERR_GOLD && s.getType() != Info.EMPTY) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    Alert alert = as.getAlertByPoint(p);
    if(alert != null && alert.getType().equals("BUILD")) {
      return Response.status(Response.Status.FORBIDDEN).build();//.entity(new ErrorDTO("ALREADY_TRAINING")).build();
    }
    if (es.build(user, p.getX(), p.getY(), buildDTO.getType())) {
      return Response.status(Response.Status.NO_CONTENT).build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  @DELETE
  @Path("/{x}/{y}")
  public Response demolish(@PathParam("x") final int x, @PathParam("y") final int y) {
    User user = AuthenticatedUser.getUser(us);
    Point p = new Point(x, y);
    if (!Validator.validBoardPosition(p)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Sector s = ss.getSector(p);
    if (!s.getUser().equals(user)) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    ss.deleteBuilding(p);
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @PUT
  @Path("/{x}/{y}")
  public Response levelUp(@PathParam("x") final int x, @PathParam("y") final int y) {
    User user = AuthenticatedUser.getUser(us);
    Point p = new Point(x, y);
    if (!Validator.validBoardPosition(p)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Sector s = ss.getSector(p);
    if (!s.getUser().equals(user)) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    int price = ss.getPrice(user) + (int) Math.pow(s.getLevel(), 4);
    int gold = es.getResource(user, Info.RES_GOLD).getQuantity();
    if (s.getLevel() >= 20 || gold < price) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    sh.levelUpTask(s);
    es.subtractResourceAmount(user, Info.RES_GOLD, price);
    return Response.status(Response.Status.NO_CONTENT).build();
  }
}
