package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

@Controller
public class RankingsController {
	
	@Autowired
	private UserService us;
	@Autowired
	private EmpireService es;
	@Autowired
	private SectorService ss;
	
	
	@RequestMapping("/ranking")
	public ModelAndView home(){
		ModelAndView mav = new ModelAndView("ranking");
		
		List<User> users = us.getAllUsers();
		List<Point> castles = new ArrayList<>();
		
		List<Long> scores = new ArrayList<Long>();
		
		for(User u: users){
			scores.add(es.calculateScore(u));
			castles.add(ss.getCastle(u));
		}
		
		mav.addObject("players", users);
		mav.addObject("scores",scores);
		mav.addObject("castles",castles);
		return mav;
	}
}
