package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Sector;

/**
 * Created by lucas on 07/02/18.
 */
public class BuildingDTO {

  private int id;
  private TileDTO tile;
  private boolean canBuildCastle;
  private int castleCost;
  private boolean alreadyBuilding;

  public BuildingDTO(Sector s,int id, boolean canBuildCastle, int castleCost, boolean alreadyBuilding){
    this.tile = new TileDTO(s);
    this.id = id;
    this.canBuildCastle = canBuildCastle;
    this.castleCost = castleCost;
    this.alreadyBuilding = alreadyBuilding;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public TileDTO getTile() {
    return tile;
  }

  public void setTile(TileDTO tile) {
    this.tile = tile;
  }

  public boolean isCanBuildCastle() {
    return canBuildCastle;
  }

  public void setCanBuildCastle(boolean canBuildCastle) {
    this.canBuildCastle = canBuildCastle;
  }

  public int getCastleCost() {
    return castleCost;
  }

  public void setCastleCost(int castleCost) {
    this.castleCost = castleCost;
  }

  public boolean isAlreadyBuilding() {
    return alreadyBuilding;
  }

  public void setAlreadyBuilding(boolean alreadyBuilding) {
    this.alreadyBuilding = alreadyBuilding;
  }
}
