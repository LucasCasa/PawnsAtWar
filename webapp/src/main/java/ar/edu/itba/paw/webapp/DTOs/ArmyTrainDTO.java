package ar.edu.itba.paw.webapp.DTOs;

public class ArmyTrainDTO {

  private int type;
  private int amount;
  private PointDTO point;

  public ArmyTrainDTO() {}

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public PointDTO getPoint() {
    return point;
  }

  public void setPoint(PointDTO point) {
    this.point = point;
  }
}
