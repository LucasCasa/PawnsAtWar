package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.dataClasses.Info;
import ar.edu.itba.paw.webapp.dataClasses.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.ValidationMessage;
import java.util.*;

import static java.lang.System.out;

/**
 * Created by Team Muffin on 18/09/16.
 */
@Controller
public class ArmyController {

    @Autowired
    private ArmyService as;
    @Autowired
    private SectorService ss;
    @Autowired
    private TroopService ts;
    @Autowired
    private UserService us;
    @Autowired
    private EmpireService es;
    @Autowired
    private BuildingService bs;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value="/armies")
    public ModelAndView showArmies(@ModelAttribute("user") final User user,
                                   @RequestParam(value= "x",required = false) String x ,
                                   @RequestParam(value= "y",required = false) String y){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        final ModelAndView mav = new ModelAndView("armies");
        List<Army> armies;
        armies = as.getArmies(user.getId());

        if(Validator.validBoardPosition(x) && Validator.validBoardPosition(y)){
            mav.addObject("x",x);
            mav.addObject("y",y);
        }
        if(armies == null){
            armies = new ArrayList<>();
        }
        mav.addObject("armies",armies);
        mav.addObject("size",armies.size());
        return mav;
    }

    @RequestMapping(value="/armies/{armyId}")
    public ModelAndView showArmy(@PathVariable String armyId,
                                 @RequestParam(value= "x",required = false) String x ,
                                 @RequestParam(value= "y",required = false) String y,
                                 @ModelAttribute("user") final User user,
                                 Locale locale){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        final ModelAndView mav = new ModelAndView("army");

        if(Validator.validBoardPosition(x) && Validator.validBoardPosition(y)){
            mav.addObject("x",x);
            mav.addObject("y",y);
        }else{

            mav.addObject("x","");
            mav.addObject("y","");
        }

        if(armyId == null || !Validator.isInteger(armyId)) {
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
        }
        int id = Integer.parseInt(armyId);

        Army army = as.getArmyById(id);
        if(army == null){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notExistArmy",null,locale));
        }
        if(!as.belongs(user.getId(),id)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
        }
        List<Army> armies = as.getArmies(user.getId());
        if(armies == null){
            armies = new ArrayList<>();
        }
        List<Troop> troops = army.getTroops();
        mav.addObject("armies",armies);
        mav.addObject("armySize",armies.size());
        mav.addObject("army",army);
        mav.addObject("troops",troops);
        return mav;
    }

    @Transactional
    @RequestMapping(value="/attack", method = RequestMethod.POST)
    public ModelAndView attack(@RequestParam String x,
                               @RequestParam String y,
                               @RequestParam String army,
                               @ModelAttribute("user") final User user,
                               Locale locale){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        final ModelAndView mav = new ModelAndView("attack");
        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y) ){
            return new ModelAndView("redirect:/error?"+ messageSource.getMessage("error.invalidPosition",null,locale));
        }
        if(!Validator.isInteger(army)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
        }
        int id = Integer.parseInt(army);
        if(!as.belongs(user.getId(),id)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
        }
        int xprime = Integer.parseInt(x);
        int yprime = Integer.parseInt(y);



        Sector s = ss.getSector(new Point(xprime,yprime));
        if(s == null){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notBuildingInPosition",null,locale));

        }
        if(s.getUser() == null){
        	return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.noUserInPosition",null,locale));
        }
        if(s.getUser().getId() == user.getId()){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackSelfBuilding",null,locale));
        }else if(s.getType() == 0 || s.getType() == 5){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackTerrain",null,locale));
        }else {
            if(s.getType() == Info.CASTLE){
                if(!ss.isCastleAlone(new Point(xprime,yprime),3)){
                    return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.attackCastle",null,locale));
                }
            }
            Army d = as.getStrongest(s.getUser().getId());
            Army a = as.getArmyById(id);
            Map<String,Object> values = new HashMap<>();
            values.put("a0b",0);
            values.put("a1b",0);
            values.put("a2b",0);
            values.put("d0b",0);
            values.put("d1b",0); // QUE WASADA POR DIOS
            values.put("d2b",0);
            values.put("a0l",0);
            values.put("a1l",0);
            values.put("a2l",0);
            values.put("d0l",0);
            values.put("d1l",0);
            values.put("d2l",0);
            if(d != null){
                int defenderP = (int) ts.getValue(d.getIdArmy());
                int attackerP = (int) ts.getValue(id);
                int loserP;
                Army adef;
                Army awin;
                String prefixW;
                String prefixD;
                if(defenderP > attackerP){
                    mav.addObject("result",messageSource.getMessage("defenderWin",null,locale));
                    adef = a;
                    awin = d;
                    loserP = attackerP;
                    prefixD ="a";
                    prefixW ="d";
                }else if(attackerP > defenderP){
                    mav.addObject("result",messageSource.getMessage("attackerWin",null,locale));
                    awin = a;
                    adef = d;
                    loserP = defenderP;
                    prefixD ="d";
                    prefixW ="a";
                    ss.deleteBuilding(new Point(xprime,yprime));
                }else{
                    mav.addObject("result",messageSource.getMessage("draw",null,locale));
                    for(Troop t : a.getTroops()){
                        values.put("a"+t.getType()+"b",t.getQuantity());
                        values.put("a"+t.getType()+"l",t.getQuantity());
                    }
                    for(Troop t : d.getTroops()){
                        values.put("d"+t.getType()+"b",t.getQuantity());
                        values.put("d"+t.getType()+"l",t.getQuantity());
                    }
                    as.deleteArmy(id);
                    as.deleteArmy(d.getIdArmy());
                    mav.addAllObjects(values);
                    return mav;
                }
                List<Troop> defeated = adef.getTroops();
                as.deleteArmy(adef.getIdArmy());
                List<Troop> winner = awin.getTroops();
                for(Troop t : winner){
                    if(t.getQuantity()*(t.getType()+1) > loserP){
                        values.put(prefixW+t.getType()+"b",t.getQuantity());
                        ts.subtractTroop(awin.getIdArmy(),t.getType(),loserP/(t.getType()+1));
                        values.put(prefixW+t.getType()+"l",loserP/(t.getType()+1));
                        break;
                    }else{
                        values.put(prefixW+t.getType()+"b",t.getQuantity());
                        values.put(prefixW+t.getType()+"l",t.getQuantity());
                        ts.deleteTroop(awin.getIdArmy(),t.getType());
                        loserP-= (t.getType()+1)*t.getQuantity();

                    }
                }
                for(Troop t: defeated){
                    values.put(prefixD+t.getType()+"b",t.getQuantity());
                    values.put(prefixD+t.getType()+"l",t.getQuantity());
                }
                mav.addAllObjects(values);
                return mav;
            }
            mav.addObject("result",messageSource.getMessage("noDefenderArmy",null,locale));
            for(Troop t : ts.getTroopById(id)){
                values.put("a"+t.getType()+"b",t.getQuantity());
                values.put("a"+t.getType()+"l",0);
            }
            ss.deleteBuilding(new Point(xprime,yprime));
            mav.addAllObjects(values);
        }
        return mav;
    }

    @Transactional
    @RequestMapping(value="/train", method = RequestMethod.POST)
    public ModelAndView train(@RequestParam String type,
                              @RequestParam String amount,
                              @RequestParam String px,
                              @RequestParam String py,
                              @ModelAttribute("user") final User user,
                              Locale locale){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        if(!Validator.isInteger(type) || !Validator.isInteger(amount)
                || !Validator.validBoardPosition(px) || !Validator.validBoardPosition(py)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidParam",null,locale));
        }
        int a = Integer.valueOf(amount);
        int x = Integer.valueOf(px);
        int y = Integer.valueOf(py);
        Sector s =ss.getSector(new Point(x,y));
        if(!(s instanceof Building)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.terrainRecruit",null,locale));
        }
        Building b = (Building) s;

        int cost;
        switch (Integer.valueOf(type)){
            case Info.WARRIOR:
                    cost = (Info.COST_WARRIOR - (b.getLevel() - 1))*a;
                break;
            case Info.ARCHER:
                cost = (Info.COST_ARCHER - (b.getLevel() - 1))*a;
                break;
            case Info.HORSEMAN:
                cost = (Info.COST_HORSEMAN - (b.getLevel() - 1))*a;
                break;
            default:
                return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidTroop",null,locale));
        }
        if(es.getResource(user.getId(), Info.RES_FOOD).getQuantity() < cost){
            return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&e="+ messageSource.getMessage("error.noFood",null,locale));
        }
        es.subtractResourceAmount(user.getId(),Info.RES_FOOD,cost);
        Army ar = as.getOrCreateArmy(new Point(x,y),user.getId());
        ts.addTroop(ar.getIdArmy(),Integer.valueOf(type),a);
        return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&s="+ messageSource.getMessage("troopSuccess",null,locale));

    }

    @Transactional
    @RequestMapping(value="/merge")
    public ModelAndView train(@RequestParam String f,
                              @RequestParam String t,
                              @ModelAttribute("user") final User user,
                              Locale locale){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        if(!Validator.isInteger(f) || !Validator.isInteger(t)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidParam",null,locale));
        }
        int from = Integer.valueOf(f);
        int to = Integer.valueOf(t);
        List<Army> a = as.getArmies(user.getId());
        if(!as.belongs(user.getId(),from) || !as.belongs(user.getId(),to)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
        }

        List<Troop> troops = ts.getTroopById(from);
        for(Troop troop : troops){
            ts.addTroop(to,troop.getType(),troop.getQuantity());
        }
        as.deleteArmy(from);
        return new ModelAndView("redirect:/armies");
    }

    @Transactional
    @RequestMapping(value="/armies/{armyId}/split")
    public ModelAndView train(@PathVariable String armyId,
                              @ModelAttribute("user") final User user,
                              Locale locale){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        if(!Validator.isInteger(armyId)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
        }
        if(!as.belongs(user.getId(),Integer.valueOf(armyId))){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
        }
        ModelAndView mav = new ModelAndView("split");
        Army a = as.getArmyById(Integer.parseInt(armyId));
        List<Point> points = new ArrayList<>();
        for(Building b : bs.getAllBuildings(user.getId())){
            if(!b.getPosition().equals(a.getPosition())) {
                points.add(b.getPosition());
            }
        }
        mav.addObject("user",user);
        mav.addObject("army",a);
        mav.addObject("possiblePoints",points);
        return mav;
    }
    @RequestMapping(value="/split",method = RequestMethod.POST)
    public ModelAndView train(@RequestParam(value= "0",required = false,defaultValue = "0") String t1,
                              @RequestParam(value= "1",required = false,defaultValue = "0") String t2,
                              @RequestParam(value= "2",required = false,defaultValue = "0") String t3,
                              @RequestParam(value= "pos") String pos,
                              @RequestParam(value= "army") String armyId,
                              @ModelAttribute("user") final User user,
                              Locale locale){
        String[] po = pos.split(",");
        if(!Validator.validBoardPosition(po[0]) || !Validator.validBoardPosition(po[1])){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidPosition",null,locale));
        }
        Point p = new Point(Integer.parseInt(po[0]),Integer.parseInt(po[1]));
        if(!Validator.isInteger(t1) || !Validator.isInteger(t2) || !Validator.isInteger(t3)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidTroopAmount",null,locale));
        }
        if(!Validator.isInteger(armyId)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidArmy",null,locale));
        }
        int id = Integer.parseInt(armyId);
        if(!as.belongs(user.getId(),id)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYoursArmy",null,locale));
        }
        int warriors = Integer.parseInt(t1);
        int archers = Integer.parseInt(t2);
        int horsemen = Integer.parseInt(t3);

        Army newArmy = as.getOrCreateArmy(p,user.getId());
        if(warriors != 0){
            ts.addTroop(newArmy.getIdArmy(),Info.WARRIOR,warriors);
            ts.subtractTroop(id,Info.WARRIOR,warriors);
        }
        if(archers != 0){
            ts.addTroop(newArmy.getIdArmy(),Info.ARCHER,archers);
            ts.subtractTroop(id,Info.ARCHER,archers);
        }
        if(horsemen != 0){
            ts.addTroop(newArmy.getIdArmy(),Info.HORSEMAN,horsemen);
            ts.subtractTroop(id,Info.HORSEMAN,horsemen);
        }
        return new ModelAndView("redirect:/armies");
    }
    @ModelAttribute("user")
    public User loggedUser (final HttpSession session){
        out.println("EL USUARIO ES: " + (Integer)session.getAttribute("userId"));
        if(session.getAttribute("userId") != null)
            return  us.findById((Integer)session.getAttribute("userId"));
        return null;
    }

}