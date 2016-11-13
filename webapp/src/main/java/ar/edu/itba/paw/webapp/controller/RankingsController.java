package ar.edu.itba.paw.webapp.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.beans.UserScoreBean;

@Controller
public class RankingsController {
	
	@Autowired
	private UserService us;
	@Autowired
	private EmpireService es;
	
	@RequestMapping("/ranking")
	public ModelAndView home(){
		ModelAndView mav = new ModelAndView("ranking");
		
		List<User> users = us.getAllUsers();
		
		Set<UserScoreBean> ranks = new TreeSet<>(new Comparator<UserScoreBean>(){
			@Override
			public int compare(UserScoreBean o1, UserScoreBean o2) {
				return (int)(o2.getScore()-o1.getScore());
			}
		});
		
		for(User u: users){
			ranks.add(new UserScoreBean(u,es.calculateScore(u)));
		}
		
		mav.addObject("ranks", ranks);
		return mav;
	}
}
