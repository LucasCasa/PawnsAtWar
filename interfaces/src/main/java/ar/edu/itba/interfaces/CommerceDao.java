package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.TradeOffer;

public interface CommerceDao {
	
	public List<TradeOffer> getAllOffers();

	public TradeOffer getOffer(int id);

	public void removeOffer(int id);

	public List<TradeOffer> getAllOffers(int userid);

	public void createOffer(int id, int giveType, int giveAmount, int getType, int receiveAmount);
}
