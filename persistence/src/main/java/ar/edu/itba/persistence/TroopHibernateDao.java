package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.TroopDao;
import ar.edu.itba.model.Troop;


@Repository
public class TroopHibernateDao implements TroopDao {
	 @PersistenceContext
	 private EntityManager em;
	 
	 @Autowired
	 private ArmyDao ad;

	@Override
	public int getAmount(int idArmy, int type) {
		if(type < 0){
			return 0;
		}
		Troop t = getTroop(idArmy, type);
		return t == null ? 0 : t.getQuantity();
	}

	@Override
	public List<Troop> getAllTroop(int idArmy) {
		final TypedQuery<Troop> query = em.createQuery("from Troop as t where t.army.idArmy = :idArmy",Troop.class);
		query.setParameter("idArmy", idArmy);
		final List<Troop> list = query.getResultList();
		return list;
	}

	@Override
	public void changeAmount(int idArmy, int type, int amount) {
		if(amount < 0)
			return;
		Troop t = getTroop(idArmy, type);
		t.setQuantity(amount);
		em.persist(t);
	}

	@Override
	public void deleteTroop(int idArmy, int type) {
		if(!exists(idArmy,type)){
			return;
		}
		if(type < 0){
			return;
		}
		final Query query = em.createQuery("delete Troop where army.idArmy = :idArmy and type = :type");
		query.setParameter("idArmy", idArmy);
		query.setParameter("type", type);
		query.executeUpdate();
	}

	@Override
	public Troop addTroop(int idArmy, int type, int amount) {
		if(exists(idArmy,type)){
			return getTroop(idArmy, type);
		}
		if(amount < 0){
			amount = 0;
		}
		final Troop troop = new Troop(ad.getArmyById(idArmy),type,amount);
		em.persist(troop);
		return troop;
	}

	@Override
	public boolean exists(int idArmy, int type) {
		if(type < 0){
			return false;
		}
		if(getTroop(idArmy,type) == null){
			return false;
		}
		return true;
	}

	@Override
	public Troop getTroop(int idArmy, int type) {
		if(type < 0){
			return null;
		}
		final TypedQuery<Troop> query = em.createQuery("from Troop as t where t.army.idArmy = :idArmy and t.type = :type",Troop.class);
		query.setParameter("idArmy", idArmy);
		query.setParameter("type", type);
		final List<Troop> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public int getAmountTroops(int idArmy) {
		List<Troop> t = getAllTroop(idArmy);
		return t.isEmpty() ? 0 : t.size();
	}

}
