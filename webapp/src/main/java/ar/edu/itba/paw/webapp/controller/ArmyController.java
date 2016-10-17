package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.dataClasses.Info;
import ar.edu.itba.paw.webapp.dataClasses.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.ValidationMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return mav;
    }

    @RequestMapping(value="/armies/{armyId}")
    public ModelAndView showArmy(@PathVariable String armyId,
                                 @RequestParam(value= "x",required = false) String x ,
                                 @RequestParam(value= "y",required = false) String y,
                                 @ModelAttribute("user") final User user){
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
            return new ModelAndView("redirect:/error?m=Ejercito incorrecto");
        }
        int id = Integer.parseInt(armyId);

        Army army = as.getArmyById(id);
        if(army == null){
            return new ModelAndView("redirect:/error?m=el ejercito no existe");
        }
        if(!as.belongs(user.getId(),id)){
            return new ModelAndView("redirect:/error?m=Este ejercito no es tuyo");
        }
        List<Army> armies = as.getArmies(user.getId());
        if(armies == null){
            armies = new ArrayList<>();
        }
        List<Troop> troops = ts.getTroopById(id);
        mav.addObject("armies",armies);
        mav.addObject("armySize",armies.size());
        mav.addObject("army",army);
        mav.addObject("troops",troops);
        return mav;
    }

    @RequestMapping(value="/attack", method = RequestMethod.POST)
    public ModelAndView attack(@RequestParam String x,
                               @RequestParam String y,
                               @RequestParam String army,
                               @ModelAttribute("user") final User user ){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        final ModelAndView mav = new ModelAndView("attack");
        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y) ){
            return new ModelAndView("redirect:/error?m=Posicion invalida");
        }
        if(!Validator.isInteger(army)){
            return new ModelAndView("redirect:/error?m=Ejercito invalido");
        }
        int id = Integer.parseInt(army);
        if(!as.belongs(user.getId(),id)){
            return new ModelAndView("redirect:/error?m=El ejercito no es tuyo");
        }
        int xprime = Integer.parseInt(x);
        int yprime = Integer.parseInt(y);



        Sector s = ss.getSector(new Point(xprime,yprime));
        if(s == null){
            return new ModelAndView("redirect:/error?m=No existe edificio en esa direccion");

        }
        if(s.getIdPlayer() == user.getId()){
            return new ModelAndView("redirect:/error?m=No se puede atacar un edificio tuyo...");
        }else if(s.getType() == 0 || s.getType() == 5){
            return new ModelAndView("redirect:/error?m=No se puede atacar un Terreno sin edificio");
        }else {
            if(s.getType() == Info.CASTLE){
                if(!ss.isCastleAlone(new Point(xprime,yprime),3)){
                    return new ModelAndView("redirect:/error?m=Para atacar un castillo primero se tienen que destruir todos los demas edificios");
                }
            }
            Army a = as.getStrongest(s.getIdPlayer());
            Map<String,Object> values = new HashMap<>();
            values.put("a0b",new Integer(0));
            values.put("a1b",new Integer(0));
            values.put("a2b",new Integer(0));
            values.put("d0b",new Integer(0));
            values.put("d1b",new Integer(0)); // QUE WASADA POR DIOS
            values.put("d2b",new Integer(0));
            values.put("a0l",new Integer(0));
            values.put("a1l",new Integer(0));
            values.put("a2l",new Integer(0));
            values.put("d0l",new Integer(0));
            values.put("d1l",new Integer(0));
            values.put("d2l",new Integer(0));
            if(a != null){
                int defenderP = (int) ts.getValue(a.getIdArmy());
                int atackerP = (int) ts.getValue(id);
                int loserP = 0;
                int adef = 0;
                int awin = 0;
                String prefixW = "";
                String prefixD = "";
                if(defenderP > atackerP){
                    mav.addObject("result","Ataque no exitoso!! Perdiste");
                    adef = id;
                    awin = a.getIdArmy();
                    loserP = atackerP;
                    prefixD ="a";
                    prefixW ="d";
                }else if(atackerP > defenderP){
                    mav.addObject("result","Ataque Exitoso!! Ganaste");
                    awin = id;
                    adef = a.getIdArmy();
                    loserP = defenderP;
                    prefixD ="d";
                    prefixW ="a";
                    ss.deleteBuilding(new Point(xprime,yprime));
                }else{
                    mav.addObject("result","Empate");
                    as.deleteArmy(id);
                    as.deleteArmy(a.getIdArmy());
                }
                List<Troop> defeated = ts.getTroopById(adef);
                as.deleteArmy(adef);
                List<Troop> winner = ts.getTroopById(awin);
                for(Troop t : winner){
                    if(t.getQuantity()*(t.getType()+1) > loserP){
                        values.put(prefixW+t.getType()+"b",t.getQuantity());
                        ts.subtractTroop(awin,t.getType(),loserP/(t.getType()+1));
                        values.put(prefixW+t.getType()+"l",loserP/(t.getType()+1));
                        break;
                    }else{
                        values.put(prefixW+t.getType()+"b",t.getQuantity());
                        values.put(prefixW+t.getType()+"l",t.getQuantity());
                        ts.deleteTroop(awin,t.getType());
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
            mav.addObject("result","Ataque Exitoso!! No habia nadie para defender");
            for(Troop t : ts.getTroopById(id)){
                values.put("a"+t.getType()+"b",t.getQuantity());
                values.put("a"+t.getType()+"l",0);
            }
            ss.deleteBuilding(new Point(xprime,yprime));
            mav.addAllObjects(values);
        }
        return mav;
    }
    @RequestMapping(value="/train", method = RequestMethod.POST)
    public ModelAndView train(@RequestParam String type,
                              @RequestParam String amount,
                              @RequestParam String px,
                              @RequestParam String py,
                              @ModelAttribute("user") final User user ){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        if(!Validator.isInteger(type) || !Validator.isInteger(amount)
                || !Validator.validBoardPosition(px) || !Validator.validBoardPosition(py)){
            return new ModelAndView("redirect:/error?m=Parametros Invalidos");
        }
        int a = Integer.valueOf(amount);
        int x = Integer.valueOf(px);
        int y = Integer.valueOf(py);

        switch (Integer.valueOf(type)){
            case Info.WARRIOR:
                if(es.getResource(user.getId(), Info.RES_FOOD).getQuantity() < a * Info.COST_WARRIOR){
                    return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&m=No hay suficiente comida");
                }
                es.subtractResourceAmount(user.getId(),Info.RES_FOOD,a * Info.COST_WARRIOR);
                break;
            case Info.ARCHER:
                if(es.getResource(user.getId(), Info.RES_FOOD).getQuantity() < a * Info.COST_ARCHER) {
                    return new ModelAndView("redirect:/building?x=" + x + "&y=" + y + "&m=No hay suficiente comida");
                }
                es.subtractResourceAmount(user.getId(),Info.RES_FOOD,a * Info.COST_ARCHER);
                break;
            case Info.HORSEMAN:
                if(es.getResource(user.getId(), Info.RES_FOOD).getQuantity() < a * Info.COST_HORSEMAN){
                    return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&m=No hay suficiente comida");
                }
                es.subtractResourceAmount(user.getId(),Info.RES_FOOD,a * Info.COST_HORSEMAN);
                break;
            default:
                return new ModelAndView("redirect:/error?m=Tipo de tropa invalida");
        }
        Army ar = as.getOrCreateArmy(new Point(x,y),user.getId());
        ts.addTroop(ar.getIdArmy(),Integer.valueOf(type),a);
        return new ModelAndView("redirect:/building?x=" +x + "&y=" +y + "&m=Tropa creada exitosamente");

    }
    @RequestMapping(value="/merge")
    public ModelAndView train(@RequestParam String f,
                              @RequestParam String t,
                              @ModelAttribute("user") final User user ){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        if(!Validator.isInteger(f) || !Validator.isInteger(t)){
            return new ModelAndView("redirect:/error?m=Parametros Invalidos");
        }
        int from = Integer.valueOf(f);
        int to = Integer.valueOf(t);
        List<Army> a = as.getArmies(user.getId());
        if(!as.belongs(user.getId(),from) || !as.belongs(user.getId(),to)){
            return new ModelAndView("redirect:/error?m=Uno de los ejercitos no es tuyo");
        }

        List<Troop> troops = ts.getTroopById(from);
        for(Troop troop : troops){
            ts.addTroop(to,troop.getType(),troop.getQuantity());
        }
        as.deleteArmy(from);
        return new ModelAndView("redirect:/armies");
    }
    @RequestMapping(value="/armies/{armyId}/split")
    public ModelAndView train(@PathVariable String armyId,
                              @ModelAttribute("user") final User user ){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        if(!Validator.isInteger(armyId)){
            return new ModelAndView("redirect:/error?m=Ejercito Invalido");
        }
        if(!as.belongs(user.getId(),Integer.valueOf(armyId))){
            return new ModelAndView("redirect:/error?m=Este Ejercito no te pertenece");
        }
        ModelAndView mav = new ModelAndView("split");
        Army a = as.getArmyById(Integer.parseInt(armyId));
        List<Point> points = new ArrayList<>();
        for(Building b : bs.getAllBuildings(user.getId())){
            if(!b.getPosition().equals(a.getPosition())) {
                points.add(b.getPosition());
            }
        }
        List<Troop> t = ts.getTroopById(Integer.parseInt(armyId));
        mav.addObject("user",user);
        mav.addObject("army",a);
        mav.addObject("troops",t);
        mav.addObject("possiblePoints",points);
        return mav;
    }
    @RequestMapping(value="/split",method = RequestMethod.POST)
    public ModelAndView train(@RequestParam(value= "0",required = false,defaultValue = "0") String t1,
                              @RequestParam(value= "1",required = false,defaultValue = "0") String t2,
                              @RequestParam(value= "2",required = false,defaultValue = "0") String t3,
                              @RequestParam(value= "pos") String pos,
                              @RequestParam(value= "army") String armyId,
                              @ModelAttribute("user") final User user ){
        String[] po = pos.split(",");
        if(!Validator.validBoardPosition(po[0]) || !Validator.validBoardPosition(po[1])){
            return new ModelAndView("redirect:/error?m=Posición invalida");
        }
        Point p = new Point(Integer.parseInt(po[0]),Integer.parseInt(po[1]));
        if(!Validator.isInteger(t1) || !Validator.isInteger(t2) || !Validator.isInteger(t3)){
            return new ModelAndView("redirect:/error?m=Cantidad de tropas invalida");
        }
        if(!Validator.isInteger(armyId)){
            return new ModelAndView("redirect:/error?m=Ejercito invalido");
        }
        int id = Integer.parseInt(armyId);
        if(!as.belongs(user.getId(),id)){
            return new ModelAndView("redirect:/error?m=Ese ejercito no es tuyo");
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