package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {

	@RequestMapping("/map")
	public ModelAndView gridLoader(){
		final ModelAndView mav = new ModelAndView("index");
		List<List<Integer>>  elements = new ArrayList<>();
		/* ESTO ES UN ASCO */
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.add(new ArrayList<>());
		elements.get(0).add(0);
		elements.get(0).add(0);
		elements.get(0).add(0);
		elements.get(0).add(0);
		elements.get(0).add(0);
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
		elements.get(3).add(0);
		elements.get(3).add(0);
		elements.get(3).add(0);
		elements.get(3).add(0);
		elements.get(3).add(0);
		elements.get(4).add(0);
		elements.get(4).add(0);
		elements.get(4).add(0);
		elements.get(4).add(0);
		elements.get(4).add(0);
		mav.addObject("map",elements);
		return mav;
	}
}
