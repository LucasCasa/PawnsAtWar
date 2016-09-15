package ar.edu.itba.interfaces;

import java.util.List;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Terrain;

/**
 * Created by root on 9/12/16.
 */
public interface TerrainService {
	public Integer getPower(Point position);
	public Integer getType(Point position);
	public List<List<Terrain>> getTerrain(Point p, int range);
	public Terrain getTerrain(Point p);
	public Terrain setPower(Point p, int power);
	public Terrain setType(Point p, int t);
	public Terrain addTerrain(Point p, int power, int t);
	public Terrain addTerrain(Point p, int t);
	public Terrain addTerrain(Point p);
}
