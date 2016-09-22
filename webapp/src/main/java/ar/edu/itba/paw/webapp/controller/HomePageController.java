package ar.edu.itba.paw.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import static java.lang.System.out;

@Controller
public class HomePageController {
	
	@Autowired
	private SectorService ss;
	@Autowired
	private EmpireService es;

	@RequestMapping(value="/map", method = RequestMethod.GET)
	public ModelAndView gridLoader(@RequestParam(value= "x",required = false,defaultValue = "50")  String x ,
								   @RequestParam(value= "y",required = false,defaultValue = "50") String y){

		String regex = "^\\d+";

		if(!x.matches(regex) || !y.matches(regex)) {
			return new ModelAndView("redirect:/error");
		}else{
			final ModelAndView mav = new ModelAndView("index");

			List<List<Sector>> elements;
			elements = ss.getSector(new Point(Integer.parseInt(x),Integer.parseInt(y)), 3);
			mav.addObject("resList",es.getResources(0));
			mav.addObject("map",elements);
			mav.addObject("x",x);
			mav.addObject("y",y);
			return mav;
		}




	}



}
