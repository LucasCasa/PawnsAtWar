package ar.edu.itba.paw.webapp.DTOs;

import java.util.List;

public class ArmySplitDTO {

  private int armyId;
  private PointDTO point;
  private List<UnitDTO> troops;

  public ArmySplitDTO() {}

  public int getArmyId() {
    return armyId;
  }

  public void setArmyId(int armyId) {
    this.armyId = armyId;
  }

  public PointDTO getPoint() {
    return point;
  }

  public void setPoint(PointDTO position) {
    this.point = position;
  }

  public List<UnitDTO> getTroops() {
    return troops;
  }

  public void setTroops(List<UnitDTO> units) {
    this.troops = units;
  }
}
