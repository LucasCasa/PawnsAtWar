package ar.edu.itba.service;

import ar.edu.itba.interfaces.*;
import ar.edu.itba.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by lucas on 24/11/16.
 */
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private TaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());
    @Autowired
    private SectorService ss;
    @Autowired
    private EmpireService es;
    @Autowired
    private ArmyService ars;
    @Autowired
    private AlertService as;
    @Autowired
    private MessageSource messageSource;

    @Override
    public void buildTask(final User u, final Point p, final int t) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE,1);
        Date d = c.getTime();
        Alert alert = as.createAlert(u,getBuildAlertMessage(u,p,t,d),d,AlertType.BUILD.toString(),p,t,null);

        setBuildTask(u,p,t,alert,d);
    }

    @Override
    public void levelUpTask(Sector s) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE,s.getLevel());
        Date d = c.getTime();
        Alert alert = as.createAlert(s.getUser(),getLevelUpAlertMessage(s,d),d,AlertType.UPGRADE.toString(),s.getPosition(),null,null);
        setLevelUpTask(s,alert,d);
    }

    @Override
    public void attackTask() {
/*
        scheduler.schedule(exampleRunnable,
               new Date(1432152000000L));*/
    }

    @Override
    public void TrainTask(User u,Point p, Integer amount, Integer type) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.SECOND,amount*30);
        Date d = c.getTime();
        Alert alert = as.createAlert(u,getTrainAlertMessage(p,amount,type),d,AlertType.RECRUIT.toString(),p,amount,type);
        setTrainTask(u,p,amount,type,alert,d);
    }

    private void setTrainTask(User u, Point p, Integer am, Integer t,final Alert alert, final Date d) {
        Runnable buildRunnable = new Runnable() {
            User user = u;
            Point pos = p;
            int amount = am;
            int type = t;
            Alert a = alert;
            @Override
            public void run() {
                ars.trainTroops(user,pos,amount,type);
                as.removeAlert(a);
            }
        };
        scheduler.schedule(buildRunnable,d);
    }

    @Override
    public void resumeTask(Alert a){
        String s = a.getType();
        if(s.equals(AlertType.BUILD.toString())){
            setBuildTask(a.getUser(),a.getP(),a.getParam1(),a,a.getDate());
        }else if(s.equals(AlertType.UPGRADE.toString())){
            setLevelUpTask(ss.getSector(a.getP()),a,a.getDate());
        }else if(s.equals(AlertType.ATTACK.toString())){

        }else if(s.equals(AlertType.RECRUIT.toString())){
            setTrainTask(a.getUser(),a.getP(),a.getParam1(),a.getParam2(),a,a.getDate());
        }
    }

    private void setBuildTask(final User u, final Point p,final int t,final Alert alert,final Date d){
        Runnable buildRunnable = new Runnable() {
            User user = u;
            Point pos = p;
            int type = t;
            Alert a = alert;
            @Override
            public void run() {
                ss.addBuilding(pos,user,type);
                as.removeAlert(a);
            }
        };
        scheduler.schedule(buildRunnable,d);
    }

    private void setLevelUpTask(Sector s,Alert alert,Date d){
        Runnable upgradeRunnable = new Runnable() {
            Sector building = s;
            Alert a = alert;
            @Override
            public void run() {
                ss.levelUp(building.getPosition());
                as.removeAlert(a);
            }
        };

        scheduler.schedule(upgradeRunnable,d);
    }

    private String getBuildAlertMessage(User u, Point p, int t, Date d) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] params = new Object[3];
        params[0] = messageSource.getMessage(SectorType.get(t).toString(),null,locale);
        params[1] = p.getX();
        params[2] = p.getY();
        return messageSource.getMessage("alert.building",params,locale);
    }


    private String getLevelUpAlertMessage(Sector s,Date c) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] params = new Object[3];
        params[0] = messageSource.getMessage(SectorType.get(s.getType()).toString(),null,locale);
        params[1] = s.getPosition().getX();
        params[2] = s.getPosition().getY();
        return messageSource.getMessage("alert.upgrading",params,locale);
    }

    private String getTrainAlertMessage(Point p,Integer amount, Integer type) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] params = new Object[4];
        params[0] = amount;
        params[1] = messageSource.getMessage(TroopType.get(type).toString(),null,locale);
        params[2] = p.getX();
        params[3] = p.getY();
        return messageSource.getMessage("alert.training",params,locale);
    }

}
