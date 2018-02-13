package ar.edu.itba.paw.webapp.DTOs;

public class ArmyTrainDTO {
  private int amount;
  private PointDTO point;

  public ArmyTrainDTO() {}

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
