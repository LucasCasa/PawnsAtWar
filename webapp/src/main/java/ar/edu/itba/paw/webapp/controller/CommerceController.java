package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.interfaces.CommerceService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.MessageService;
import ar.edu.itba.interfaces.UserService;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;
import ar.edu.itba.paw.webapp.DTOs.TradeOfferCreateDTO;
import ar.edu.itba.paw.webapp.DTOs.TradeOfferDTO;
import ar.edu.itba.paw.webapp.auth.AuthenticatedUser;
import ar.edu.itba.paw.webapp.beans.ResourceBarBean;
import ar.edu.itba.paw.webapp.data.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
        List<TradeOfferDTO> offers = new ArrayList<>();
        cs.getAllOffers().forEach(o -> offers.add(new TradeOfferDTO(o)));
        return Response.ok().entity(offers).build();
    }

    @POST
    @Path("/trade/{id}")
    public Response acceptTrade(@PathParam("id") final int id) {
        User user = AuthenticatedUser.getUser(us);
        TradeOffer offer = cs.getOffer(id);
        if (offer == null || offer.getOwner().equals(user)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (es.getResource(user, offer.getReceiveType()).getQuantity() < offer.getReceiveAmount()) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        cs.acceptOffer(offer, user);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/trade/{id}")
    public Response deleteTrade(@PathParam("id") final int id) {
        User user = AuthenticatedUser.getUser(us);
        TradeOffer offer = cs.getOffer(id);
        if (offer == null || offer.getOwner().getId() != user.getId()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        cs.removeOffer(offer);
        return Response.noContent().build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrade(TradeOfferCreateDTO create) {
        User creator = AuthenticatedUser.getUser(us);
        boolean created = cs.createOffer(creator, create.getOffer().getType(), create.getOffer().getAmount(),
                create.getReceive().getType(), create.getReceive().getAmount());
        if (created) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
