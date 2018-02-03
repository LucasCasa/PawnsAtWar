package ar.edu.itba.paw.webapp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeOfferResourceDTO {

    private int type;
    private int amount;

    public TradeOfferResourceDTO() {}

    public TradeOfferResourceDTO(int type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
