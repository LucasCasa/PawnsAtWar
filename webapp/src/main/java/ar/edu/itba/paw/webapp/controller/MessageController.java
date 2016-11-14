package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.PAWMailService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Created by root on 26/10/16.
 */
@Controller
public class MessageController {
    @Autowired
    private UserService us;

    @Autowired
    private MessageSource messageSource;


    @Autowired
    private MessageService ms;

    @Autowired
    private PAWMailService mailService;

    @RequestMapping(value="/messages")
    public ModelAndView messages(@ModelAttribute("user") final User user, @RequestParam(value="s", required = false,defaultValue = "") final String success){

        if(user == null)
            return new ModelAndView("redirect:/login");

        final ModelAndView mav = new ModelAndView("messages");

        System.out.println("QUE TIENE LA VARIABLE SUCCESS? " + success);

        List<Message> messagesReceived = ms.getAllMessages(user);
        mav.addObject("messagesReceived",messagesReceived);
        mav.addObject("mReceivdedListSize", messagesReceived.size());
        mav.addObject("success",success);
        mav.addObject("messageSource",messageSource);

        return mav;
    }

    @RequestMapping(value="/messages/sendMessage", method = RequestMethod.POST)
    public ModelAndView sendMessage(@RequestParam(required = false) String username,@RequestParam(required = false) String message, @RequestParam(required = false) String subject, @ModelAttribute("user") final User user, Locale locale){

        ms.createMessage( user, us.findByUsername(username), subject, message);

        if(!us.exists(username)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.userAlreadyExist",null,locale));
        }

        if(message.length() > 150){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.userAlreadyExist",null,locale));
        }
        return new ModelAndView("redirect:/messages?s=" + messageSource.getMessage("messageSent",null,locale));

    }


    @RequestMapping(value="/messages/delete")
    public ModelAndView deleteMessage(@RequestParam final Long id, @ModelAttribute("user") final User user){

        Message mssg = ms.getById(id);

        if(mssg == null)
            return new ModelAndView("redirect:/error");

        ms.deleteMessage(mssg);

        return new ModelAndView("redirect:/messages");
    }

    @ModelAttribute("user")
    public User loggedUser (final HttpSession session){
        if(session.getAttribute("userId") != null)
            return  us.findById((Integer)session.getAttribute("userId"));
        return null;
    }


    @RequestMapping(value="/messages/seeMessage")
    public ModelAndView answerMessage(@RequestParam final Long id, @ModelAttribute("user") final User user){

        Message mssg = ms.getById(id);

        final ModelAndView mav = new ModelAndView("seeMessage");

        mav.addObject("from", mssg.getFrom().getName());
        mav.addObject("subject", mssg.getSubject());
        mav.addObject("message", mssg.getMessage());

        //sendEmail(user.getEmail(), mssg.getSubject(), mssg.getMessage());

        return mav;
    }

    private void sendEmail(String emailTo, String subject, String message){
        mailService.sendEmail(emailTo, subject, message);
    }


}
