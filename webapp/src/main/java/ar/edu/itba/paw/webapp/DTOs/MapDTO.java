package ar.edu.itba.paw.webapp.DTOs;

import ar.edu.itba.model.Sector;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = { "basePath" })
public class MapDTO {

	private static final String basePath = "/map/{0}/{1}";

	private int x;
	private int y;
	private String nextx;
	private String nexty;
	private String prevx;
	private String prevy;
	private List<List<TileDTO>> tile;

	public MapDTO(List<List<Sector>> sectors, int x, int y) {
		this.x = x;
		this.y = y;
		this.nextx = format(x+1, y);
		this.nexty = format(x, y+1);
		this.prevx = format(x-1, y);
		this.prevy = format(x, y-1);
		this.tile = new ArrayList<>();
		for(List<Sector> row: sectors) {
			List<TileDTO> tileRow = new ArrayList<>();
			for(Sector s: row) {
				tileRow.add(new TileDTO(s));
			}
		}
	}

	public static String getBasePath() {
		return basePath;
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

	public List<List<TileDTO>> getTile() {
		return tile;
	}

	public void setTile(List<List<TileDTO>> tile) {
		this.tile = tile;
	}

	private static String format(int x, int y) {
		//TODO void link for invalid pos
		return MessageFormat.format(basePath, x, y);
	}
}
