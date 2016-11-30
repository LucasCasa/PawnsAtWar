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

import java.util.*;
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
    private TroopService ts;
    @Autowired
    private MessageService ms;
    @Autowired
    private MessageSource messageSource;

    @Override
    public void buildTask(final User u, final Point p, final int t) {
        Calendar c = Calendar.getInstance();
        int time = 1;
        if(t == 1){
            Point ca = ss.getCastle(u);
            time+= (int)Math.sqrt(Math.pow(ca.getX() - p.getX(),2) + Math.pow(ca.getY() - p.getY(),2));
        }
        c.add(Calendar.MINUTE,time);
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
    public void attackTask(User user, Point point, int armyId) {
        Calendar c = Calendar.getInstance();
        Point o = ars.getArmyById(armyId).getPosition();
        int time = (int)(Math.log10(Math.pow(o.getX() - point.getX(),2) + Math.pow(o.getY() - point.getY(),2))*60);
        c.add(Calendar.SECOND,time);
        Date d = c.getTime();
        Alert alert = as.createAlert(user,getAttackAlertMessage(point),d,AlertType.ATTACK.toString(),point,armyId,null);
        setAttackTask(user,point,armyId,alert,d);
    }

    @Override
    public void resumeTask(Alert a){
        String s = a.getType();
        if(s.equals(AlertType.BUILD.toString())){
            setBuildTask(a.getUser(),a.getP(),a.getParam1(),a,a.getDate());
        }else if(s.equals(AlertType.UPGRADE.toString())){
            setLevelUpTask(ss.getSector(a.getP()),a,a.getDate());
        }else if(s.equals(AlertType.ATTACK.toString())){
            setAttackTask(a.getUser(),a.getP(),a.getParam1(),a,a.getDate());
        }else if(s.equals(AlertType.RECRUIT.toString())){
            setTrainTask(a.getUser(),a.getP(),a.getParam1(),a.getParam2(),a,a.getDate());
        }
    }



    private void setAttackTask(User u, Point p, int id,Alert alert, Date d){
        Runnable buildRunnable = new Runnable() {
            User user = u;
            Point pos = p;
            int armyId = id;
            Alert a = alert;
            @Override
            public void run() {
                Army d = ars.getArmyAtPosition(u,p);
                Army a = ars.getArmyById(id);
                Map<String,Integer> values = new HashMap<>();
                int res = 0;
                values.put("a0b",0);
                values.put("a1b",0);
                values.put("a2b",0);
                values.put("d0b",0);
                values.put("d1b",0); // QUE WASADA POR DIOS
                values.put("d2b",0);
                values.put("a0l",0);
                values.put("a1l",0);
                values.put("a2l",0);
                values.put("d0l",0);
                values.put("d1l",0);
                values.put("d2l",0);
                if(d != null && d.getAvailable()){
                    int defenderP = (int) ts.getValue(d.getIdArmy());
                    int attackerP = (int) ts.getValue(id);
                    int loserP;
                    Army adef;
                    Army awin;
                    String prefixW;
                    String prefixD;
                    if(defenderP > attackerP){
                        //mav.addObject("result",messageSource.getMessage("defenderWin",null,locale));
                        adef = a;
                        awin = d;
                        loserP = attackerP;
                        prefixD ="a";
                        prefixW ="d";
                        res = 1;
                    }else if(attackerP > defenderP){
                        //mav.addObject("result",messageSource.getMessage("attackerWin",null,locale));
                        awin = a;
                        adef = d;
                        loserP = defenderP;
                        prefixD ="d";
                        prefixW ="a";
                        ss.deleteBuilding(p);
                        ars.setAvailable(id,true);
                        res = 2;
                    }else{
                        //mav.addObject("result",messageSource.getMessage("draw",null,locale));
                        for(Troop t : a.getTroops()){
                            values.put("a"+t.getType()+"b",t.getQuantity());
                            values.put("a"+t.getType()+"l",t.getQuantity());
                        }
                        for(Troop t : d.getTroops()){
                            values.put("d"+t.getType()+"b",t.getQuantity());
                            values.put("d"+t.getType()+"l",t.getQuantity());
                        }
                        ars.deleteArmy(id);
                        ars.deleteArmy(d.getIdArmy());
                        as.removeAlert(this.a);
                        sendMail(values,user,ss.getPlayer(pos),0);
                        return;
                    }
                    List<Troop> defeated = adef.getTroops();
                    ars.deleteArmy(adef.getIdArmy());
                    List<Troop> winner = awin.getTroops();
                    for(Troop t : winner){
                        if(t.getQuantity()*(t.getType()+1) > loserP){
                            values.put(prefixW+t.getType()+"b",t.getQuantity());
                            ts.subtractTroop(awin.getIdArmy(),t.getType(),loserP/(t.getType()+1));
                            values.put(prefixW+t.getType()+"l",loserP/(t.getType()+1));
                            break;
                        }else{
                            values.put(prefixW+t.getType()+"b",t.getQuantity());
                            values.put(prefixW+t.getType()+"l",t.getQuantity());
                            ts.deleteTroop(awin.getIdArmy(),t.getType());
                            loserP-= (t.getType()+1)*t.getQuantity();

                        }
                    }
                    for(Troop t: defeated){
                        values.put(prefixD+t.getType()+"b",t.getQuantity());
                        values.put(prefixD+t.getType()+"l",t.getQuantity());
                    }
                    sendMail(values,user,ss.getPlayer(pos),res);
                    as.removeAlert(this.a);
                    return;
                }
                //mav.addObject("result",messageSource.getMessage("noDefenderArmy",null,locale));
                for(Troop t : ts.getTroopById(id)){
                    values.put("a"+t.getType()+"b",t.getQuantity());
                    values.put("a"+t.getType()+"l",0);
                }
                ss.deleteBuilding(pos);
                as.removeAlert(this.a);
                ars.setAvailable(id,true);
                sendMail(values,user,ss.getPlayer(pos),1);
            }
        };
        scheduler.schedule(buildRunnable,d);
    }
    private void setBuildTask(final User u, final Point p,final int t,final Alert alert,final Date d){
        Runnable buildRunnable = new Runnable() {
            User user = u;
            Point pos = p;
            int type = t;
            Alert a = alert;
            @Override
            public void run() {
            	if(type==SectorServiceImpl.CASTLE)
            		ss.updateTerrain(p, u, SectorServiceImpl.initRange);
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
    private String getAttackAlertMessage(Point point) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] params = new Object[2];
        params[0] = point.getX();
        params[1] = point.getY();
        return messageSource.getMessage("alert.attacking",params,locale);
    }

    private void sendMail(Map<String,Integer> res,User a, User d,int result){
        String headerD;
        String headerA;
        String body;
        if(result == 0)
            headerD = "Fuiste atacado por " +a.getName() +": Fue un empate\n";
        else if(result == 1)
            headerD = "Fuiste atacado por "+ a.getName() +": Ganaste\n";
        else
            headerD = "Fuiste atacado por "+ a.getName() +": Perdiste\n";
        Integer[] params = new Integer[18];
        String[] params2 = new String[1];
        params2[0] = a.getName();
        params[0] = res.get("d0b");
        params[1] = res.get("d0l");
        params[2] = res.get("d0b") - res.get("d0l");
        params[3] = res.get("d1b");
        params[4] = res.get("d1l");
        params[5] = res.get("d1b") - res.get("d1l");
        params[6] = res.get("d2b");
        params[7] = res.get("d2l");
        params[8] = res.get("d2b") - res.get("d2l");
        params[9] = res.get("a0b");
        params[10] = res.get("a0l");
        params[11] = res.get("a0b") - res.get("a0l");
        params[12] = res.get("a1b");
        params[13] = res.get("a1l");
        params[14] = res.get("a1b") - res.get("a1l");
        params[15] = res.get("a2b");
        params[16] = res.get("a2l");
        params[17] = res.get("a2b") - res.get("a2l");
        Locale l =LocaleContextHolder.getLocale();
        ms.createMessage(a,d,messageSource.getMessage("attack.message.defender.subject",params2,l),messageSource.getMessage("attack.message.defender",params,l));
        ms.createMessage(a,a,messageSource.getMessage("attack.message.attacker.subject",null,l),messageSource.getMessage("attack.message.attacker",params,l));
        System.out.println("HOLA");
    }
}
