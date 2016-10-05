package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

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
import ar.edu.itba.paw.webapp.dataClasses.BuildingInformationMap;
import ar.edu.itba.paw.webapp.dataClasses.InformationBuilding;

/**
 * Created by root on 9/18/16.
 */
@Controller
public class BuildingController {

    @Autowired
    private SectorService ss;
    @Autowired
	private EmpireService es;

    @RequestMapping(value="/building", method = RequestMethod.GET)


    public ModelAndView terrainParams(@RequestParam(value="x",required = false) final String x,
                                      @RequestParam(value="y", required = false) final String y,
                                      @ModelAttribute("user") final User user) {

            String regex = "^\\d+";

        if(!x.matches(regex) || !y.matches(regex)) {
            return new ModelAndView("redirect:/error");
        }else{
        	
        	/* Should be somewhere else (?) */
        	List<Integer> plainTerrainBuildings = new ArrayList<Integer>();
        	plainTerrainBuildings.add(BuildingInformationMap.CASTLE);
        	plainTerrainBuildings.add(BuildingInformationMap.ARCHERY);
        	plainTerrainBuildings.add(BuildingInformationMap.BARRACKS);
        	plainTerrainBuildings.add(BuildingInformationMap.MILL);
        	plainTerrainBuildings.add(BuildingInformationMap.BLACKSMITH);
        	
        	/* Should be somewhere else (?) */
        	Integer goldTerrainBuilding = BuildingInformationMap.GOLD;

        	
            final ModelAndView mav = new ModelAndView("building");

            Sector sector = ss.getSector(new Point(Integer.parseInt(x),Integer.parseInt(y)));
            InformationBuilding ib  = BuildingInformationMap.getInstance().getBuildingInformation(sector.getType());

            mav.addObject("building",ib);
            mav.addObject("owner",sector.getIdPlayer());
            mav.addObject("user",user);
            mav.addObject("point",new Point(Integer.parseInt(x),Integer.parseInt(y)));
            mav.addObject("plainTerrainBuildings",plainTerrainBuildings);
            mav.addObject("goldTerraunBuilding",goldTerrainBuilding);
            mav.addObject("resList",es.getResources(user.getId()));
            mav.addObject("ratesList",es.getRates(user.getId()));

            return mav;

        }


    }
    
    @RequestMapping(value="/build", method = RequestMethod.POST)
    public ModelAndView showArmy(@RequestParam String x, @RequestParam String y,@RequestParam String type,@ModelAttribute("user") final User user ){
       // final ModelAndView mav = new ModelAndView("redirect:/building");
        String regex = "^\\d\\d?";
        if(x == null || y == null || !x.matches(regex) || !y.matches(regex) || type == null || !type.matches(regex) ){
            return new ModelAndView("redirect:/error");
        }
        int xprime = Integer.parseInt(x);
        int yprime = Integer.parseInt(y);
        int typep = Integer.parseInt(type);



        Sector s = ss.getSector(new Point(xprime,yprime));
        
        if(s == null || s.getIdPlayer() != user.getId() || (s.getType() != BuildingInformationMap.TERR_GOLD && s.getType() != BuildingInformationMap.EMPTY) ){
        	System.out.println("salgo por acaaa");
            return new ModelAndView("redirect:/error");
        }
        
        if(es.build(user.getId(),xprime,yprime,typep)){
        	return new ModelAndView("redirect:/map");
        }else{
        	return new ModelAndView("redirect:/building?x=" + xprime + "&y=" + yprime);
        }
      
    }


    @ModelAttribute("user")
    public User setRandomUser() {
        User bean = new User(69,"lucas","42069","l@l.com");
        return bean;
    }

}
