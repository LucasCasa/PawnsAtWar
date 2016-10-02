package ar.edu.itba.paw.webapp.controller;

import java.util.List;

import ar.edu.itba.interfaces.BuildingService;
import ar.edu.itba.model.User;
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

@Controller
public class HomePageController {

	final int USERID = 69;
	@Autowired
	private SectorService ss;
	@Autowired
	private EmpireService es;
	@Autowired
	private BuildingService bs;

	@RequestMapping(value={"/map"}, method = RequestMethod.GET)
	public ModelAndView gridLoader(@RequestParam(value= "x",required = false,defaultValue = "-1") int x ,
								   @RequestParam(value= "y",required = false,defaultValue = "-1") int y,
								   @ModelAttribute("user") final User user){
		final ModelAndView mav = new ModelAndView("index");
		if(x == -1 && y == -1){
			Point p = bs.getCastle(user.getId());
			x = p.getX();
			y = p.getY();
		}
		List<List<Sector>> elements;
		elements = ss.getSector(new Point(x,y), 3);
		mav.addObject("resList",es.getResources(0));
		mav.addObject("map",elements);

		mav.addObject("x",x);
		mav.addObject("y",y);

		mav.addObject("user",user);
		return mav;
	}

	@RequestMapping("/")
	public ModelAndView login(){
		final ModelAndView mav = new ModelAndView("home");
		return mav;
	}
	@RequestMapping(value= "/login",method = RequestMethod.POST)
	public ModelAndView redir(@RequestParam(value= "id",required = false,defaultValue = "0") int id,
							  @ModelAttribute("user") final User user){
		final ModelAndView mav = new ModelAndView("login");
		mav.addObject("id",id);
		return mav;
	}

	@ModelAttribute("user")
	public User setRandomUser() {
		User bean = new User(USERID,"lucas","42069","l@l.com");
		return bean;
	}
}
