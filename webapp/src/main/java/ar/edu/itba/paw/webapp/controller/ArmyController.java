package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.TerrainService;
import ar.edu.itba.interfaces.TroopService;
import ar.edu.itba.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView showArmies(){
        final ModelAndView mav = new ModelAndView("armies");
        List<Army> armies;
        User s = new User(0,"lucas","lucas","l@l.com");
        armies = as.getArmies(0); // aca el id del flaco
        if(armies == null){
            armies = new ArrayList<>();
        }
        mav.addObject("armies",armies);
        return mav;
    }

    @RequestMapping(value="/armies/{armyId}")
    public ModelAndView showArmy(@PathVariable int armyId){
        final ModelAndView mav = new ModelAndView("army");
        Army army = as.getArmyById(armyId);
        List<Troop> troops = ts.getTroopById(armyId);
        mav.addObject("army",army);
        mav.addObject("troops",troops);
        return mav;
    }

    @RequestMapping(value="/attack", method = RequestMethod.POST)
    public ModelAndView showArmy(@RequestParam int x, @RequestParam int y ){
        final ModelAndView mav = new ModelAndView("attack");
        ss.deleteBuilding(new Point(x,y));
        return mav;
    }
}