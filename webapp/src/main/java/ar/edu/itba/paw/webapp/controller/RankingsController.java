package ar.edu.itba.paw.webapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.beans.UserScoreBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class RankingsController {
	
	@Autowired
	private UserService us;
	@Autowired
	private EmpireService es;
	@Autowired
	private MessageService ms;
	
	@RequestMapping("/ranking")
	public ModelAndView home(@ModelAttribute("userId") final User user){
		ModelAndView mav = new ModelAndView("ranking");
		
		List<User> users = us.getAllUsers();
		List<UserScoreBean> ranks = new ArrayList<>(users.size());
		Comparator<UserScoreBean> c = new Comparator<UserScoreBean>(){
			@Override
			public int compare(UserScoreBean o1, UserScoreBean o2) {
				return (int)(o2.getScore()-o1.getScore());
			}
		};
		for(User u: users){
			ranks.add(new UserScoreBean(u,es.calculateScore(u)));
		}
		ranks.sort(c);

		int unreadMessages = ms.countUnreadMessages(user);

		mav.addObject("ranks", ranks);
		mav.addObject("unreadMessages", unreadMessages);
		return mav;
	}



	@ModelAttribute("userId")
	public User loggedUser (final HttpSession session){
		if(session.getAttribute("userId") != null)
			return  us.findById((Integer)session.getAttribute("userId"));
		return null;
	}
}
