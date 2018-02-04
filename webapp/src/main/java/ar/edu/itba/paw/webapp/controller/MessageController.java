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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

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
    @Path("/message/{userId}")
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

//    @RequestParam(required = false) String username,@RequestParam(required = false) String message, @RequestParam(required = false) String subject, @ModelAttribute("userId") final User user, Locale locale

    @POST
    @Path("/message/{userId}/{username}/{message}/{subject}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage(@PathParam("userId") final int userId, @PathParam("username") final String username, @PathParam("message") final String message, @PathParam("subject") final String subject ){
        User user = us.findById(userId);

        ms.createMessage( user, us.findByUsername(username), subject, message);

        if(!us.exists(username)){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(message.length() > 1024 || subject.length() > 50){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        

        return new ModelAndView("redirect:/messages?s=" + messageSource.getMessage("messageSent",null,locale));

    }

    @DELETE
    @Path("/message/{id}")
    public Response deleteMessage(@PathParam("id") final Long id){

        Message mssg = ms.getById(id);

        if(mssg == null)
            return Response.status(Response.Status.NOT_FOUND).build();


        ms.deleteMessage(mssg);

        return Response.noContent().build();
    }


    @POST
    @Path("/message/{id}")
    public Response answerMessage(@PathParam("id") final Long msgId){

        User user = AuthenticatedUser.getUserO(us);
        Message mssg = ms.getById(msgId);

        if(mssg==null || (!mssg.getFrom().equals(user) && !mssg.getTo().equals(user))){
        	return Response.status(Response.Status.NOT_FOUND).build();
        }

        final ModelAndView mav = new ModelAndView("seeMessage");
        ms.markAsRead(msgId);

//        int messagesUnread = ms.countUnreadMessages(user);


        return Response.noContent().build();
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
