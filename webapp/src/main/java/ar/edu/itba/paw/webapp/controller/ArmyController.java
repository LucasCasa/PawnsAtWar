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
import java.util.ArrayList;
import java.util.List;

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
    public ModelAndView attack(@RequestParam String x, @RequestParam String y,@ModelAttribute("user") final User user ){
        if(user == null){
            return new ModelAndView("redirect:/");
        }
        final ModelAndView mav = new ModelAndView("attack");
        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y) ){
            return new ModelAndView("redirect:/error?m=Posicion invalida");
        }
        int xprime = Integer.parseInt(x);
        int yprime = Integer.parseInt(y);



        Sector s = ss.getSector(new Point(xprime,yprime));
        if(s == null){
            return new ModelAndView("redirect:/error?m=No existe edificio en esa direccion");
        }
        if(s.getIdPlayer() == user.getId()){
            mav.addObject("message","No se puede atacar un edificio tuyo...");
        }else if(s.getType() == 0 || s.getType() == 5){
            mav.addObject("message","No se puede atacar un Terreno sin edificio");
        }else {
            if(s.getType() == Info.CASTLE){
              /*if(ss.isCastleAlone()){
                  mav.addObject("message", "ATAQUE EXITOSO!");
              }else{
                  mav.addObject("message","Para atacar un castillo primero se tienen que destruir todos los demas edificios");
                  return mav;
              }*/
            }
            mav.addObject("message", "ATAQUE EXITOSO!");
            ss.deleteBuilding(new Point(xprime,yprime));
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
        List<Point> points = new ArrayList<>();
        mav.addObject("user",user);
        mav.addObject("armyId",armyId);
        mav.addObject("posiblePoints",points);
        return mav;
    }
    @ModelAttribute("user")
    public User loggedUser (final HttpSession session){
        out.println("EL USUARIO ES: " + (Integer)session.getAttribute("userId"));
        if(session.getAttribute("userId") != null)
            return  us.findById((Integer)session.getAttribute("userId"));
        return null;
    }

}