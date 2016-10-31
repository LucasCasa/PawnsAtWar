package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;

public interface CommerceDao {
	
	public List<TradeOffer> getAllOffers();

	public TradeOffer getOffer(int id);

	public void removeOffer(int id);

	public List<TradeOffer> getAllOffers(User u);

	public void createOffer(User u, int giveType, int giveAmount, int getType, int receiveAmount);
}
