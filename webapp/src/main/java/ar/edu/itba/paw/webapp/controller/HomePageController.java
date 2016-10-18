package ar.edu.itba.paw.webapp.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.BuildingService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.dataClasses.Info;
import ar.edu.itba.paw.webapp.dataClasses.Validator;

@Controller
public class HomePageController {

	@Autowired
	private SectorService ss;
	@Autowired
	private EmpireService es;
	@Autowired
	private BuildingService bs;
	@Autowired
	private UserService us;
	@Autowired
	private MessageSource messageSource;



	@RequestMapping("/")
	public ModelAndView home(@ModelAttribute("userId") final User user){
		if(user == null) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("redirect:/map");
	}

	@RequestMapping(value={"/map"}, method = RequestMethod.GET)
	public ModelAndView gridLoader(@RequestParam(value= "x",required = false) String x ,
								   @RequestParam(value= "y",required = false) String y,
								   @ModelAttribute("userId") final User user,
								   Locale locale){

		if(user == null){
			return new ModelAndView("redirect:/");
		}
		int xPrime;
		int yPrime;

		if(x == null && y == null){
			Point p = bs.getCastle(user.getId());
			xPrime = p.getX();
			yPrime = p.getY();
		}else{
			if(!Validator.isInteger(x) || !Validator.isInteger(y) ) {
				return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.invalidParam",null,locale));
			}
			xPrime = Validator.getValidPos(Integer.parseInt(x));
			yPrime = Validator.getValidPos(Integer.parseInt(y));
		}


		final ModelAndView mav = new ModelAndView("map");

		List<List<Sector>> elements;
		elements = ss.getSector(new Point(xPrime,yPrime), Info.VIEW_RANGE);
		mav.addObject("resList",es.getResources(user.getId()));
		mav.addObject("ratesList",es.getRates(user.getId()));
		mav.addObject("map",elements);
		mav.addObject("x",xPrime);
		mav.addObject("y",yPrime);
		mav.addObject("range",Info.VIEW_RANGE);

		mav.addObject("user",user);

		return mav;
	}
	@ModelAttribute("userId")
	public User loggedUser (final HttpSession session){
		if(session.getAttribute("userId") != null)
			return  us.findById((Integer)session.getAttribute("userId"));
		return null;
	}
}
