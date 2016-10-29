package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * Created by Muffin Team on 9/20/16.
 */
@Controller
public class BadRequestController {


    @RequestMapping(value="/error")
    public ModelAndView errors(@RequestParam(value= "m",required = false) String message){
        
        final ModelAndView mav = new ModelAndView("error");

        mav.addObject("message",message);
        return mav;
    }
}
