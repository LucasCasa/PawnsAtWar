package ar.edu.itba.interfaces;


import ar.edu.itba.model.Point;
import ar.edu.itba.model.Terrain;

public interface TerrainService {
	public Integer getPower(Point position);
	public Integer getType(Point position);
	public void setPower(Point p, int power);
	public void setType(Point p, int t);
	public Terrain addTerrain(Point p, int power, int t);
	public Terrain addTerrain(Point p, int t);
	public Terrain addTerrain(Point p);
}
