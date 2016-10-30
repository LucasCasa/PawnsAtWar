package ar.edu.itba.model;

import java.util.Objects;

public class Point {
	private int x;
	private int y;
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	/* package */ Point(){
		
	}
	
	public double getDistance(Point p){
		return Math.sqrt(Math.pow(x-p.getX(), 2) +Math.pow(y-p.getY(), 2) );
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	public String toString(){
		return "(" + this.x + "," + this.y + ")";
	}

	public boolean equals(Object o){
		if(o == null)
			return false;
		if(!(o instanceof Point))
			return false;
		Point p = (Point) o;
		return x == p.x && y == p.y;
	}

	@Override
	public int hashCode() {
		return x + y*1000;
	}
}
