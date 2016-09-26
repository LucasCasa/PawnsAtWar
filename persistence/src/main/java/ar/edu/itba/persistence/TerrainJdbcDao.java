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
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("TERRAIN");
	}

	@Override
	public Integer getPower(Point position) {
		if(position.getX() < 0 || position.getY() < 0){
			return null;
		}
		List<Integer> terrainList = jdbcTemplate
				.query("SELECT * FROM TERRAIN WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
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
				.query("SELECT * FROM TERRAIN WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("type");
						},position.getX(),position.getY());
		return terrainList.isEmpty() ? null:terrainList.get(0);
	}
	
	@Override
	public List<Sector> getTerrain(Point p, int range) {
        List<Sector> terrainList = jdbcTemplate
                .query("SELECT * FROM TERRAIN WHERE ((x BETWEEN ? AND ?) AND (y BETWEEN ? AND ?))",(ResultSet resultSet, int rowNum) -> {
                    return new Sector(new Point(resultSet.getInt("x"),resultSet.getInt("y")),resultSet.getInt("type"),resultSet.getInt("idPlayer"));
                },p.getX()-range,p.getX() + range, p.getY() - range, p.getY()+range);

        return terrainList;
	}


	@Override
	public Sector getTerrain(Point p) {
		List<Sector> terrains =  getTerrain(p,0);
		return terrains.isEmpty() ? null : terrains.get(0);
	}


	@Override
	public void setPower(Point p, int power) {
		jdbcTemplate.update("UPDATE TERRAIN SET power = ? WHERE x = ? AND y = ?", power,p.getX(),p.getY());
	}


	@Override
	public void setType(Point p, int t) {
		jdbcTemplate.update("UPDATE TERRAIN SET type = ? WHERE x = ? AND y = ?", t,p.getX(),p.getY());
	}


	@Override
	public Terrain addTerrain(Point p, int power,int idPlayer ,int t) {
		final Map<String,Object> args = new HashMap<>();
		args.put("x", p.getX());
		args.put("y", p.getY());
		args.put("power",power);
		args.put("idPlayer", idPlayer);
		args.put("type", t);
		jdbcInsert.execute(args);
		return new Terrain(p,power,t,idPlayer);
	}


	@Override
	public Terrain addTerrain(Point p, int t) {
		return addTerrain(p,0,-1,t);
	}


	@Override
	public Terrain addTerrain(Point p) {
		return addTerrain(p,0,-1,0);
	}

	@Override
	public int getMaxX() {
		 List<Integer> maxX = jdbcTemplate
	                .query("SELECT max(x) as aux FROM TERRAIN",(ResultSet resultSet, int rowNum) -> {
	                    return resultSet.getInt("aux");
	                });

	        return maxX.isEmpty() ? -1 : maxX.get(0);
		
	}
	
	@Override
	public int getMaxY() {
		 List<Integer> maxY = jdbcTemplate
	                .query("SELECT max(y) as aux FROM TERRAIN",(ResultSet resultSet, int rowNum) -> {
	                    return resultSet.getInt("aux");
	                });

	        return maxY.isEmpty() ? -1 : maxY.get(0);
		
	}

	@Override
	public int getId(Point p) {
		List<Integer> terrainList = jdbcTemplate
				.query("SELECT * FROM TERRAIN WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("idPlayer");
						},p.getX(),p.getY());
		return terrainList.isEmpty() ? null:terrainList.get(0);
	}
	
	

}
