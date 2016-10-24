package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.Terrain;

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
	public void setIdPlayer(Point p, int idPlayer) {
		final Query query = em.createQuery("update Building set idPlayer = :idPlayer where x = :x AND y = :y");
		query.setParameter("idPlayer",idPlayer);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		query.executeUpdate();

	}

	@Override
	public Building addBuilding(Point p, int level, int idPlayer, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Building addBuilding(Point p, int idPlayer, int type) {
		return addBuilding(p,1,idPlayer,type);
	}

	@Override
	public boolean belongsTo(Point p, int idPlayer) {
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
	public Point getCastle(int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Building> getBuildings(int userId, int type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Point> getAllCastles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Building> getBuildings(int idPlayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType(Point p) {
		// TODO Auto-generated method stub
		return 0;
	}

}
