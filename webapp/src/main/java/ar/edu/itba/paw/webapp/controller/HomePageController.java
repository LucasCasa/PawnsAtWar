package ar.edu.itba.paw.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.TerrainService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Terrain;

@Controller
public class HomePageController {
	
	@Autowired
	private TerrainService ts;

	@RequestMapping("/map")
	public ModelAndView gridLoader(){
		final ModelAndView mav = new ModelAndView("index");

		List<List<Terrain>> elements;
		elements = ts.getTerrain(new Point(3,3), 1);
		mav.addObject("map",elements);
		return mav;
	}
}
