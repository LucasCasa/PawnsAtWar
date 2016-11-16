package ar.edu.itba.paw.webapp.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Muffin Team on 9/20/16.
 */
@Controller
public class BadRequestController {

	@Autowired
	private MessageSource messageSource;


	@RequestMapping(value="/error")
	public ModelAndView errors(@RequestParam(value= "m",required = false) String message, Locale locale){
		final ModelAndView mav = new ModelAndView("error");
		mav.addObject("message",message);
		if(message!=null && message.equals(messageSource.getMessage("error.gameOver",null,locale))){
			mav.addObject("gameOver", true);
		}else{
			mav.addObject("gameOver", false);
		}
		return mav;
	}
}
