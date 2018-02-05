package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Alert;

public class AlertDTO {

  private String type;
  private long timestamp;
  private PointDTO position;
  private int param1;
  private int param2;

  public AlertDTO(Alert alert) {
    this.type = alert.getType();
    this.timestamp = alert.getDate().toInstant().toEpochMilli();
    this.position = new PointDTO(alert.getP());
    this.param1 = alert.getParam1();
    this.param2 = alert.getParam2();
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public PointDTO getPosition() {
    return position;
  }

  public void setPosition(PointDTO position) {
    this.position = position;
  }

  public int getParam1() {
    return param1;
  }

  public void setParam1(int param1) {
    this.param1 = param1;
  }

  public int getParam2() {
    return param2;
  }

  public void setParam2(int param2) {
    this.param2 = param2;
  }
}
