package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.Alert;
import ar.edu.itba.model.AlertType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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


    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void setAlerts() {
        List<Alert> alerts = as.getAllAlerts();
        for(Alert a: alerts){
            checkAlert(a);
        }
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

        }else if(s.equals(AlertType.RECRUIT.toString())){

        }
        as.removeAlert(a);
    }

}