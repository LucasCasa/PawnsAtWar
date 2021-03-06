package ar.edu.itba.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

@Repository
public class BuildingHibernateDao implements BuildingDao {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Integer getLevel(Point p) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.p = :p",Sector.class);
		query.setParameter("p",p);
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? 0 :list.get(0).getLevel();
	}

	@Override
	public void setLevel(Point p, int level) {
		Sector s = getBuilding(p);
		s.setLevel(level);
		em.persist(s);

	}

	@Override
	public List<Sector> getBuildings(Point p, int range) {
		if(range < 0 || p == null)
			return null;
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
	public User getPlayer(Point p) {
		Sector s = getBuilding(p);
		return s.getUser();
	}
	@Override
	public Integer getIdPlayer(Point p) {
		Sector s = getBuilding(p);
		return s.getUser().getId();
	}

	@Override
	public void setIdPlayer(Point p, User u) {
		Sector s = getBuilding(p);
		s.setUser(u);
		em.persist(s);

	}

	@Override
	public Sector addBuilding(Point p, int level, User u, int type) {
		if(p == null || getBuilding(p) != null || p.getX() < 0 || p.getY() < 0)
			return null;
		Sector b = new Sector(u,p,type,level);
		em.persist(b);
		return b;
	}

	@Override
	public Sector addBuilding(Point p, User u, int type) {
		return addBuilding(p,1,u,type);
	}


	@Override
	public void deleteBuilding(Point p) {
		final Query query = em.createQuery("delete Sector where p = :p");
		query.setParameter("p",p);
		query.executeUpdate();

	}

	@Override
	public Point getCastle(User u) {
		if(u == null)
			return null;
		return getAllCastles(u).get(0);
	}

	@Override
	public List<Point> getAllCastles(User u) {
		List<Point> res = new ArrayList<>();
		if(u == null)
			return null;
		final TypedQuery<Sector> query = em.createQuery("from Sector where userBuilding = :u and type = :type",Sector.class);
		query.setParameter("u",u);
		query.setParameter("type", 1);
		final List<Sector> list = query.getResultList();
		for(Sector s:list){
			res.add(s.getPosition());
		}
		return res;
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		int cant= 0;
		List<Sector> list = getBuildings(p, range);
		for(Sector s: list){
			if(s.getType() != 5 && s.getType() != 0){
				cant ++;
			}
		}
		if(cant > 1){
			return false;
		}
		return true;
	}

	@Override
	public List<Sector> getBuildings(User u, int type) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.userBuilding = :u and t.type = :type",Sector.class);
		query.setParameter("u", u);
		query.setParameter("type", type);
		return query.getResultList();
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
	public List<Sector> getBuildings(User u) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.userBuilding = :u",Sector.class);
		query.setParameter("u", u);
		List<Sector> list = query.getResultList();
		return list;
	}

}
