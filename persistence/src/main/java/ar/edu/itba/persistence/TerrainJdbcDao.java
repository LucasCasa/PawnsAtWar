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

import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.Terrain;

@Repository
public class TerrainJdbcDao implements TerrainDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public TerrainJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("terrain");
	}

	@Override
	public Integer getPower(Point position) {
		if(position.getX() < 0 || position.getY() < 0){
			return null;
		}
		List<Integer> terrainList = jdbcTemplate
				.query("SELECT * FROM terrain WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("power");
						},position.getX(),position.getY());
		return terrainList.isEmpty() ? null:terrainList.get(0);
	}

	@Override
	public Integer getType(Point position) {
		if(position.getX() < 0 || position.getY() < 0){
			return null;
		}
		List<Integer> terrainList = jdbcTemplate
				.query("SELECT * FROM terrain WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("type");
						},position.getX(),position.getY());
		return terrainList.isEmpty() ? null:terrainList.get(0);
	}
	
	@Override
	public List<Sector> getTerrain(Point p, int range) {
        List<Sector> terrainList = jdbcTemplate
                .query("SELECT * FROM terrain WHERE ((x BETWEEN ? AND ?) AND (y BETWEEN ? AND ?))",(ResultSet resultSet, int rowNum) -> {
                    return new Sector(new Point(resultSet.getInt("x"),resultSet.getInt("y")),resultSet.getInt("type"));
                },p.getX()-range,p.getX() + range, p.getY() - range, p.getY()+range);

        return terrainList;
	}


	@Override
	public Sector getTerrain(Point p) {
		List<Sector> terrains =  getTerrain(p,0);
		return terrains.isEmpty() ? null : terrains.get(0);
	}


	@Override
	public Terrain setPower(Point p, int power) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Terrain setType(Point p, int t) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Terrain addTerrain(Point p, int power, int t) {
		final Map<String,Object> args = new HashMap<>();
		args.put("x", p.getX());
		args.put("y", p.getY());
		args.put("power",power);
		args.put("type", t);
		jdbcInsert.execute(args);
		return new Terrain(p,power,t);
	}


	@Override
	public Terrain addTerrain(Point p, int t) {
		return addTerrain(p,0,t);
	}


	@Override
	public Terrain addTerrain(Point p) {
		return addTerrain(p,0,0);
	}

}
