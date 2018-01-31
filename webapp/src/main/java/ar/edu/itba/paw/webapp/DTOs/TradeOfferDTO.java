package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.TradeOffer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeOfferDTO {

    private int id;
    private UserDTO owner;
    private TradeOfferResourceDTO offer;
    private TradeOfferResourceDTO receive;

    public TradeOfferDTO(TradeOffer tradeOffer) {
        this.id = tradeOffer.getId();
        this.owner = new UserDTO(tradeOffer.getOwner());
        this.offer = new TradeOfferResourceDTO(tradeOffer.getOfferType(), tradeOffer.getOfferAmount());
        this.receive = new TradeOfferResourceDTO(tradeOffer.getReceiveType(), tradeOffer.getReceiveAmount());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    public TradeOfferResourceDTO getOffer() {
        return offer;
    }

    public void setOffer(TradeOfferResourceDTO offer) {
        this.offer = offer;
    }

    public TradeOfferResourceDTO getReceive() {
        return receive;
    }

    public void setReceive(TradeOfferResourceDTO receive) {
        this.receive = receive;
    }
}
