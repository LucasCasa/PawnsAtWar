package ar.edu.itba.interfaces;

import ar.edu.itba.model.Alert;
import ar.edu.itba.model.User;

import java.util.Date;
import java.util.List;

public interface AlertService {
	public Alert findById(int id);
	public Alert createAlert(User user, String message,Date d);
	public void removeAlert(Alert a);
	public List<Alert> getByUser(User u);
}
