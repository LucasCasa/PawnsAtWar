package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TileDTO {

	private int x;
	private int y;
	private int type;
	private int owner;

	public TileDTO(Sector sector) {
		this.x = sector.getPosition().getX();
		this.y = sector.getPosition().getY();
		this.type = sector.getType();
		this.owner = sector.getUser() == null ? -1 : sector.getUser().getId();
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

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}
}
