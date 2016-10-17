package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ar.edu.itba.interfaces.BuildingService;
import ar.edu.itba.model.Building;
import ar.edu.itba.paw.webapp.dataClasses.Info;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.paw.webapp.dataClasses.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    private BuildingService bs;
    @Autowired
	private EmpireService es;
    @Autowired
    private UserService us;
    @Autowired
    private MessageSource messageSource;


    @RequestMapping(value="/building", method = RequestMethod.GET)


    public ModelAndView terrainParams(Locale locale,
                                      @RequestParam(value="x",required = false) final String x,
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
            InformationBuilding ib  = Info.getInstance().getBuildingInformation(sector.getType(),locale.getLanguage());

            mav.addObject("building",ib);
            mav.addObject("owner",sector.getIdPlayer());
            mav.addObject("user",user);
            mav.addObject("p",new Point(Integer.parseInt(x),Integer.parseInt(y)));
            mav.addObject("plainTerrainBuildings",plainTerrainBuildings);
            mav.addObject("goldTerraunBuilding",goldTerrainBuilding);
            if(sector instanceof Building){
                mav.addObject("level",((Building) sector).getLevel());
            }else{
                mav.addObject("level",1);
            }
            mav.addObject("resList",es.getResources(user.getId()));
            mav.addObject("ratesList",es.getRates(user.getId()));
            mav.addObject("message",message);
            mav.addObject("locale",locale);
            mav.addObject("messageSource",messageSource);
            return mav;

        }


    }
    
    @RequestMapping(value="/build", method = RequestMethod.POST)
    public ModelAndView build(@RequestParam String x,
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
    @RequestMapping(value="/demolish", method = RequestMethod.POST)
    public ModelAndView demolish(@RequestParam String x,
                                 @RequestParam String y,
                                 @ModelAttribute("userId") final User user){
        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)){
            return new ModelAndView("redirect:/error?m=Direccion invalida");
        }
        Point p = new Point(Integer.parseInt(x),Integer.parseInt(y));
        Sector s = ss.getSector(p);
        if(s.getIdPlayer() != user.getId()){
            return new ModelAndView("redirect:/error?m=Esta posicion no te pertenece");
        }
        ss.deleteBuilding(p);
        return new ModelAndView("redirect:/map");
    }

    @RequestMapping(value="/levelup", method = RequestMethod.POST)
    public ModelAndView levelup(@RequestParam String x,
                                 @RequestParam String y,
                                 @ModelAttribute("userId") final User user){
        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)){
            return new ModelAndView("redirect:/error?m=Direccion invalida");
        }
        Point p = new Point(Integer.parseInt(x),Integer.parseInt(y));
        Sector s = ss.getSector(p);
        if(s.getIdPlayer() != user.getId()){
            return new ModelAndView("redirect:/error?m=Esta posicion no te pertenece");
        }
        bs.levelUp(p);

        if(bs.getType(p) == Info.GOLD ){
            es.addResourceAmount(user.getId(),1, 5000);//1=GOLD
            return new ModelAndView("redirect:/map");
        }

        if(bs.getType(p) == Info.MILL ){
            es.addResourceAmount(user.getId(),0, 5000); //O=FOOD
            return new ModelAndView("redirect:/map");
        }

        if(bs.getType(p) == Info.ARCHERY || bs.getType(p) == Info.BARRACKS || bs.getType(p) == Info.BLACKSMITH || bs.getType(p) == Info.STABLE){
           // TODO
        }



        return new ModelAndView("redirect:/building?x="+x+"&y=" + y);
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
