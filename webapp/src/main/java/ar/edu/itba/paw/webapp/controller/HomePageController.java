package ar.edu.itba.paw.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

@Controller
public class HomePageController {
	
	@Autowired
	private SectorService ss;

	@RequestMapping(value="/map", method = RequestMethod.GET)
	public ModelAndView gridLoader(@RequestParam(value= "x",required = false,defaultValue = "50") int x ,
								   @RequestParam(value= "y",required = false,defaultValue = "50") int y){
		final ModelAndView mav = new ModelAndView("index");

		List<List<Sector>> elements;
		elements = ss.getSector(new Point(x,y), 3);
		mav.addObject("map",elements);
		mav.addObject("x",x);
		mav.addObject("y",y);
		return mav;
	}
}
