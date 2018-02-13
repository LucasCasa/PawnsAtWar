package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.CommerceService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.ErrorDTO;
import ar.edu.itba.paw.webapp.DTOs.TradeOfferCreateDTO;
import ar.edu.itba.paw.webapp.DTOs.TradeOfferDTO;
import ar.edu.itba.paw.webapp.DTOs.TradeOffersDTO;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("commerce")
@Controller
public class CommerceController {

  @Autowired
  private EmpireService es;

  @Autowired
  private CommerceService cs;

  @Autowired
  private UserService us;

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllTrades() {
    String username = AuthenticatedUser.getUser(us).getName();
    List<TradeOffer> allOffers = cs.getAllOffers();
    List<TradeOfferDTO> mine = new ArrayList<>();
    List<TradeOfferDTO> others = new ArrayList<>();
    for (TradeOffer t : allOffers) {
      if (t.getOwner().getName().equals(username))
        mine.add(new TradeOfferDTO(t));
      else
        others.add(new TradeOfferDTO(t));
    }
    return Response.ok().entity(new TradeOffersDTO(mine, others)).build();
  }

  @POST
  @Path("/trade/{id}")
  public Response acceptTrade(@PathParam("id") final int id) {
    User user = AuthenticatedUser.getUser(us);
    TradeOffer offer = cs.getOffer(id);
    if (offer == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    if(offer.getOwner().equals(user)){
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("CANT_ACCEPT_SELF_OFFER")).build();
    }
    if (es.getResource(user, offer.getReceiveType()).getQuantity() < offer.getReceiveAmount()) {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("NOT_ENOUGH_RESOURCES")).build();
    }
    cs.acceptOffer(offer, user);
    return Response.noContent().build();
  }

  @DELETE
  @Path("/trade/{id}")
  public Response deleteTrade(@PathParam("id") final int id) {
    User user = AuthenticatedUser.getUser(us);
    TradeOffer offer = cs.getOffer(id);
    if (offer == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else if(offer.getOwner().getId() != user.getId()) {
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    cs.removeOffer(offer);
    return Response.noContent().build();
  }

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createTrade(TradeOfferCreateDTO create) {
    User creator = AuthenticatedUser.getUser(us);
    if(create.getOffer().getType() == create.getReceive().getType()) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }
    boolean created = cs.createOffer(creator, create.getOffer().getType(), create.getOffer().getAmount(),
      create.getReceive().getType(), create.getReceive().getAmount());
    if (created) {
      return Response.noContent().build();
    } else {
      return Response.status(Response.Status.FORBIDDEN).entity(new ErrorDTO("NOT_ENOUGH_RESOURCES")).build();
    }
  }
}
