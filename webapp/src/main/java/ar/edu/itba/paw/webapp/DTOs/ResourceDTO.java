package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Resource;

public class ResourceDTO {

  private int type;
  private int amount;
  private double rate;

  public ResourceDTO(Resource resource, double rate) {
    this.type = resource.getType();
    this.amount = resource.getQuantity();
    this.rate = rate;
  }

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

  public double getRate() {
    return rate;
  }

  public void setRate(double rate) {
    this.rate = rate;
  }
}
