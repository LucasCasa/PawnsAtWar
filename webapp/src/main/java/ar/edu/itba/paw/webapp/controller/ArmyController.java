package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.interfaces.TerrainService;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Terrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Team Muffin on 18/09/16.
 */
@Controller
public class ArmyController {

    @Autowired
    private ArmyService ts;

    @RequestMapping(value="/army")
    public ModelAndView showArmies(){
        final ModelAndView mav = new ModelAndView("army");
        //List<Army> armies;
        //armies = ts.getArmies(0); // aca el id del flaco
        //mav.addObject("map",armies);
        return mav;
    }
}