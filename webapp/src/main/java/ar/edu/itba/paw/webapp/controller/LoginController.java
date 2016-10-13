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
import ar.edu.itba.paw.webapp.form.RegisterForm;

@Controller
public class LoginController {

	@Autowired
	UserService us;
	
	private final static String LOGGED_USER_ID = "userId";
	
	@RequestMapping("/register")
	public ModelAndView login(@ModelAttribute("registerForm") final RegisterForm form){
		return new ModelAndView("register");
	}
	
	@RequestMapping("/login")
	public ModelAndView authenticate(@ModelAttribute("loginForm") final LoginForm form){
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/create", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("registerForm") final RegisterForm form, final BindingResult errors, final HttpSession session) {
		if (errors.hasErrors()) {
			return login(form);
		}
		us.create(form.getUsername(), form.getPassword(), form.getEmail());
		User u = us.findByUsername(form.getUsername());
		session.setAttribute(LOGGED_USER_ID, u.getId());
		return new ModelAndView("redirect:/map");
	}
	
	@ModelAttribute("userId")
	public Integer loggedUser (final HttpSession session){
		return (Integer) session.getAttribute(LOGGED_USER_ID);
	}
	
	@RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
	public ModelAndView create(@Valid @ModelAttribute("loginForm") final LoginForm form, final BindingResult errors, final HttpSession session) {
		if (errors.hasErrors()) {
			return authenticate(form);
		}
		if(us.exists(form.getUsername(),form.getPassword())){
			User u = us.findByUsername(form.getUsername());
			session.setAttribute(LOGGED_USER_ID, u.getId());
			return new ModelAndView("redirect:/map");
		}else{
			return authenticate(form);
		}
	}
	
}

			        
			        
