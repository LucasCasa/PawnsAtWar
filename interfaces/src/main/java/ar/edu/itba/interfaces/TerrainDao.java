package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Terrain;

public interface TerrainDao {
	/**
	 * @param position, the position (x,y) from which you want to know the power
	 * @return integer with the power associated with a certain point. Null if the position does not exist or coordinates are negative
	 */
	public Integer getPower(Point position);
	
	/**
	 * @param position, the position (x,y) from which you want to know the type
	 * @return integer with the type associated with a certain point. Null if the position does not exist or coordinates are negative
	 */
	public Integer getType(Point position);
	
	/**
	 * @param p, the position (x,y)
	 * @param range, the range of positions that you have to give.
	 * @return a list of terrains that are at a distance of range of the position p
	 */
	public List<Terrain> getTerrain(Point p, int range);
	/**
	 * Returns the terrain in a position
	 * @param p
	 * @return the terrain that is in position p
	 */
	public Terrain getTerrain(Point p);
	
	public Terrain setPower(Point p, int power);
	
	public Terrain setType(Point p, int t);
	
	public Terrain addTerrain(Point p, int power, int t);
	
	public Terrain addTerrain(Point p, int t);
	
	public Terrain addTerrain(Point p);
	
	
}