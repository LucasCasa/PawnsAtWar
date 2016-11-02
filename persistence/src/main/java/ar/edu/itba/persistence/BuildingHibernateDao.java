package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

<<<<<<< HEAD
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;
=======
import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.Terrain;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf

public class BuildingHibernateDao implements BuildingDao {

	@PersistenceContext
    private EntityManager em;
	
	@Override
	public Integer getLevel(Point p) {
		final TypedQuery<Building> query = em.createQuery("from Building as t where t.x = :x and t.y = :y:",Building.class);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		final List<Building> list = query.getResultList();
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
		final TypedQuery<Sector> query = em.createQuery("from Building as t where ((t.x BETWEEN :xmin AND :xmax) AND (t.y BETWEEN :ymin AND :ymax))",Sector.class);
		query.setParameter("ymin",p.getY()-range);
		query.setParameter("xmin", p.getX()-range);
		query.setParameter("xmax", p.getX()+range);
		query.setParameter("ymax", p.getY()+ range);
		final List<Sector> list = query.getResultList();
		return list;
	}

	@Override
	public Sector getBuilding(Point p) {
		final TypedQuery<Sector> query = em.createQuery("from Building as t where t.x = :x and t.y = :y:",Sector.class);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Integer getIdPlayer(Point p) {
		Sector s = getBuilding(p);
		return s.getUser().getId();
	}

	@Override
<<<<<<< HEAD
	public void setIdPlayer(Point p, User u) {
		final Query query = em.createQuery("update Sector set userBuilding.id = :idPlayer where p = :p");
		query.setParameter("idPlayer",u);
		query.setParameter("p",p);
=======
	public void setIdPlayer(Point p, int idPlayer) {
		final Query query = em.createQuery("update Building set idPlayer = :idPlayer where x = :x AND y = :y");
		query.setParameter("idPlayer",idPlayer);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
		query.executeUpdate();

	}

	@Override
<<<<<<< HEAD
	public Sector addBuilding(Point p, int level, User u, int type) {
		Sector b = new Sector(u,p,type,level);
		em.persist(b);
		return b;
	}

	@Override
	public Sector addBuilding(Point p, User u, int type) {
		return addBuilding(p,1,u,type);
=======
	public Building addBuilding(Point p, int level, int idPlayer, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Building addBuilding(Point p, int idPlayer, int type) {
		return addBuilding(p,1,idPlayer,type);
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	}

	@Override
	public boolean belongsTo(Point p, User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteBuilding(Point p) {
		final Query query = em.createQuery("delete Building where x = :x and y = :y");
		query.setParameter("y", p.getY());
		query.setParameter("x", p.getX());
		query.executeUpdate();

	}

	@Override
<<<<<<< HEAD
	public Point getCastle(User u) {
		final TypedQuery<Sector> query = em.createQuery("from Sector where userBuilding = :u and type = :type",Sector.class);
		query.setParameter("u",u);
		query.setParameter("type", 1);
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getPosition();
=======
	public Point getCastle(int idPlayer) {
		// TODO Auto-generated method stub
		return null;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
<<<<<<< HEAD
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
=======
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Building> getBuildings(int userId, int type) {
		// TODO Auto-generated method stub
		return null;
>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
	}

	@Override
	public List<Point> getAllCastles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
<<<<<<< HEAD
	public List<Sector> getBuildings(User u) {
		final TypedQuery<Sector> query = em.createQuery("from Sector as t where t.userBuilding = :u",Sector.class);
		query.setParameter("u", u);
		List<Sector> list = query.getResultList();
		return list;
	}

=======
	public List<Building> getBuildings(int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType(Point p) {
		// TODO Auto-generated method stub
		return 0;
	}

>>>>>>> d9433c89c73caca8960c804bdb6b8b63df0fe4cf
}
