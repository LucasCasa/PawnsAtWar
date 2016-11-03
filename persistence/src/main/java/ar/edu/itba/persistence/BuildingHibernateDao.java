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
		return list.isEmpty() ? null :list.get(0).getLevel();
	}

	@Override
	public void setLevel(Point p, int level) {
		final Query query = em.createQuery("update Building set level = :level where x = :x AND y = :y");
		query.setParameter("level",level);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
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
	public void setIdPlayer(Point p, User u) {
		final Query query = em.createQuery("update Sector set userBuilding.id = :idPlayer where p = :p");
		query.setParameter("idPlayer",u);
		query.setParameter("p",p);
		query.executeUpdate();

	}

	@Override
	public Sector addBuilding(Point p, int level, User u, int type) {
		Sector b = new Sector(u,p,type,level);
		em.persist(b);
		return b;
	}

	@Override
	public Sector addBuilding(Point p, User u, int type) {
		return addBuilding(p,1,u,type);
	}

	@Override
	public boolean belongsTo(Point p, User u) {
		// TODO Autogenerated method stub
		return false;
	}

	@Override
	public void deleteBuilding(Point p) {
		final Query query = em.createQuery("delete Sector where p = :p");
		query.setParameter("p",p);
		query.executeUpdate();

	}

	@Override
	public Point getCastle(User u) {
		final TypedQuery<Sector> query = em.createQuery("from Sector where userBuilding = :u and type = :type",Sector.class);
		query.setParameter("u",u);
		query.setParameter("type", 1);
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getPosition();
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
