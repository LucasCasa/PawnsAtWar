package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Troop;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TroopDTO {

  private long id;
  private int type;
  private int quantity;

  public TroopDTO() {
  }

  public TroopDTO(Troop troop) {
    this.id = troop.getId();
    this.type = troop.getType();
    this.quantity = troop.getQuantity();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
