package ar.edu.itba.interfaces;

import ar.edu.itba.model.Alert;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.User;

import java.util.Date;
import java.util.List;

public interface AlertService {
	Alert findById(int id);
    Alert createAlert(User user, String message, Date d, String type, Point p, Integer param1, Integer param2);

    void removeAlert(Alert a);
	List<Alert> getByUser(User u);
    List<Alert> getAllAlerts();
    List<Alert> getBuildingConstructed(Point p);
    Alert getAlertByPoint(Point p);
}
