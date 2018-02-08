package ar.edu.itba.paw.webapp.DTOs;

public class ArmyMoveDTO {

  private int armyId;
  private PointDTO point;

  public ArmyMoveDTO() {}

  public int getArmyId() {
    return armyId;
  }

  public void setArmyId(int armyId) {
    this.armyId = armyId;
  }

  public PointDTO getPoint() {
    return point;
  }

  public void setPoint(PointDTO point) {
    this.point = point;
  }
}
