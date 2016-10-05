package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.TerrainService;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.dataClasses.BuildingInformationMap;
import ar.edu.itba.paw.webapp.dataClasses.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value="/armies")
    public ModelAndView showArmies(@ModelAttribute("user") final User user, @RequestParam String x, @RequestParam String y){
        final ModelAndView mav = new ModelAndView("armies");
        List<Army> armies;
        armies = as.getArmies(0); // aca el id del flaco

        if(Validator.isInteger(x) && Validator.isInteger(y){

        }
        if(armies == null){
            armies = new ArrayList<>();
        }
        mav.addObject("armies",armies);
        return mav;
    }

    @RequestMapping(value="/armies/{armyId}")
    public ModelAndView showArmy(@PathVariable String armyId){

        final ModelAndView mav = new ModelAndView("army");

        if(armyId == null || !Validator.isInteger(armyId)) {
            return new ModelAndView("redirect:/error?m=Valor de parametro incorrecto");
        }
        int id = Integer.parseInt(armyId);

        Army army = as.getArmyById(id);
        if(army == null){
            return new ModelAndView("redirect:/error?m=el ejercito no existe");
        }
        List<Troop> troops = ts.getTroopById(id);
        mav.addObject("army",army);
        mav.addObject("troops",troops);
        return mav;
    }

    @RequestMapping(value="/attack", method = RequestMethod.POST)
    public ModelAndView showArmy(@RequestParam String x, @RequestParam String y,@ModelAttribute("user") final User user ){
        final ModelAndView mav = new ModelAndView("attack");
        if(x == null || y == null || !Validator.validBoardPosition(x) || Validator.validBoardPosition(y) ){
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
            if(s.getType() == BuildingInformationMap.CASTLE){
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

    @ModelAttribute("user")
    public User setRandomUser() {
        User bean = new User(69,"lucas","42069","l@l.com");
        return bean;
    }


}