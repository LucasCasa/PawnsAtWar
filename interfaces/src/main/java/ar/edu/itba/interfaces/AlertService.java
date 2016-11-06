package ar.edu.itba.interfaces;

import ar.edu.itba.model.Alert;
import ar.edu.itba.model.User;

public interface AlertService {
	public Alert findById(int id);
	public Alert createAlert(User user, String message);
}
