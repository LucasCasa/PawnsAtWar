package ar.edu.itba.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.Terrain;
import ar.edu.itba.model.User;

public class TerrainHibernateDao implements TerrainDao {
	 @PersistenceContext
	 private EntityManager em;
	 
	 @Autowired
	 private UserDao ud;

	@Override
	public Integer getPower(Point position) {
		final TypedQuery<Terrain> query = em.createQuery("from Terrain as t where t.x = :x and t.y = :y:",Terrain.class);
		query.setParameter("x", position.getX());
		query.setParameter("y", position.getY());
		final List<Terrain> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getPower(); 
	}

	@Override
	public Integer getType(Point position) {
		Sector s = getTerrain(position);
		return s.getType();
	}

	@Override
	public List<Sector> getTerrain(Point p, int range) {
		final TypedQuery<Sector> query = em.createQuery("from Terrain as t where ((t.x BETWEEN :xmin AND :xmax) AND (t.y BETWEEN :ymin AND :ymax))",Sector.class);
		query.setParameter("ymin",p.getY()-range);
		query.setParameter("xmin", p.getX()-range);
		query.setParameter("xmax", p.getX()+range);
		query.setParameter("ymax", p.getY()+ range);
		final List<Sector> list = query.getResultList();
		return list;
	}

	@Override
	public Sector getTerrain(Point p) {
		final TypedQuery<Sector> query = em.createQuery("from Terrain as t where t.x = :x and t.y = :y", Sector.class);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		final List<Sector> list = query.getResultList();
		return list.isEmpty() ? null :list.get(0);
	}

	@Override
	public void setPower(Point p, int power) {
		final Query query = em.createQuery("update Terrain set power = :power where x = :x AND y = :y");
		query.setParameter("power",power);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		query.executeUpdate();
	}

	@Override
	public void setType(Point p, int t) {
		final Query query = em.createQuery("update Terrain set type = :type where x = :x AND y = :y");
		query.setParameter("type",t);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		query.executeUpdate();
	}

	@Override
	public Terrain addTerrain(Point p, int power, int idPlayer, int t) {
		User u = ud.findById(idPlayer);
		Terrain terr = new Terrain(p, t, power, u);
		em.persist(terr);
		return terr;
	}

	@Override
	public Terrain addTerrain(Point p, int t) {
		return addTerrain(p,0,0,t);
	}

	@Override
	public Terrain addTerrain(Point p) {
		return addTerrain(p,0,0,0);
	}

	@Override
	public Integer getId(Point p) {
		final TypedQuery<Terrain> query = em.createQuery("from Terrain as t where t.x = :x and t.y = ;y",Terrain.class);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		final List<Terrain> list = query.getResultList();
		return list.isEmpty() ? null : list.get(0).getUser().getId();
	}

	@Override
	public void setIdPlayer(Point p, Integer idPlayer) {
		final Query query = em.createQuery("update Terrain set idPlayer = :idPlayer where x = :x AND y = :y");
		query.setParameter("idPlayer",idPlayer);
		query.setParameter("x", p.getX());
		query.setParameter("y", p.getY());
		query.executeUpdate();

	}

	@Override
	public void deleteTerrain(Point p) {
		final Query query = em.createQuery("delete Terrain where x = :x and y = :y");
		query.setParameter("y", p.getY());
		query.setParameter("x", p.getX());
		query.executeUpdate();

	}

}
