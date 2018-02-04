package ar.edu.itba.paw.webapp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by lucas on 04/02/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeOffersDTO {

    List<TradeOfferDTO> mine;
    List<TradeOfferDTO> others;

    public TradeOffersDTO(List<TradeOfferDTO> mine, List<TradeOfferDTO> others){
        this.mine = mine;
        this.others = others;
    }

    public List<TradeOfferDTO> getMine() {
        return mine;
    }

    public void setMine(List<TradeOfferDTO> mine) {
        this.mine = mine;
    }

    public List<TradeOfferDTO> getOthers() {
        return others;
    }

    public void setOthers(List<TradeOfferDTO> others) {
        this.others = others;
    }
}
