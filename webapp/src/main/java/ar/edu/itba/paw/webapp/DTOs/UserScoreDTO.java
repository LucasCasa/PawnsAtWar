package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserScoreDTO {

	private int id;
	private String username;
  private PointDTO point;
	private Long score;

	public UserScoreDTO(User user, Point p, Long score){
		this.id = user.getId();
		this.username = user.getName();
    this.point = (p == null)?new PointDTO(new Point(0,0)):new PointDTO(p);
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}

  public PointDTO getPoint() {
    return point;
  }

  public void setPoint(PointDTO point) {
    this.point = point;
  }
}
