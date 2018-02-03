package ar.edu.itba.paw.webapp.DTOs;

public class TradeOfferCreateDTO {

    private TradeOfferResourceDTO offer;

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

    private TradeOfferResourceDTO receive;

}
