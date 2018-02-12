package ar.edu.itba.paw.webapp.DTOs;

import java.util.List;

public class ArmySplitDTO {

  private int armyId;
  private PointDTO position;
  private List<UnitDTO> units;

  public ArmySplitDTO() {}

  public int getArmyId() {
    return armyId;
  }

  public void setArmyId(int armyId) {
    this.armyId = armyId;
  }

  public PointDTO getPosition() {
    return position;
  }

  public void setPosition(PointDTO position) {
    this.position = position;
  }

  public List<UnitDTO> getUnits() {
    return units;
  }

  public void setUnits(List<UnitDTO> units) {
    this.units = units;
  }
}
