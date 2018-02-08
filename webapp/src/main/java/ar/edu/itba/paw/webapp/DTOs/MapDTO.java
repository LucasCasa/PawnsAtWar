package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Sector;
import ar.edu.itba.paw.webapp.data.Validator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MapDTO {

  private int id;
	private int x;
	private int y;
	private String nextx;
	private String nexty;
	private String prevx;
	private String prevy;
	private List<List<TileDTO>> tiles;


	public MapDTO(List<List<Sector>> sectors, int x, int y, int id) {
    this.id = id;
		this.x = x;
		this.y = y;
		this.nextx = Validator.format(x+1, y);
		this.nexty = Validator.format(x, y+1);
		this.prevx = Validator.format(x-1, y);
		this.prevy = Validator.format(x, y-1);
		this.tiles = new ArrayList<>();
		for(List<Sector> row: sectors) {
			List<TileDTO> tileRow = new ArrayList<>();
			tiles.add(tileRow);
			for(Sector s: row) {
				tileRow.add(new TileDTO(s));
			}
		}
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

	public String getNextx() {
		return nextx;
	}

	public void setNextx(String nextx) {
		this.nextx = nextx;
	}

	public String getNexty() {
		return nexty;
	}

	public void setNexty(String nexty) {
		this.nexty = nexty;
	}

	public String getPrevx() {
		return prevx;
	}

	public void setPrevx(String prevx) {
		this.prevx = prevx;
	}

	public String getPrevy() {
		return prevy;
	}

	public void setPrevy(String prevy) {
		this.prevy = prevy;
	}

	public List<List<TileDTO>> getTiles() {
		return tiles;
	}

	public void setTiles(List<List<TileDTO>> tiles) {
		this.tiles = tiles;
	}

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
