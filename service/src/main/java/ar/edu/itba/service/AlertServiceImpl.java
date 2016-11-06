package ar.edu.itba.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.interfaces.AlertDao;
import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.User;


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
	public Alert createAlert(User user, String message) {
		if(user == null || message == null){
			return null;
		}
		return ad.createAlert(user, message);
	}

}
