package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.PAWMailService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.MessageDTO;
import ar.edu.itba.paw.webapp.DTOs.UserMessagesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Path("message")
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

    @GET
    @Path("/messages/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserMessages(@PathParam("userId") final int userId){

        User user = us.findById(userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final List<String> usernames = us.getUsernames();

        final List<MessageDTO> messagesRead = new ArrayList<>();
        final List<MessageDTO> messagesUnread = new ArrayList<>();
        ms.getReadMessages(user).forEach(m -> messagesRead.add(new MessageDTO(m.getFrom(), user, m.getSubject(), m.getMessage())));
        final List<Message> messagesUnr = ms.getUnreadMessages(user);
        ms.getUnreadMessages(user).forEach(m -> messagesUnread.add(new MessageDTO(m.getFrom(), user, m.getSubject(), m.getMessage())));

        return Response.ok().entity(new UserMessagesDTO(user, messagesRead, messagesUnread)).build();

    }

    @POST
    @Path("/messages/sendmessage")
    @Produces(MediaType.APPLICATION_JSON)
    public ModelAndView sendMessage(@RequestParam(required = false) String username,@RequestParam(required = false) String message, @RequestParam(required = false) String subject, @ModelAttribute("userId") final User user, Locale locale){

        ms.createMessage( user, us.findByUsername(username), subject, message);

        if(!us.exists(username)){
            return new ModelAndView("redirect:/error?m="+ messageSource.getMessage("error.userAlreadyExist",null,locale));
        }

        if(message.length() > 1024 || subject.length() > 50){
            return new ModelAndView("redirect:/messages?m=" + messageSource.getMessage("error.longMessage",null ,locale));
        }
        

        return new ModelAndView("redirect:/messages?s=" + messageSource.getMessage("messageSent",null,locale));

    }


    @DELETE
    @Path("/messages/delete/{messageId}")
    public Response deleteMessage(@PathParam("messageId") final Long messageId){

        Message mssg = ms.getById(messageId);

        if(mssg == null)
            return Response.status(Response.Status.NOT_FOUND).build();


        ms.deleteMessage(mssg);

        return Response.ok().build();
    }
    
    @RequestMapping(value="/messages/seeMessage")
    public ModelAndView answerMessage(@RequestParam final Long msgId, @ModelAttribute("userId") final User user){

        Message mssg = ms.getById(msgId);


        if(mssg==null || (!mssg.getFrom().equals(user) && !mssg.getTo().equals(user))){
        	return new ModelAndView("redirect:/error");
        }

        final ModelAndView mav = new ModelAndView("seeMessage");
        ms.markAsRead(msgId);
        int messagesUnread = ms.countUnreadMessages(user);




        mav.addObject("from", mssg.getFrom().getName());
        mav.addObject("subject", mssg.getSubject());
        mav.addObject("message", mssg.getMessage());
        mav.addObject("unreadMessages", messagesUnread);
        ;

        return mav;
    }

    @ModelAttribute("userId")
    public User loggedUser (final HttpSession session){
        if(session.getAttribute("userId") != null){
        	User u = us.findById((Integer)session.getAttribute("userId"));
            return  u;
        }
        return null;
    }




    private void sendEmail(String emailTo, String subject, String message){
        mailService.sendEmail(emailTo, subject, message);
    }


}
