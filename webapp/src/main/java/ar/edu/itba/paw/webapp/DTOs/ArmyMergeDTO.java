package ar.edu.itba.paw.webapp.DTOs;

public class ArmyMergeDTO {
  private int fromId;
  private int toId;

  public ArmyMergeDTO() {}

  public int getFromId() {
    return fromId;
  }

  public void setFromId(int fromId) {
    this.fromId = fromId;
  }

  public int getToId() {
    return toId;
  }

  public void setToId(int toId) {
    this.toId = toId;
  }
}
