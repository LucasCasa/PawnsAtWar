package ar.edu.itba.paw.webapp.controller;

import java.time.Clock;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		//elements = ts.getTerrain(new Point(0,0), 5);
		//elements = ts.getTerrain(new Point(0,0),1);

		elements = ts.getTerrain(new Point(2,2),2);
		//System.out.println(elements);
		mav.addObject("map",elements);
		return mav;
	}

	@RequestMapping("/map/{x}/{y}")
	public ModelAndView terrainParams(@PathVariable(value="x") final int x, @PathVariable(value="y") final int y ) {

		final ModelAndView mav = new ModelAndView("terrain");
		System.out.println("ANTES DE GET TERRAIN");

		Terrain terrain = ts.getTerrain(new Point(x,y));
		System.out.println("DESPUES DEL GET TERRAIN");
		mav.addObject("terrain",terrain);

		return mav;

	}
}
