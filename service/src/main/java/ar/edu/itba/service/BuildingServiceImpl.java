package ar.edu.itba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.BuildingDao;
import ar.edu.itba.interfaces.BuildingService;
import ar.edu.itba.model.Buildings;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

@Service
public class BuildingServiceImpl implements BuildingService{
	
	@Autowired
	BuildingDao bd;

	@Override
	public Integer getLevel(Point p) {
		return bd.getLevel(p);
	}

	@Override
	public Integer setLevel(Point p, int level) {
		return bd.setLevel(p, level);
	}

	@Override
	public Integer getIdPlayer(Point p) {
		return bd.getIdPlayer(p);
	}

	@Override
	public Buildings setIdPlayer(Point p) {
		return bd.setIdPlayer(p);
	}

	@Override
	public Buildings addBuilding(Point p, int level, int idPlayer, int type) {
		return bd.addBuilding(p, level, idPlayer, type);
	}

	@Override
	public Buildings addBuilding(Point p, int idPlayer, int type) {
		return bd.addBuilding(p, idPlayer, type);
	}

	@Override
	public boolean belongsTo(Point p, User u) {
		return bd.belongsTo(p, u);
	}

}
