package ar.edu.itba.interfaces;

import ar.edu.itba.model.*;

/**
 * Created by lucas on 24/11/16.
 */
public interface ScheduleService {

    void buildTask(User u, Point p, int type);

    void levelUpTask(Sector s);

    void TrainTask(User u, Point p,Integer amount, Integer type);

    void resumeTask(Alert a);

    void attackTask(User user, Point point, int armyId);

  void returnTask(User u, Army a, Point from);

  void moveTask(User u, int armyId, Point p);

    void mergeTask(User u, int armyId, int armyId2, Point p);

    void splitTask(User u, int armyId, Point p);
}
