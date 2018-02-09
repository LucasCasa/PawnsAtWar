package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.CommerceDao;
import ar.edu.itba.interfaces.CommerceService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.model.TradeOffer;
import ar.edu.itba.model.User;

@Service
@Transactional
public class CommerceServiceImpl implements CommerceService {

  @Autowired
  private CommerceDao cd;
  @Autowired
  private EmpireService es;

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
  public void acceptOffer(TradeOffer to, User u) {
    deleteOffer(to.getId());

    //Adds resources to the user which offered
    es.addResourceAmount(to.getOwner(), to.getReceiveType(), to.getReceiveAmount());
    //Removes resources from the user which accepted
    es.subtractResourceAmount(u, to.getReceiveType(), to.getReceiveAmount());
    //Adds resources to the user which accepted
    es.addResourceAmount(u, to.getOfferType(), to.getOfferAmount());
  }

  @Override
  public List<TradeOffer> getAllOffers(User u) {
    if (u == null) {
      return null;
    }
    return cd.getAllOffers(u);
  }

  @Override
  public List<TradeOffer> showOffers(User u) {
    if (u == null) {
      return new ArrayList<>();
    }
    List<TradeOffer> list = cd.getAllOffers();
    List<TradeOffer> own = cd.getAllOffers(u);
    list.removeAll(own);
    return list;
  }

  @Override
  public void removeOffer(TradeOffer to) {
    if (!cd.getAllOffers().contains(to)) {
      return;
    }
    es.addResourceAmount(to.getOwner(), to.getOfferType(), to.getOfferAmount());
    deleteOffer(to.getId());
  }

  @Override
  public boolean createOffer(User u, int giveType, int giveAmount, int getType, int receiveAmount) {
    if (giveType == getType || giveAmount <= 0 || receiveAmount <= 0) {
      return false;
    }
    if (es.getResource(u, giveType).getQuantity() < giveAmount) {
      return false;
    }
    es.subtractResourceAmount(u, giveType, giveAmount);
    cd.createOffer(u, giveType, giveAmount, getType, receiveAmount);
    return true;
  }

  public void setEmpireService(EmpireService empireService) {
    this.es = empireService;
  }

  public void setCommerceDao(CommerceDao commerceDao) {
    this.cd = commerceDao;
  }

}
