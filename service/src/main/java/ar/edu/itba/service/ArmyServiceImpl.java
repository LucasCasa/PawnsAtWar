package ar.edu.itba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.interfaces.ArmyDao;
import ar.edu.itba.interfaces.ArmyService;
import ar.edu.itba.model.Army;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

@Service
public class ArmyServiceImpl implements ArmyService {
	
	@Autowired
	private ArmyDao ad;

	@Override
	public List<Army> getArmy(User u) {
		return ad.getArmy(u.getId());
	}

	@Override
	public Army createArmy(Point p, User u) {
		return ad.addArmy(p, u.getId());
	}

}
