package ar.edu.itba.paw.webapp.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.form.LoginForm;

@Controller
public class LoginController {

	@Autowired
	UserService us;
	
	private final static String LOGGED_USER_ID = "userId";
	
	@RequestMapping("/login")
	public ModelAndView index(@ModelAttribute("registerForm") final LoginForm form){
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final LoginForm form, final BindingResult errors) {
		if (errors.hasErrors()) {
			return index(form);
		}
		final User u = us.create(form.getUsername(), form.getPassword(), form.getEmail());
		return new ModelAndView("redirect:/map?userId=" + u.getId());
	}
	
	@ModelAttribute("userId")
	public Integer loggedUser (final HttpSession session){
		return (Integer) session.getAttribute(LOGGED_USER_ID);
	}
}

			        
			        
