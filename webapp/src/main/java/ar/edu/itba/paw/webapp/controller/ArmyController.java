package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.DTOs.ArmyAttackDTO;
import ar.edu.itba.paw.webapp.DTOs.ArmyDTO;
import ar.edu.itba.paw.webapp.DTOs.ArmyTrainDTO;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import ar.edu.itba.paw.webapp.data.Info;
import ar.edu.itba.paw.webapp.data.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
  private TroopService ts;
  @Autowired
  private EmpireService es;
  @Autowired
  private UserService us;
  @Autowired
  private ScheduleService sh;
  @Autowired
  private AlertService als;
  @Autowired
  private MessageSource messageSource;
  @Autowired
  private PAWMailService mailService;
  @Autowired
  private MessageService ms;

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
  public Response attack(ArmyAttackDTO attackDTO) {
    User user = AuthenticatedUser.getUser(us);
    int xPrime = attackDTO.getPoint().getX()-1;
    int yPrime = attackDTO.getPoint().getY()-1;
    Point point = new Point(xPrime, yPrime);
    if(!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Army army = as.getArmyById(attackDTO.getArmyId());
    if(army==null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else if (!army.getUser().equals(user)) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    Sector sector = ss.getSector(point);
    if(sector==null || sector.getUser().equals(user) || !army.getAvailable() || sector.getType() == Info.EMPTY || sector.getType() == Info.TERR_GOLD) {
      // TODO return a custom message for each
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    sh.attackTask(user, point, army.getIdArmy());
    as.setAvailable(army.getIdArmy(), false);
    return Response.noContent().build();
  }

  @POST
  @Path("/train")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response train(ArmyTrainDTO trainDTO) {
    User user = AuthenticatedUser.getUser(us);
    Point point = new Point(trainDTO.getPoint().getX()-1, trainDTO.getPoint().getY()-1);
    if (!Validator.validBoardPosition(point)) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    Sector sector = ss.getSector(point);
    return null;
  }

  @RequestMapping(value = "/train", method = RequestMethod.POST)
  public ModelAndView train(@RequestParam String type,
                            @RequestParam String amount,
                            @RequestParam String px,
                            @RequestParam String py,
                            @ModelAttribute("userId") final User user,
                            Locale locale) {
    if (user == null) {
      return new ModelAndView("redirect:/");
    }
    if (!Validator.isInteger(type) || !Validator.isInteger(amount)
      || !Validator.validBoardPosition(px) || !Validator.validBoardPosition(py)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidParam", null, locale));
    }
    int a = Integer.valueOf(amount);
    int x = Integer.valueOf(px);
    int y = Integer.valueOf(py);
    Point p = new Point(x, y);
    Sector s = ss.getSector(p);
    int troopType = Integer.valueOf(type);
    int cost = 0;
    switch (troopType) {
      case Info.WARRIOR:
        cost = (Info.COST_WARRIOR - (s.getLevel() - 1)) * a;
        break;
      case Info.ARCHER:
        cost = (Info.COST_ARCHER - (s.getLevel() - 1)) * a;
        break;
      case Info.HORSEMAN:
        cost = (Info.COST_HORSEMAN - (s.getLevel() - 1)) * a;
        break;
      default:
        return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidTroop", null, locale));
    }
    boolean resp = false;
    if (als.getAlertByPoint(p) != null) {
      return new ModelAndView("redirect:/error");
    }
    if (es.subtractResourceAmount(user, Info.RES_FOOD, cost)) {
      resp = true;
      sh.TrainTask(user, p, a, troopType);
    }

    if (resp) {
      return new ModelAndView("redirect:/");

    } else {
      return new ModelAndView("redirect:/building?x=" + x + "&y=" + y + "&e=" + messageSource.getMessage("error.noFood", null, locale));
    }

  }

  @RequestMapping(value = "/merge")
  public ModelAndView merge(@RequestParam String f,
                            @RequestParam String t,
                            @ModelAttribute("userId") final User user,
                            Locale locale) {
    if (user == null) {
      return new ModelAndView("redirect:/");
    }
    if (!Validator.isInteger(f) || !Validator.isInteger(t)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidParam", null, locale));
    }
    int from = Integer.valueOf(f);
    int to = Integer.valueOf(t);
    if (!as.belongs(user, from) || !as.belongs(user, to)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.notYoursArmy", null, locale));
    }
    sh.mergeTask(user, from, to, as.getArmyById(to).getPosition());
    as.setAvailable(from, false);
    return new ModelAndView("redirect:/armies");
  }

  @RequestMapping(value = "/armies/{armyId}/split")
  public ModelAndView split(@PathVariable String armyId,
                            @ModelAttribute("userId") final User user,
                            Locale locale) {
    if (user == null) {
      return new ModelAndView("redirect:/");
    }
    if (!Validator.isInteger(armyId)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidArmy", null, locale));
    }
    if (!as.belongs(user, Integer.valueOf(armyId))) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.notYoursArmy", null, locale));
    }
    ModelAndView mav = new ModelAndView("split");
    Army a = as.getArmyById(Integer.parseInt(armyId));
    List<Point> points = new ArrayList<>();
    for (Sector b : ss.getAllBuildings(user)) {
      if (!b.getPosition().equals(a.getPosition())) {
        points.add(b.getPosition());
      }
    }
    int unreadMessages = ms.countUnreadMessages(user);

    mav.addObject("unreadMessages", unreadMessages);
    mav.addObject("user", user);
    mav.addObject("army", a);
    mav.addObject("possiblePoints", points);
    return mav;
  }

  @RequestMapping(value = "/split", method = RequestMethod.POST)
  public ModelAndView split2(@RequestParam(value = "0", required = false, defaultValue = "0") String t1,
                             @RequestParam(value = "1", required = false, defaultValue = "0") String t2,
                             @RequestParam(value = "2", required = false, defaultValue = "0") String t3,
                             @RequestParam(value = "pos") String pos,
                             @RequestParam(value = "armyId") String armyId,
                             @ModelAttribute("userId") final User user,
                             Locale locale) {
    String[] po = pos.split(",");
    if (!Validator.validBoardPosition(po[0]) || !Validator.validBoardPosition(po[1])) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidPosition", null, locale));
    }
    Point p = new Point(Integer.parseInt(po[0]), Integer.parseInt(po[1]));
    if (!Validator.isInteger(t1) || !Validator.isInteger(t2) || !Validator.isInteger(t3)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidTroopAmount", null, locale));
    }
    if (!Validator.isInteger(armyId)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidArmy", null, locale));
    }
    int id = Integer.parseInt(armyId);
    if (!as.belongs(user, id)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.notYoursArmy", null, locale));
    }
    int warriors = Integer.parseInt(t1);
    int archers = Integer.parseInt(t2);
    int horsemen = Integer.parseInt(t3);
    Map<TroopType, Integer> tr = new HashMap<>();
    tr.put(TroopType.warrior, warriors);
    tr.put(TroopType.archer, archers);
    tr.put(TroopType.horseman, horsemen);
    Army a = as.splitArmy(id, tr);
    sh.splitTask(user, a.getIdArmy(), p);
    return new ModelAndView("redirect:/armies");
  }

  @RequestMapping(value = "/move", method = RequestMethod.POST)
  public ModelAndView move(@ModelAttribute("userId") final User user,
                           @RequestParam(value = "x", required = true) String x,
                           @RequestParam(value = "y", required = true) String y,
                           @RequestParam(value = "armyId", required = true) String armyId,
                           Locale locale) {

    if (!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidPosition", null, locale));
    }
    Point p = new Point(Integer.parseInt(x), Integer.parseInt(y));
    if (!Validator.isInteger(armyId)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.invalidArmy", null, locale));
    }
    int id = Integer.parseInt(armyId);
    if (!as.belongs(user, id)) {
      return new ModelAndView("redirect:/error?m=" + messageSource.getMessage("error.notYoursArmy", null, locale));
    }
    as.setAvailable(id, false);
    sh.moveTask(user, id, p);
    return new ModelAndView("redirect:/map");

  }

  @ModelAttribute("userId")
  public User loggedUser(final HttpSession session) {
    if (session.getAttribute("userId") != null)
      return us.findById((Integer) session.getAttribute("userId"));
    return null;
  }

}
