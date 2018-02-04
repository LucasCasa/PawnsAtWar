package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Point;

public class PointDTO {

  private int x;
  private int y;

  public PointDTO(){}

  public PointDTO(Point point) {
    this.x = point.getX();
    this.y = point.getY();
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
}
