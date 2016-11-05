package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by root on 26/10/16.
 */
@Controller
public class MessagesController {
    @Autowired
    private UserService us;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value="/messages")
    public ModelAndView messages(@ModelAttribute("user") final User user){


        if(user == null)
            return new ModelAndView("redirect:/login");

        final ModelAndView mav = new ModelAndView("messages");

       // List<TradeOffer> myTrades = cs.getAllOffers(user.getId());
        return new ModelAndView("messages");
    }

}
