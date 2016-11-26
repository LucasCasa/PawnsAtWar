package ar.edu.itba.service;

import ar.edu.itba.interfaces.AlertService;
import ar.edu.itba.interfaces.EmpireService;
import ar.edu.itba.interfaces.ScheduleService;
import ar.edu.itba.interfaces.SectorService;
import ar.edu.itba.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by lucas on 24/11/16.
 */
@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {
    private TaskScheduler scheduler;
    @Autowired
    private SectorService ss;
    @Autowired
    private EmpireService es;
    @Autowired
    private AlertService as;
    @Autowired
    private MessageSource messageSource;

    @Override
    public void buildTask(final User u, final Point p, final int t) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE,2);
        Date d = c.getTime();
        Alert alert = as.createAlert(u,getBuildAlertMessage(u,p,t,d),d);

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

    private String getBuildAlertMessage(User u, Point p, int t, Date d) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] params = new Object[3];
        params[0] = messageSource.getMessage(SectorType.get(t).toString(),null,locale);
        params[1] = p.getX();
        params[2] = p.getY();
        return messageSource.getMessage("alert.building",params,locale);
    }

    @Override
    public void levelUpTask(Sector s) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE,s.getLevel());
        Date d = c.getTime();
        Alert alert = as.createAlert(s.getUser(),getLevelUpAlertMessage(s,d),d);
        Runnable upgradeRunnable = new Runnable() {
            Sector building = s;
            Alert a = alert;
            @Override
            public void run() {
                ss.levelUp(building.getPosition());
                as.removeAlert(a);
                System.out.println("Subi de nivel");
            }
        };

        scheduler.schedule(upgradeRunnable,d);
    }

    private String getLevelUpAlertMessage(Sector s,Date c) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] params = new Object[3];
        params[0] = messageSource.getMessage(SectorType.get(s.getType()).toString(),null,locale);
        params[1] = s.getPosition().getX();
        params[2] = s.getPosition().getY();
        return messageSource.getMessage("alert.upgrading",params,locale);
    }

    @Override
    public void attackTask() {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
/*
        scheduler.schedule(exampleRunnable,
               new Date(1432152000000L));*/
    }

    @Override
    public void TrainTask() {

    }
}
