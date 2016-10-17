package ar.edu.itba.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.BuildingService;
import ar.edu.itba.interfaces.TerrainDao;
import ar.edu.itba.model.Building;
import ar.edu.itba.model.Point;

@Service
public class BuildingServiceImpl implements BuildingService{
	
	List<Point> availableSpots;
	private static final int RANGE = 6;
	private static final int MAXVALUE = 99;
	
	@Autowired
	BuildingDao bd;
	
	@Autowired
	TerrainDao td;
	
	public void LoadSpots(){
		availableSpots = new ArrayList<Point>();
		boolean [][] aux = new boolean[MAXVALUE+1][MAXVALUE+1];
		List<Point> castles = bd.getAllCastles();
		for(Point p: castles){
			int minX = p.getX()-RANGE < 0 ? 0 : p.getX()-RANGE;
			int minY = p.getY()-RANGE < 0 ? 0 : p.getY()-RANGE;
			int maxX = p.getX()+RANGE > MAXVALUE ? MAXVALUE : p.getX() + RANGE;
			int maxY = p.getY()+RANGE > MAXVALUE ? MAXVALUE : p.getY() + RANGE;
			for(int i = minX; i <=maxX ; i++ ){
				for (int j = minY ; j<=maxY; j++ ){
					aux[i][j] = true;
				}
			}
		}
		for(int i = RANGE ; i<=MAXVALUE-RANGE/2 ;i++){
			for (int j = RANGE ;j<=MAXVALUE-RANGE/2;j++){
				if(!aux[i][j]){
					availableSpots.add(new Point(i,j));
				}
			}
		}
	}

	@Override
	public Integer getLevel(Point p) {
		return bd.getLevel(p);
	}

	@Override
	public void setLevel(Point p, int level) {
		bd.setLevel(p, level);
	}

	@Override
	public Integer getIdPlayer(Point p) {
		return bd.getIdPlayer(p);
	}

	@Override
	public void setIdPlayer(Point p,int idPlayer) {
		bd.setIdPlayer(p,idPlayer);
	}

	@Override
	public boolean belongsTo(Point p, int idPlayer) {
		return bd.belongsTo(p, idPlayer);
	}

	@Override
	public Point getCastle(int idPlayer){ 
		return bd.getCastle(idPlayer);
	}

	@Override
	public void levelUp(Point p) {
		bd.setLevel(p, bd.getLevel(p) + 1);
		
	}

	@Override
	public Point addCastle(int idPlayer) {
		if(availableSpots == null)
			LoadSpots();
		Random random = new Random();
		int n = random.nextInt(availableSpots.size());
		Point p = availableSpots.get(n);
		availableSpots.remove(n);
		bd.addBuilding(p, idPlayer, 1);
		return p;
	}

	@Override
	public List<Building> getAllBuildings(int idPlayer) {
		return bd.getBuildings(idPlayer);
	}

	@Override
	public void addBuilding(Point p, int idPlayer, int type) {
		bd.addBuilding(p, idPlayer, type);
	}

	@Override
	public int getType(Point p) {
		return bd.getType(p);
	}

	@Override
	public int getPrice(Point point,int userId) {
		System.out.println("ENTRO A GET PRICE");
		System.out.println("LEVEL ESSSS : " + getLevel(getCastle(userId)));
		return 1000-10*getLevel(getCastle(userId));
	}
	
	
}
