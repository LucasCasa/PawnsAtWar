package ar.edu.itba.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.model.Buildings;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

@Repository
public class BuildingJdbcDao implements BuildingDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public BuildingJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("buildings");
	}

	@Override
	public Integer getLevel(Point p) {
		if(p.getX() < 0 || p.getY() < 0){
			return null;
		}
		List<Integer> buildingList = jdbcTemplate
				.query("SELECT * FROM buildings WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("level");
						},p.getX(),p.getY());
		return buildingList.isEmpty() ? null:buildingList.get(0);
	}

	@Override
	public Integer setLevel(Point p,int level) {
		return null;
	}

	@Override
	public List<Sector> getBuildings(Point p, int range) {
			if(range < 0){
				return null;
			}
			
	        List<Sector> buildingList = jdbcTemplate
	                .query("SELECT * FROM terrain WHERE ((x BETWEEN ? AND ?) AND (y BETWEEN ? AND ?))",(ResultSet resultSet, int rowNum) -> {
	                    return new Sector(new Point(resultSet.getInt("x"),resultSet.getInt("y")),resultSet.getInt("type"));
	                },p.getX()-range,p.getX() + range, p.getY() - range, p.getY()+range);

	        return buildingList;
	}

	@Override
	public Sector getBuilding(Point p) {
		List<Sector> buildings = getBuildings(p,0);
		return buildings.isEmpty() ? null : buildings.get(0);
	}

	@Override
	public Integer getIdPlayer(Point p) {
		if(p.getX() < 0 || p.getY() < 0){
			return null;
		}
		List<Integer> buildingList = jdbcTemplate
				.query("SELECT * FROM buildings WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("idPlayer");
						},p.getX(),p.getY());
		return buildingList.isEmpty() ? null:buildingList.get(0);
	}

	@Override
	public Buildings setIdPlayer(Point p) {
		if(p.getX() < 0 || p.getY() < 0){
			return null;
		}
		return null;
	}

	@Override
	public Buildings addBuilding(Point p, int level, int idPlayer, int type) {
		final Map<String,Object> args = new HashMap<>();
		args.put("x", p.getX());
		args.put("y", p.getY());
		args.put("type",type);
		args.put("idPlayer", idPlayer);
		args.put("type", type);
		jdbcInsert.execute(args);
		return new Buildings(p,type,level);
	}

	@Override
	public Buildings addBuilding(Point p, int idPlayer, int type) {
		return addBuilding(p,0,idPlayer,type);
	}

	@Override
	public boolean belongsTo(Point p, User u) {
		return false;
	}
	

}
