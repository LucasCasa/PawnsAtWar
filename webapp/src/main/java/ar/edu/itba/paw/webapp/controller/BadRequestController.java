package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by root on 9/20/16.
 */
@Controller
public class BadRequestController {


    @RequestMapping(value="/error")
    public ModelAndView errors(){

        final ModelAndView mav = new ModelAndView("error");


        return mav;
    }
}