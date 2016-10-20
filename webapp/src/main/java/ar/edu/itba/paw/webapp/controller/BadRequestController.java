package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by root on 9/20/16.
 */
@Controller
public class BadRequestController {


    @RequestMapping(value="/error")
    public ModelAndView errors(@RequestParam(value= "m",required = false) String message){
        
        final ModelAndView mav = new ModelAndView("error");
        String s;
        try{
            s = URLEncoder.encode(message, "UTF-8");
        }catch(Exception e){
            System.out.println("PUTO");
        }

        mav.addObject("message",message);
        return mav;
    }
}
