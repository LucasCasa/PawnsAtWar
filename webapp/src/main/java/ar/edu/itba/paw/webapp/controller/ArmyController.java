package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.TerrainService;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.*;
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
    public ModelAndView showArmies(@ModelAttribute("user") final User user){
        final ModelAndView mav = new ModelAndView("armies");
        List<Army> armies;
        armies = as.getArmies(0); // aca el id del flaco
        if(armies == null){
            armies = new ArrayList<>();
        }
        mav.addObject("armies",armies);
        return mav;
    }

    @RequestMapping(value="/armies/{armyId}")
    public ModelAndView showArmy(@PathVariable String armyId){

        final ModelAndView mav = new ModelAndView("army");
        String regex = "^\\d\\d?";

        if(armyId == null || !armyId.matches(regex)) {
            return new ModelAndView("redirect:/error");
        }
        int id = Integer.parseInt(armyId);

        Army army = as.getArmyById(id);
        if(army == null){
            return new ModelAndView("redirect:/error");
        }
        List<Troop> troops = ts.getTroopById(id);
        mav.addObject("army",army);
        mav.addObject("troops",troops);
        return mav;
    }

    @RequestMapping(value="/attack", method = RequestMethod.POST)
    public ModelAndView showArmy(@RequestParam String x, @RequestParam String y,@ModelAttribute("user") final User user ){
        final ModelAndView mav = new ModelAndView("attack");
        String regex = "^\\d\\d?";
        if(x == null || y == null || !x.matches(regex) || !y.matches(regex) ){
            return new ModelAndView("redirect:/error");
        }
        int xprime = Integer.parseInt(x);
        int yprime = Integer.parseInt(y);



        Sector s = ss.getSector(new Point(xprime,yprime));
        if(s == null){
            return new ModelAndView("redirect:/error");
        }
        if(s.getIdPlayer() == user.getId()){
            mav.addObject("message","No se puede atacar un edificio tuyo...");
        }else if(s.getType() == 0 || s.getType() == 5){
            mav.addObject("message","No se puede atacar un Terreno sin edificio");
        }else {
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