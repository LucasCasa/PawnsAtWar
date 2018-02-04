package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Army;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ArmyDTO {

  private int id;
  private PointDTO position;
  private boolean available;
  private UserDTO owner;
  private List<TroopDTO> troops;

  public ArmyDTO(Army army) {
    this.id = army.getIdArmy();
    this.position = new PointDTO(army.getPosition());
    this.available = army.getAvailable();
    this.owner = new UserDTO(army.getUser());
    this.troops = new ArrayList<>();

    army.getTroops().forEach(troop -> troops.add(new TroopDTO(troop)));
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public PointDTO getPosition() {
    return position;
  }

  public void setPosition(PointDTO position) {
    this.position = position;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public UserDTO getOwner() {
    return owner;
  }

  public void setOwner(UserDTO owner) {
    this.owner = owner;
  }

  public List<TroopDTO> getTroops() {
    return troops;
  }

  public void setTroops(List<TroopDTO> troops) {
    this.troops = troops;
  }
}
