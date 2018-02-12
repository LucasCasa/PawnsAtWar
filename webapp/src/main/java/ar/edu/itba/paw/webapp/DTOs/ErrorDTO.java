package ar.edu.itba.paw.webapp.DTOs;

public class ErrorDTO {
  private String errorId;

  public ErrorDTO(String errorId) {
    this.errorId = errorId;
  }

  public String getErrorId() {
    return errorId;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }
}
