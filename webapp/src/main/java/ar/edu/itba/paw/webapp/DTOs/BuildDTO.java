package ar.edu.itba.paw.webapp.DTOs;

public class BuildDTO {

  private int type;
  private PointDTO position;

  public BuildDTO() {}

  public PointDTO getPosition() {
    return position;
  }

  public void setPosition(PointDTO position) {
    this.position = position;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }
}
