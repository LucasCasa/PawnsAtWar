package ar.edu.itba.interfaces;

import ar.edu.itba.model.Alert;
import ar.edu.itba.model.Point;
import ar.edu.itba.model.Sector;
import ar.edu.itba.model.User;

/**
 * Created by lucas on 24/11/16.
 */
public interface ScheduleService {

    public void buildTask(User u, Point p, int type);

    public void levelUpTask(Sector s);

    public void attackTask();

    void TrainTask(User u, Point p,Integer amount, Integer type);

    void resumeTask(Alert a);
}
