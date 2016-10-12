package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.TradeOffer;

public interface CommerceService {
	
	/**
	 * Gets a list with all existing trade offers
	 * @return The list containing all trade offers
	 */
	public List<TradeOffer> getAllOffers();
	
	/**
	 * Gets the trade offer with the specified id
	 * @param id The id of the trade offer to be retrieved
	 * @return The trade offer with id as specified
	 */
	public TradeOffer getOffer(int id);
	
	/**
	 * The specified trade offer is accepted by user with id userIdPartner
	 * @param to The accepted trade offer
	 * @param userIdPartner The user which accepts the offer
	 */
	public void acceptOffer(TradeOffer to, int userIdPartner);

	/**
	 * Removes a trade offer, replenishing the offered resources
	 * @param to The trade offer to be removed
	 */
	public void removeOffer(TradeOffer to);
	
	/**
	 * Removes the trade offer
	 * @param id The id of the trade to be removed
	 */
	public void deleteOffer(int id);

	/**
	 * Retrieves all offers created by the user
	 * @param id The user's id
	 * @return A list with all the trade offers
	 */
	public List<TradeOffer> getAllOffers(int id);
	
	/**
	 * Displays all available offers for a certain user
	 * @param id The user's id
	 * @return A list with all tradeoffers avaiable for the user
	 */
	public List<TradeOffer> showOffers(int id);
	
	/**
	 * Creates a trade offer
	 * @param id The user's id
	 * @param giveType The resource type to be offered
	 * @param giveAmount The resource amount offered
	 * @param getType The resource type to be received
	 * @param receiveAmount The resource amount received
	 * @return Whether the creation was successful
	 */
	public boolean createOffer(int id, int giveType, int giveAmount, int getType, int receiveAmount);
}
