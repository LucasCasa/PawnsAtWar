package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by root on 26/10/16.
 */
@Controller
public class MessageController {
    @Autowired
    private UserService us;


    @Autowired
    private MessageService ms;

    @RequestMapping(value="/messages")
    public ModelAndView messages(@ModelAttribute("user") final User user){


        if(user == null)
            return new ModelAndView("redirect:/login");

        final ModelAndView mav = new ModelAndView("messages");

        List<Message> messagesReceived = ms.getAllMessages(user);
        mav.addObject("messagesReceived",messagesReceived);
        mav.addObject("mReceivdedListSize", messagesReceived.size());

        return mav;
    }


    @ModelAttribute("user")
    public User loggedUser (final HttpSession session){
        if(session.getAttribute("userId") != null)
            return  us.findById((Integer)session.getAttribute("userId"));
        return null;
    }
}
