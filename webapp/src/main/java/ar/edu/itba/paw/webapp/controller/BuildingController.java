package ar.edu.itba.paw.webapp.controller;

import java.util.List;
import java.util.Locale;

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


/**
 * Created by Muffin Team on 9/18/16.
 */
@Controller
public class BuildingController {

    @Autowired
    private SectorService ss;
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
                                      @RequestParam(value="e", required = false,defaultValue = "") final String error,
                                      @RequestParam(value="s", required = false,defaultValue = "") final String success,
                                      @ModelAttribute("userId") final User user) {



        if(user == null)
            return new ModelAndView("redirect:/login");

        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)) {
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidPosition",null,locale));
        }else{
        	
        	List<Integer> plainTerrainBuildings = Info.getInstance().getPlainTerrainBuildings();
        	
        	Integer goldTerrainBuilding = Info.GOLD;

        	
            final ModelAndView mav = new ModelAndView("building");

            Sector sector = ss.getSector(new Point(Integer.parseInt(x),Integer.parseInt(y)));
            InformationBuilding ib  = Info.getInstance().getBuildingInformation(sector.getType(),locale.getLanguage());

            mav.addObject("building",ib);
            mav.addObject("owner",sector.getUser());
            mav.addObject("user",user);
            mav.addObject("p",new Point(Integer.parseInt(x),Integer.parseInt(y)));
            mav.addObject("plainTerrainBuildings",plainTerrainBuildings);
            mav.addObject("goldTerraunBuilding",goldTerrainBuilding);
            mav.addObject("price", ss.getPrice(new Point(Integer.parseInt(x),Integer.parseInt(y)),user));
            mav.addObject("level",sector.getLevel());
    		mav.addObject("resList",es.getResources(user));
            mav.addObject("ratesList",es.getRates(user));
            mav.addObject("error",error);
            mav.addObject("success",success);
            mav.addObject("locale",locale);
            mav.addObject("messageSource",messageSource);
            return mav;

        }


    }

    
    @RequestMapping(value="/build", method = RequestMethod.POST)
    public ModelAndView build(@RequestParam String x,
                              @RequestParam String y,
                              @RequestParam String type,
                              @ModelAttribute("userId") final User user,
                              Locale locale){

        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y) || !Validator.isInteger(type)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidParam",null,locale));
        }
        int xprime = Integer.parseInt(x);
        int yprime = Integer.parseInt(y);
        int typep = Integer.parseInt(type);



        Sector s = ss.getSector(new Point(xprime,yprime));
        
        if(s == null || !s.getUser().equals(user)|| (s.getType() != Info.TERR_GOLD && s.getType() != Info.EMPTY) ){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.cantConstruct",null,locale));
        }
        
        if(es.build(user,xprime,yprime,typep)){
        	return new ModelAndView("redirect:/map");
        }else{
        	return new ModelAndView("redirect:/building?x=" + xprime + "&y=" + yprime+ "&e=" + messageSource.getMessage("error.noGold",null,locale));
        }
      
    }

    
    @RequestMapping(value="/demolish", method = RequestMethod.POST)
    public ModelAndView demolish(@RequestParam String x,
                                 @RequestParam String y,
                                 @ModelAttribute("userId") final User user,
                                 Locale locale){
        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidPosition",null,locale));
        }
        Point p = new Point(Integer.parseInt(x),Integer.parseInt(y));
        Sector s = ss.getSector(p);
        if(!s.getUser().equals(user)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYourPosition",null,locale));
        }
        ss.deleteBuilding(p);
        return new ModelAndView("redirect:/map");
    }
    
    @RequestMapping(value="/levelup", method = RequestMethod.POST)
    public ModelAndView levelup(@RequestParam String x,
                                @RequestParam String y,
                                @ModelAttribute("userId") final User user,
                                Locale locale){
        if(!Validator.validBoardPosition(x) || !Validator.validBoardPosition(y)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidPosition",null,locale));
        }
        Point p = new Point(Integer.parseInt(x),Integer.parseInt(y));
        Sector s = ss.getSector(p);
        if(!s.getUser().equals(user)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.notYourPosition",null,locale));
        }
//        if(!(s.getLevel() == 1)){
//            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.cantLevelUpTerrain",null,locale));
//        }
        if(s.getLevel() < 20){
            int price = ss.getPrice(p,user) + (int) Math.pow(s.getLevel(),4);
            if(es.getResource(user,Info.RES_GOLD).getQuantity() >= price ) {
                ss.levelUp(p);
                es.subtractResourceAmount(user, Info.RES_GOLD, price);
            }else{
               return new ModelAndView("redirect:/building?x="+x+"&y=" +y+"&m="+ messageSource.getMessage("error.noGold",null,locale));
            }
        }else{
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.maxLevel",null,locale));
        }

        return new ModelAndView("redirect:/building?x="+x+"&y=" + y);
    }

    @ModelAttribute("userId")
    public User loggedUser (final HttpSession session){
        if(session.getAttribute("userId") == null) {
            return null;
        }

        return  us.findById((Integer)session.getAttribute("userId"));

    }

}
