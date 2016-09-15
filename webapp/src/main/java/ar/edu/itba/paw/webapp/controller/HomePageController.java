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
		//List<List<Integer>>  elements = new ArrayList<>();
		
		List<List<Terrain>> elements;
		elements = ts.getTerrain(new Point(3,3), 1);
		System.err.println(elements.toString());
		/* ESTO ES UN ASCO */
		/*elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.get(0).add(0);
		elements.get(0).add(0);
		elements.get(0).add(0);
		elements.get(0).add(4);
		elements.get(0).add(0);
		elements.get(0).add(4);
		elements.get(0).add(0);
		elements.get(1).add(0);
		elements.get(1).add(2);
		elements.get(1).add(0);
		elements.get(1).add(0);
		elements.get(1).add(0);
		elements.get(1).add(0);
		elements.get(1).add(0);
		elements.get(2).add(0);
		elements.get(2).add(0);
		elements.get(2).add(1);
		elements.get(2).add(0);
		elements.get(2).add(0);
		elements.get(2).add(0);
		elements.get(2).add(0);
		elements.get(3).add(0);
		elements.get(3).add(5);
		elements.get(3).add(0);
		elements.get(3).add(3);
		elements.get(3).add(0);
		elements.get(3).add(0);
		elements.get(3).add(5);
		elements.get(4).add(6);
		elements.get(4).add(0);
		elements.get(4).add(0);
		elements.get(4).add(0);
		elements.get(4).add(0);
		elements.get(4).add(6);
		elements.get(4).add(0);
		elements.get(5).add(0);
		elements.get(5).add(5);
		elements.get(5).add(0);
		elements.get(5).add(3);
		elements.get(5).add(0);
		elements.get(5).add(0);
		elements.get(5).add(5);
		elements.get(6).add(6);
		elements.get(6).add(0);
		elements.get(6).add(0);
		elements.get(6).add(0);
		elements.get(6).add(0);
		elements.get(6).add(6);
		elements.get(6).add(0);
		*/
		mav.addObject("map",elements);
		return mav;
	}
}
