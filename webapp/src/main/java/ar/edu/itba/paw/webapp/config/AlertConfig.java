package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;

/**
 * Created by TeamMuffin on 27/11/16.
 */
@Component
public class AlertConfig {

    @Autowired
    SectorService ss;

    @Autowired
    ArmyService ars;

    @Autowired
    EmpireService es;

    @Autowired
    AlertService as;

    @Autowired
    ScheduleService sh;


    @PostConstruct
    public void setAlerts() {
        List<Alert> alerts = as.getAllAlerts();
        alerts.forEach(this::checkAlert);
    }

    private void checkAlert(Alert a) {
        if(a.getDate().getTime() <= Calendar.getInstance().getTimeInMillis()){
            performAlert(a);
        }else {
            sh.resumeTask(a);
        }

    }

    private void performAlert(Alert a) {
        String s = a.getType();
        if(s.equals(AlertType.BUILD.toString())){
            ss.addBuilding(a.getP(),a.getUser(),a.getParam1());
        }else if(s.equals(AlertType.UPGRADE.toString())){
            ss.levelUp(a.getP());
        }else if(s.equals(AlertType.ATTACK.toString())){
            ars.setAvailable(a.getParam1(), true);
        }else if(s.equals(AlertType.RECRUIT.toString())){
            ars.trainTroops(a.getUser(),a.getP(),a.getParam2(),a.getParam1());
        } else if(s.equals(AlertType.RETURN.toString())){
          ars.setAvailable(a.getParam1(), true);
        }
        as.removeAlert(a);
    }

}
