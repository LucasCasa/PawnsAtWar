package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.PAWMailService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.Message;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.ErrorDTO;
import ar.edu.itba.paw.webapp.DTOs.MessageCreateDTO;
import ar.edu.itba.paw.webapp.DTOs.MessageDTO;
import ar.edu.itba.paw.webapp.DTOs.UserMessagesDTO;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("messages")
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

    User user = AuthenticatedUser.getUser(us);

    if (user == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    //final List<String> usernames = us.getUsernames();

    final List<MessageDTO> messagesRead = new ArrayList<>();
    final List<MessageDTO> messagesUnread = new ArrayList<>();
    final List<MessageDTO> messagesSent = new ArrayList<>();

    ms.getReadMessages(user).forEach(m -> messagesRead.add(new MessageDTO(m.getId(), m.getFrom().getName(), m.getSubject(), m.getMessage())));
    ms.getUnreadMessages(user).forEach(m -> messagesUnread.add(new MessageDTO(m.getId(), m.getFrom().getName(), m.getSubject(), m.getMessage())));
    ms.getSentMessages(user).forEach(m -> messagesSent.add(new MessageDTO(m.getId(), m.getFrom().getName(), m.getSubject(), m.getMessage())));

    return Response.ok().entity(new UserMessagesDTO(messagesRead, messagesUnread, messagesSent)).build();

  }

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createMessage(MessageCreateDTO create){
    User user = AuthenticatedUser.getUser(us);
    User to = us.findByUsername(create.getTo());

    if(to == null){
      return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO("INVALID_USER")).build();
    }

    if(create.getMessage().length() > 1024 || create.getSubject().length() > 50){
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    Message message =  ms.createMessage( user, to, create.getSubject(), create.getMessage());

    if (message!=null) {
      return Response.noContent().build();
    } else {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteMessage(@PathParam("id") final Long id){

    Message mssg = ms.getById(id);

    if(mssg == null)
      return Response.status(Response.Status.NOT_FOUND).build();


    ms.deleteMessage(mssg);

    return Response.noContent().build();
  }


  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response answerMessage(@PathParam("id") final Long id){

    User user = AuthenticatedUser.getUser(us);
    Message mssg = ms.getById(id);

    if(mssg==null || (!mssg.getFrom().equals(user) && !mssg.getTo().equals(user))){
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    ms.markAsRead(id);

    return Response.noContent().build();
  }


}
