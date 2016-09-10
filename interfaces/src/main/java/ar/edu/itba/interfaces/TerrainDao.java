package ar.edu.itba.interfaces;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.Type;

public interface TerrainDao {
	public int getPower(Point position);
	public Type getType(Point position);
	
}
