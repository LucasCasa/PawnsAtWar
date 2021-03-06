package ar.edu.itba.paw.webapp.controller;

import java.util.List;
import java.util.Locale;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import ar.edu.itba.paw.webapp.beans.ResourceBarBean;
import ar.edu.itba.paw.webapp.data.Info;
import ar.edu.itba.paw.webapp.data.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.paw.webapp.data.InformationBuilding;

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
    private ScheduleService sh;
    @Autowired
    private AlertService as;
    @Autowired
    private MessageService ms;
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
        	
            final ModelAndView mav = new ModelAndView("building");
            Point p = new Point(Integer.parseInt(x),Integer.parseInt(y));
            Sector sector = ss.getSector(p);
            int id = sector.getType();
            Alert a = as.getAlertByPoint(p);
            String name = SectorType.get(id).toString();
    		int unreadMessages = ms.countUnreadMessages(user);
            InformationBuilding ib  = new InformationBuilding(id,name,messageSource.getMessage("description."+ name,null,locale));
            mav.addObject("castleCost",ss.getCastlePrice(user));
            mav.addObject("canBuildCastle",es.validCastlePosition(p));
            mav.addObject("building",ib);
            mav.addObject("owner",sector.getUser());
            mav.addObject("user",user);
            mav.addObject("p",p);
    		mav.addObject("unreadMessages", unreadMessages);
            mav.addObject("alert",a);
            mav.addObject("price", ss.getPrice(user));
            mav.addObject("level",sector.getLevel());
            mav.addObject("rBar", new ResourceBarBean(es.getResources(user), es.getMaxStorage(user), es.getRates(user)));
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
        
        if(s == null || (s.getType() != Info.TERR_GOLD && s.getType() != Info.EMPTY) ){
        	System.err.println("cant build");
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.cantConstruct",null,locale));
        }
        
        if(es.build(user,xprime,yprime,typep)){
        	return new ModelAndView("redirect:/map");
        }else{
        	System.err.println("build ret false");
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
            int price = ss.getPrice(user) + (int) Math.pow(s.getLevel(),4);
            if(es.getResource(user,Info.RES_GOLD).getQuantity() >= price ) {
                sh.levelUpTask(s);
                es.subtractResourceAmount(user, Info.RES_GOLD, price);
            }else{
               return new ModelAndView("redirect:/building?x="+x+"&y=" +y+"&e="+ messageSource.getMessage("error.noGold",null,locale));
            }
        }else{
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.maxLevel",null,locale));
        }

        return new ModelAndView("redirect:/map?x="+x+"&y=" + y);
    }

    @ModelAttribute("userId")
    public User loggedUser (final HttpSession session){
        if(session.getAttribute("userId") == null) {
            return null;
        }
        return  us.findById((Integer)session.getAttribute("userId"));

    }

}
