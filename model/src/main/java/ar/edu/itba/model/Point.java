package ar.edu.itba.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Point {
	@Column(name = "x", nullable = false)
	private int x;
	@Column(name = "y", nullable = false)
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
