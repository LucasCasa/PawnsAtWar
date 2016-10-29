package ar.edu.itba.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

@Repository
public class BuildingHibernateDao implements BuildingDao {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private UserDao ud;
	
	@Override
	public Integer getLevel(Point p) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.p = :p",Sector.class);
		query.setParameter("p",p);
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? null :list.get(0).getLevel();
	}

	@Override
	public void setLevel(Point p, int level) {
		final Query query = em.createQuery("update Sector set level = :level where p = :p");
		query.setParameter("level",level);
		query.setParameter("p",p);
		query.executeUpdate();

	}

	@Override
	public List<Sector> getBuildings(Point p, int range) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where ((t.p.x BETWEEN :xmin AND :xmax) AND (t.p.y BETWEEN :ymin AND :ymax))",Sector.class);
		query.setParameter("ymin",p.getY()-range);
		query.setParameter("xmin", p.getX()-range);
		query.setParameter("xmax", p.getX()+range);
		query.setParameter("ymax", p.getY()+ range);
		final List<Sector> list = query.getResultList();
		return list;
	}

	@Override
	public Sector getBuilding(Point p) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.p = :p",Sector.class);
		query.setParameter("p",p);
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Integer getIdPlayer(Point p) {
		Sector s = getBuilding(p);
		return s.getUser().getId();
	}

	@Override
	public void setIdPlayer(Point p, int idPlayer) {
		final Query query = em.createQuery("update Sector set userBuilding.id = :idPlayer where p = :p");
		query.setParameter("idPlayer",idPlayer);
		query.setParameter("p",p);
		query.executeUpdate();

	}

	@Override
	public Sector addBuilding(Point p, int level, int idPlayer, int type) {
		Sector b = new Sector(ud.findById(idPlayer),p,type,level);
		em.persist(b);
		return b;
	}

	@Override
	public Sector addBuilding(Point p, int idPlayer, int type) {
		return addBuilding(p,1,idPlayer,type);
	}

	@Override
	public boolean belongsTo(Point p, int idPlayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteBuilding(Point p) {
		final Query query = em.createQuery("delete Sector where p = :p");
		query.setParameter("p",p);
		query.executeUpdate();

	}

	@Override
	public Point getCastle(int idPlayer) {
		final TypedQuery<Sector> query = em.createQuery("from Sector where userBuilding.id = :idPlayer and type = :type",Sector.class);
		query.setParameter("idPlayer",idPlayer);
		query.setParameter("type", 1);
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getPosition();
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		List<Sector> list = getBuildings(p, range);
		if(list.isEmpty() || list.size() > 1){
			return false;
		}
		return true;
	}

	@Override
	public List<Sector> getBuildings(int userId, int type) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.userBuilding.id = :idPlayer and t.type = :type",Sector.class);
		query.setParameter("idPlayer", userId);
		query.setParameter("type", type);
		List<Sector> list = query.getResultList();
		return list;
	}

	@Override
	public List<Point> getAllCastles() {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.type = :type",Sector.class);
		query.setParameter("type", 1);
		List<Sector> list = query.getResultList();
		List<Point> listPoint = new ArrayList<Point>();
		for(Sector s: list){
			listPoint.add(s.getPosition());
		}
		return listPoint;
	}

	@Override
	public List<Sector> getBuildings(int idPlayer) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.idPlayer = :idPlayer",Sector.class);
		query.setParameter("idPlayer", idPlayer);
		List<Sector> list = query.getResultList();
		return list;
	}

	@Override
	public int getType(Point p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setType(Point p, int type) {
		final Query query = em.createQuery("update Sector set type = :type where x = :x AND y = :y");
		query.setParameter("type",type);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		query.executeUpdate();
		
	}

}
