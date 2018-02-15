package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Alert;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Calendar;
import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertDTO {

  private String type;
  private long timestamp;
  private long serverTimestamp;
  private PointDTO position;
  private Integer param1;
  private Integer param2;

  public AlertDTO(Alert alert) {
    this.type = alert.getType();
    this.serverTimestamp = new Date().getTime();
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

  public long getServerTimestamp() {
    return serverTimestamp;
  }

  public void setServerTimestamp(long serverTimestamp) {
    this.serverTimestamp = serverTimestamp;
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

  public Integer getParam1() {
    return param1;
  }

  public void setParam1(Integer param1) {
    this.param1 = param1;
  }

  public Integer getParam2() {
    return param2;
  }

  public void setParam2(Integer param2) {
    this.param2 = param2;
  }
}
