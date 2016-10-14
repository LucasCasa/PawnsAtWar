package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.paw.webapp.dataClasses.Info;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.paw.webapp.dataClasses.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.dataClasses.InformationBuilding;

import javax.servlet.http.HttpSession;

import static java.lang.System.out;

/**
 * Created by root on 9/18/16.
 */
@Controller
public class BuildingController {

    @Autowired
    private SectorService ss;
    @Autowired
	private EmpireService es;
    @Autowired
    private UserService us;


    @RequestMapping(value="/building", method = RequestMethod.GET)


    public ModelAndView terrainParams(@RequestParam(value="x",required = false) final String x,
                                      @RequestParam(value="y", required = false) final String y,
                                      @RequestParam(value="m", required = false,defaultValue = "") final String message,
                                      @ModelAttribute("userId") final User user) {



        if(user == null)
            return new ModelAndView("redirect:/login");

        String regex = "^\\d+";


        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)) {
            return new ModelAndView("redirect:/error?m=Posicion invalida para construir");
        }else{
        	
        	/* Should be somewhere else (?) */
        	List<Integer> plainTerrainBuildings = new ArrayList<Integer>();
        	plainTerrainBuildings.add(Info.CASTLE);
        	plainTerrainBuildings.add(Info.ARCHERY);
        	plainTerrainBuildings.add(Info.BARRACKS);
        	plainTerrainBuildings.add(Info.MILL);
        	plainTerrainBuildings.add(Info.BLACKSMITH);
        	
        	/* Should be somewhere else (?) */
        	Integer goldTerrainBuilding = Info.GOLD;

        	
            final ModelAndView mav = new ModelAndView("building");

            Sector sector = ss.getSector(new Point(Integer.parseInt(x),Integer.parseInt(y)));
            InformationBuilding ib  = Info.getInstance().getBuildingInformation(sector.getType());

            mav.addObject("building",ib);
            mav.addObject("owner",sector.getIdPlayer());
            mav.addObject("user",user);
            mav.addObject("p",new Point(Integer.parseInt(x),Integer.parseInt(y)));
            mav.addObject("plainTerrainBuildings",plainTerrainBuildings);
            mav.addObject("goldTerraunBuilding",goldTerrainBuilding);
            mav.addObject("resList",es.getResources(user.getId()));
            mav.addObject("ratesList",es.getRates(user.getId()));
            mav.addObject("message",message);
            return mav;

        }


    }
    
    @RequestMapping(value="/build", method = RequestMethod.POST)
    public ModelAndView showArmy(@RequestParam String x,
                                 @RequestParam String y,
                                 @RequestParam String type,
                                 @ModelAttribute("userId") final User user ){

        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y) || !Validator.isInteger(type)){
            return new ModelAndView("redirect:/error?m=Parametro Invalido");
        }
        int xprime = Integer.parseInt(x);
        int yprime = Integer.parseInt(y);
        int typep = Integer.parseInt(type);



        Sector s = ss.getSector(new Point(xprime,yprime));
        
        if(s == null || s.getIdPlayer() != user.getId() || (s.getType() != Info.TERR_GOLD && s.getType() != Info.EMPTY) ){
            return new ModelAndView("redirect:/error?m=No se puede construir en esta posicion");
        }
        
        if(es.build(user.getId(),xprime,yprime,typep)){
        	return new ModelAndView("redirect:/map");
        }else{
        	return new ModelAndView("redirect:/building?x=" + xprime + "&y=" + yprime);
        }
      
    }

    /*
    @ModelAttribute("user")
    public User setRandomUser() {
        User bean = new User(69,"lucas","42069","l@l.com");
        return bean;
    }*/

    @ModelAttribute("userId")
    public User loggedUser (final HttpSession session){
        if(session.getAttribute("userId") == null) {
            return null;
        }

        return  us.findById((Integer)session.getAttribute("userId"));

    }

}
