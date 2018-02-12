package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TileDTO {

  private int x;
  private int y;
  private int type;
  private int level;
  private UserDTO owner;

  public TileDTO(Sector sector) {
    this.x = sector.getPosition().getX();
    this.y = sector.getPosition().getY();
    this.type = sector.getType();
    this.level = sector.getLevel();
    this.owner = sector.getUser() == null ? null : new UserDTO(sector.getUser());
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

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getLevel() {
    return this.level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public UserDTO getOwner() {
    return owner;
  }

  public void setOwner(UserDTO owner) {
    this.owner = owner;
  }
}
