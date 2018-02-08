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
  @Path("/attack")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response attack(ArmyAttackDTO attackDTO) {
    User user = AuthenticatedUser.getUser(us);
    int xPrime = attackDTO.getPoint().getX() - 1;
    int yPrime = attackDTO.getPoint().getY() - 1;
    Point point = new Point(xPrime, yPrime);
    if (!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Army army = as.getArmyById(attackDTO.getArmyId());
    if (army == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else if (!army.getUser().equals(user)) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    Sector sector = ss.getSector(point);
    if (sector.getUser().equals(user)) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("ATTACK_SELF_BUILDING")).build();
    } else if (!army.getAvailable()) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("ARMY_UNAVAILABLE")).build();
    } else if (sector.getType() == Info.EMPTY || sector.getType() == Info.TERR_GOLD) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("ATTACK_EMPTY_TERRAIN")).build();
    } else if (sector.getType() == Info.CASTLE && !ss.isCastleAlone(point, 3)) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("CANT_ATTACK_CASTLE")).build();
    }
    sh.attackTask(user, point, army.getIdArmy());
    as.setAvailable(army.getIdArmy(), false);
    return Response.noContent().build();
  }

  @POST
  @Path("/train")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response train(ArmyTrainDTO trainDTO) {
    User user = AuthenticatedUser.getUser(us);
    Point point = new Point(trainDTO.getPoint().getX(), trainDTO.getPoint().getY());
    if (!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Sector sector = ss.getSector(point);
    int cost;
    switch (trainDTO.getType()) {
      case Info.WARRIOR:
        cost = (Info.COST_WARRIOR - (sector.getLevel() - 1)) * trainDTO.getAmount();
        break;
      case Info.ARCHER:
        cost = (Info.COST_ARCHER - (sector.getLevel() - 1)) * trainDTO.getAmount();
        break;
      case Info.HORSEMAN:
        cost = (Info.COST_HORSEMAN - (sector.getLevel() - 1)) * trainDTO.getAmount();
        break;
      default:
        return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("INVALID_TROOP_TYPE")).build();
    }
    Alert alert = als.getAlertByPoint(point);
    if(alert != null && alert.getType().equals("RECRUIT")) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("ALREADY_TRAINING")).build();
    }
    if (es.subtractResourceAmount(user, Info.RES_FOOD, cost)) {
      sh.TrainTask(user, point, trainDTO.getAmount(), trainDTO.getType());
      return Response.noContent().build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).entity("NO_FOOD").build();
    }
  }

  @POST
  @Path("/move")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response move(ArmyMoveDTO moveDTO) {
    User user = AuthenticatedUser.getUser(us);
    Point point = new Point(moveDTO.getPoint().getX(), moveDTO.getPoint().getY());
    if (!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Sector sector = ss.getSector(point);
    if (!user.equals(sector.getUser())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    if (as.getArmyById(moveDTO.getArmyId()) == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    if (!as.belongs(user, moveDTO.getArmyId())) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("NOT_YOUR_ARMY")).build();
    }
    as.setAvailable(moveDTO.getArmyId(), false);
    sh.moveTask(user, moveDTO.getArmyId(), point);
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @POST
  @Path("/merge")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response merge(ArmyMergeDTO mergeDTO) {
    User user = AuthenticatedUser.getUser(us);
    if(!as.belongs(user, mergeDTO.getFromId()) || !as.belongs(user, mergeDTO.getToId())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    sh.mergeTask(user, mergeDTO.getFromId(), mergeDTO.getToId(), as.getArmyById(mergeDTO.getToId()).getPosition());
    as.setAvailable(mergeDTO.getFromId(), false);
    return Response.status(Response.Status.NO_CONTENT).build();
  }

  @POST
  @Path("split")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response split(ArmySplitDTO splitDTO) {
    User user = AuthenticatedUser.getUser(us);
    Point point = new Point(splitDTO.getPosition().getX(), splitDTO.getPosition().getY());
    if(!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    if(!as.belongs(user, splitDTO.getArmyId())) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    Map<TroopType, Integer> tr = new HashMap<>();
    splitDTO.getUnits().forEach(u -> tr.put(TroopType.get(u.getType()), u.getAmount()));
    Army newArmy = as.splitArmy(splitDTO.getArmyId(), tr); // newArmy starts as unavailable
    sh.splitTask(user, newArmy.getIdArmy(), point);
    return Response.status(Response.Status.NO_CONTENT).build();
  }
}
