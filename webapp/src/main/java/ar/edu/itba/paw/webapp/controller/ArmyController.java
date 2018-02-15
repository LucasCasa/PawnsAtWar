package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.DTOs.*;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import ar.edu.itba.paw.webapp.data.Info;
import ar.edu.itba.paw.webapp.data.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("armies")
@Controller
public class ArmyController {

  @Autowired
  private ArmyService as;
  @Autowired
  private SectorService ss;
  @Autowired
  private EmpireService es;
  @Autowired
  private UserService us;
  @Autowired
  private ScheduleService sh;
  @Autowired
  private AlertService als;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getArmies() {
    User user = AuthenticatedUser.getUser(us);
    List<ArmyDTO> armies = new ArrayList<>();
    as.getArmies(user).forEach(army -> armies.add(new ArmyDTO(army)));
    return Response.ok().entity(armies).build();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getArmyById(@PathParam("id") final int id) {
    User user = AuthenticatedUser.getUser(us);
    Army army = as.getArmyById(id);
    if (army == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else if (!army.getUser().equals(user)) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    return Response.ok().entity(new ArmyDTO(army)).build();
  }

  @POST
  @Path("/move")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response moveArmy(ArmyMoveDTO moveDTO){
    User user = AuthenticatedUser.getUser(us);
    Point point = new Point(moveDTO.getPoint().getX(), moveDTO.getPoint().getY());
    if (!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("INVALID_POSITION")).build();
    }
    Army army = as.getArmyById(moveDTO.getArmyId());
    if(army == null){
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("INVALID_ARMY")).build();
    }
    if (!user.equals(army.getUser())) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("NOT_YOUR_ARMY")).build();
    }
    if(!army.getAvailable()){
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("ARMY_UNAVAILABLE")).build();
    }
    User owner = ss.getPlayer(point);
    if(owner == null){
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("INVALID_POSITION")).build();
    }
    if(user.equals(owner)) {
      Army otherArmy = as.getArmyAtPosition(user, point);
      if(otherArmy != null){
        return merge(user, point, army, otherArmy);
      }
      return move(user, point, army);
    }
    return attack(user, point, army);
  }
  private Response move(User user, Point point, Army army) {
    Sector sector = ss.getSector(point);
    if(sector.getType() == SectorType.EMPTY.getType() || sector.getType() == SectorType.TERR_GOLD.getType()){
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("INVALID_POSITION")).build();
    }

    as.setAvailable(army.getIdArmy(), false);
    sh.moveTask(user, army.getIdArmy(), point);
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  private Response merge(User user, Point to, Army thisArmy, Army otherArmy) {
    sh.mergeTask(user, thisArmy.getIdArmy(), otherArmy.getIdArmy(), to);
    as.setAvailable(thisArmy.getIdArmy(), false);
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  private Response attack(User user, Point point, Army army) {
    Sector sector = ss.getSector(point);
    if (sector.getType() == Info.EMPTY || sector.getType() == Info.TERR_GOLD) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("ATTACK_EMPTY_TERRAIN")).build();
    } else if (sector.getType() == Info.CASTLE && !ss.isCastleAlone(point, 3)) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("CANT_ATTACK_CASTLE")).build();
    }
    sh.attackTask(user, point, army.getIdArmy());
    as.setAvailable(army.getIdArmy(), false);
    return Response.noContent().build();
  }

  @POST
  @Path("/split")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response split(ArmySplitDTO splitDTO) {
    User user = AuthenticatedUser.getUser(us);
    Point point = new Point(splitDTO.getPoint().getX(), splitDTO.getPoint().getY());
    if(!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("INVALID_POSITION")).build();
    }
    if(!user.equals(ss.getPlayer(point))){
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("NOT_YOUR_TERRAIN")).build();
    }
    Sector s = ss.getSector(point);
    if(s.getType() == SectorType.EMPTY.getType() || s.getType() == SectorType.TERR_GOLD.getType()){
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("NOT_BUILDING_IN_POSITION")).build();
    }

    if(!as.belongs(user, splitDTO.getArmyId())) {
      return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorDTO("NOT_YOUR_ARMY")).build();
    }
    Map<TroopType, Integer> tr = new HashMap<>();
    splitDTO.getTroops().forEach(u -> tr.put(TroopType.get(u.getType()), u.getAmount()));
    Army newArmy = as.splitArmy(splitDTO.getArmyId(), tr); // newArmy starts as unavailable
    sh.splitTask(user, newArmy.getIdArmy(), point);
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @POST
  @Path("/train")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response train(ArmyTrainDTO trainDTO) {
    User user = AuthenticatedUser.getUser(us);
    Point point = new Point(trainDTO.getPoint().getX(), trainDTO.getPoint().getY());
    if (!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("INVALID_POSITION")).build();
    }
    Sector sector = ss.getSector(point);
    if(!user.equals(sector.getUser())){
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("NOT_YOUR_TERRAIN")).build();
    }
    int cost;
    int type;
    switch (sector.getType()) {
      case Info.BARRACKS:
        cost = (Info.COST_WARRIOR - (sector.getLevel() - 1)) * trainDTO.getAmount();
        type = Info.WARRIOR;
        break;
      case Info.ARCHERY:
        cost = (Info.COST_ARCHER - (sector.getLevel() - 1)) * trainDTO.getAmount();
        type = Info.ARCHER;
        break;
      case Info.STABLE:
        cost = (Info.COST_HORSEMAN - (sector.getLevel() - 1)) * trainDTO.getAmount();
        type = Info.HORSEMAN;
        break;
      default:
        return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("INVALID_TROOP_TYPE")).build();
    }
    Alert alert = als.getAlertByPoint(point);
    if(alert != null && alert.getType().equals("RECRUIT")) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("ALREADY_TRAINING")).build();
    }
    if (es.subtractResourceAmount(user, Info.RES_FOOD, cost)) {
      sh.TrainTask(user, point, trainDTO.getAmount(), type);
      return Response.noContent().build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("NO_FOOD")).build();
    }
  }
}
