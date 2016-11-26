package ar.edu.itba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.User;

import java.util.Date;
import java.util.List;


@Service
@Transactional
public class AlertServiceImpl implements AlertService {
	
	@Autowired
	AlertDao ad;

	@Override
	public Alert findById(int id) {
		if(id >= 0){
			return ad.findById(id);
		}
		return null;
	}

	@Override
	public Alert createAlert(User user, String message,Date d) {
		if(user == null || message == null){
			return null;
		}
		return ad.createAlert(user, message,d);
	}

	public void removeAlert(Alert a){
		ad.removeAlert(a);
	}

	@Override
	public List<Alert> getByUser(User u) {
		return ad.getByUser(u);
	}

}
