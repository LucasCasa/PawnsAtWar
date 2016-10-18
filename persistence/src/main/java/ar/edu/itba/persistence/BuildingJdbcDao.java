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
import ar.edu.itba.interfaces.UserDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;

@Repository
public class BuildingJdbcDao implements BuildingDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	UserDao ud;
	
	@Autowired
	public BuildingJdbcDao(final DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("BUILDING");
	}

	@Override
	public Integer getLevel(Point p) {
		if(p.getX() < 0 || p.getY() < 0){
			return null;
		}
		List<Integer> buildingList = jdbcTemplate
				.query("SELECT * FROM BUILDING WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("level");
						},p.getX(),p.getY());
		return buildingList.isEmpty() ? 0:buildingList.get(0);
	}

	@Override
	public void setLevel(Point p,int level) {
		jdbcTemplate
		.update("UPDATE BUILDING SET level = ? WHERE x = ? AND y = ?",level,p.getX(),p.getY());
	}

	@Override
	public List<Sector> getBuildings(Point p, int range) {
			if(range < 0){
				return null;
			}
			
	        List<Sector> buildingList = jdbcTemplate
	                .query("SELECT * FROM BUILDING WHERE ((x BETWEEN ? AND ?) AND (y BETWEEN ? AND ?))",(ResultSet resultSet, int rowNum) -> {
	                    return new Building(new Point(resultSet.getInt("x"),resultSet.getInt("y")),ud.findById(resultSet.getInt("idPlayer")),resultSet.getInt("type"),resultSet.getInt("level"));
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
		List<Integer> buildingList = jdbcTemplate
				.query("SELECT * FROM BUILDING WHERE x = ?  AND y = ?",(ResultSet resultSet, int rowNum) -> {
							return resultSet.getInt("idPlayer");
						},p.getX(),p.getY());
		return buildingList.isEmpty() ? 0:buildingList.get(0);
	}

	@Override
	public void setIdPlayer(Point p,int idPlayer) {
		jdbcTemplate
		.update("UPDATE BUILDING SET idPlayer = ? WHERE x = ? AND y = ?",idPlayer,p.getX(),p.getY());
	}

	@Override
	public Building addBuilding(Point p, int level, int idPlayer, int type) {
		final Map<String,Object> args = new HashMap<>();
		args.put("x", p.getX());
		args.put("y", p.getY());
		args.put("type",type);
		args.put("idPlayer", idPlayer);
		args.put("level", level);
		jdbcInsert.execute(args);
		return new Building(p,ud.findById(idPlayer),type,level);
	}

	@Override
	public Building addBuilding(Point p, int idPlayer, int type) {
		return addBuilding(p,1,idPlayer,type);
	}

	@Override
	public boolean belongsTo(Point p, int idPlayer) {
		int id = getIdPlayer(p);
		if(idPlayer == id){ //Falta verificar aca si es un aliado
			return true;
		}
		return false;
		
	}

	@Override
	public void deleteBuilding(Point p) {
		jdbcTemplate.update("DELETE FROM BUILDING WHERE x = ? AND y = ?", p.getX(),p.getY());
				
	}
	
	public int getMaxX(){
		 List<Integer> maxX = jdbcTemplate
	                .query("SELECT max(x) as aux FROM BUILDING",(ResultSet resultSet, int rowNum) -> {
	                    return resultSet.getInt("aux");
	                });

	        return maxX.isEmpty() ? -1 : maxX.get(0);
	}

	@Override
	public int getMaxY() {
		 List<Integer> maxX = jdbcTemplate
	                .query("SELECT max(y) as aux FROM BUILDING",(ResultSet resultSet, int rowNum) -> {
	                    return resultSet.getInt("aux");
	                });

	        return maxX.isEmpty() ? -1 : maxX.get(0);
	}
	@Override
	public Point getCastle(int idPlayer){
		List<Point> castles = jdbcTemplate
				.query("SELECT * FROM BUILDING WHERE type=1 AND idPlayer= ?",(ResultSet resultSet, int rowNum) -> {
				return new Point(resultSet.getInt("x"),resultSet.getInt("y"));
				},idPlayer);
		if(castles.isEmpty()){
			throw new RuntimeException("Un jugador no puede no tener castillos");
		}
		return castles.get(0);
	}

	@Override
	public boolean isCastleAlone(Point p, int range) {
		List<Integer> buildings = jdbcTemplate
				.query("SELECT count(*) FROM building WHERE ((x BETWEEN ? AND ?) AND (y BETWEEN ? AND ?)) ",(ResultSet resultSet,int rowNum) -> {
					return new Integer(1);
				},p.getX()- range,p.getX() + range,p.getY() - range, p.getY() + range);
		if(buildings.isEmpty() || buildings.get(0) > 1){
			return false;
		}
		return true;
	}

	@Override
	public List<Building> getBuildings(int userId, int type) {
		List<Building> buildingList = jdbcTemplate
                .query("SELECT * FROM building WHERE type = ? AND idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
                    return new Building(new Point(resultSet.getInt("x"),resultSet.getInt("y")),ud.findById(resultSet.getInt("idPlayer")),resultSet.getInt("type"),resultSet.getInt("level"));
                },type,userId);
        return buildingList;
	}

	@Override
	public List<Point> getAllCastles() {
		List<Point> buildingList = jdbcTemplate
                .query("SELECT * FROM building WHERE type = ?",(ResultSet resultSet, int rowNum) -> {
                    return new Point(resultSet.getInt("x"),resultSet.getInt("y"));
                },1);
        return buildingList;
	}

	@Override
	public List<Building> getBuildings(int idPlayer) {
		List<Building> buildingList = jdbcTemplate
                .query("SELECT * FROM building WHERE idPlayer = ?",(ResultSet resultSet, int rowNum) -> {
                    return new Building(new Point(resultSet.getInt("x"),resultSet.getInt("y")),ud.findById(resultSet.getInt("idPlayer")),resultSet.getInt("type"),resultSet.getInt("level"));
                },idPlayer);
        return buildingList;
	}

	@Override
	public int getType(Point p) {
		 List<Integer> type = jdbcTemplate
	                .query("SELECT * FROM BUILDING WHERE x = ? AND y = ?",(ResultSet resultSet, int rowNum) -> {
	                    return resultSet.getInt("type");
	                },p.getX(),p.getY());

	     return type.isEmpty() ? -1 : type.get(0);
	}

}
