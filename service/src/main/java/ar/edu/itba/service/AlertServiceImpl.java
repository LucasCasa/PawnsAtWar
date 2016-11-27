package ar.edu.itba.service;

import ar.edu.itba.interfaces.ScheduleService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.AlertType;
import ar.edu.itba.model.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class AlertServiceImpl implements AlertService {
	
	@Autowired
	AlertDao ad;

	@Autowired
	ScheduleService sh;

	@Autowired
	SectorService ss;

	@Override
	public Alert findById(int id) {
		if(id >= 0){
			return ad.findById(id);
		}
		return null;
	}

	@Override
	public Alert createAlert(User user, String message, Date d, String type, Point p, Integer param1, Integer param2) {
		if(user == null || message == null){
			return null;
		}
		return ad.createAlert(user, message,d,type,p,param1,param2);
	}

	public void removeAlert(Alert a){
		ad.removeAlert(a);
	}

	@Override
	public List<Alert> getByUser(User u) {
		return ad.getByUser(u);
	}

	@Override
	public List<Alert> getAllAlerts() {
		return ad.getAllAlerts();
	}

	@Override
	public Alert getAlertByPoint(Point p){
		return ad.getAlertByPoint(p);
	}
}
