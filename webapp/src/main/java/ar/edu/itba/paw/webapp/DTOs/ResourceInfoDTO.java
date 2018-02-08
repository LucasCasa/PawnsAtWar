package ar.edu.itba.paw.webapp.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceInfoDTO {

  private int limit;
  private List<ResourceDTO> resources;

  public ResourceInfoDTO(int limit, List<ResourceDTO> resources) {
    this.limit = limit;
    this.resources = resources;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public List<ResourceDTO> getResources() {
    return resources;
  }

  public void setResources(List<ResourceDTO> resources) {
    this.resources = resources;
  }
}
