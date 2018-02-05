package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.PAWMailService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.MessageCreateDTO;
import ar.edu.itba.paw.webapp.DTOs.MessageDTO;
import ar.edu.itba.paw.webapp.DTOs.UserMessagesDTO;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
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
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserMessages(){

        User user = AuthenticatedUser.getUserO(us);

        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
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
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMessage(MessageCreateDTO create){
        User user = AuthenticatedUser.getUserO(us);

        if(!us.exists(user.getName())){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if(create.getMessage().length() > 1024 || create.getSubject().length() > 50){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Message message =  ms.createMessage( user, us.findByUsername(user.getName()), create.getSubject(), create.getMessage());

        if (message!=null) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @DELETE
    @Path("/{id}")
    public Response deleteMessage(@PathParam("id") final Long id){

        Message mssg = ms.getById(id);

        if(mssg == null)
            return Response.status(Response.Status.NOT_FOUND).build();


        ms.deleteMessage(mssg);

        return Response.noContent().build();
    }


    @PUT
    @Path("/{id}")
    public Response answerMessage(@PathParam("id") final Long msgId){

        User user = AuthenticatedUser.getUserO(us);
        Message mssg = ms.getById(msgId);

        if(mssg==null || (!mssg.getFrom().equals(user) && !mssg.getTo().equals(user))){
        	return Response.status(Response.Status.BAD_REQUEST).build();
        }
        ms.markAsRead(msgId);

//        int messagesUnread = ms.countUnreadMessages(user);

        return Response.noContent().build();
    }


}
