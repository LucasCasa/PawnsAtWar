package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.CommerceService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.model.TradeOffer;

@Service
public class CommerceServiceImpl implements CommerceService{
	
	@Autowired
	CommerceDao cd;
	@Autowired
	EmpireService es;

	@Override
	public List<TradeOffer> getAllOffers() {
		return cd.getAllOffers();
	}

	@Override
	public TradeOffer getOffer(int id) {
		return cd.getOffer(id);
	}

	@Override
	public void deleteOffer(int id) {
		cd.removeOffer(id);
	}

	@Override
	public void acceptOffer(TradeOffer to, int userIdPartner) {
		deleteOffer(to.getId());
		
		//Adds resources to the user which offered
		es.addResourceAmount(to.getOwner().getId(),to.getReceives().getType(),to.getReceives().getQuantity());
		//Removes resources from the user which accepted
		es.subtractResourceAmount(userIdPartner,to.getReceives().getType(),to.getReceives().getQuantity());
		//Adds resources to the user which accepted
		es.addResourceAmount(userIdPartner,to.getOffer().getType(),to.getOffer().getQuantity());
	}

	@Override
	public List<TradeOffer> getAllOffers(int id) {
		return cd.getAllOffers(id);
	}

	@Override
	public List<TradeOffer> showOffers(int id) {
		List<TradeOffer> list = cd.getAllOffers();
		List<TradeOffer> own = cd.getAllOffers(id);
		list.removeAll(own);
		return list;
	}

	@Override
	public void removeOffer(TradeOffer to) {
		es.addResourceAmount(to.getOwner().getId(), to.getOffer().getType(), to.getOffer().getQuantity());
		deleteOffer(to.getId());
	}

	@Override
	public boolean createOffer(int id, int giveType, int giveAmount, int getType, int receiveAmount) {
		if(es.getResource(id, giveType).getQuantity()<giveAmount)
			return false;
		es.subtractResourceAmount(id, giveType, giveAmount);
		cd.createOffer(id,giveType,giveAmount,getType,receiveAmount);
		return true;
	}

}
