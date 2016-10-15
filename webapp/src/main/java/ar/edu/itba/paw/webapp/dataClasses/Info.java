package ar.edu.itba.paw.webapp.dataClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/18/16.
 */
public class Info {
    public static final int EMPTY = 0;
    public static final int CASTLE = 1;
    public static final int ARCHERY = 2;
    public static final int BARRACKS = 3;
    public static final int GOLD = 4;
    public static final int TERR_GOLD = 5;
    public static final int MILL = 6;
    public static final int BLACKSMITH = 7;
    public static final int STABLE = 8;

    public static final int WARRIOR = 0;
    public static final int ARCHER = 1;
    public static final int HORSEMAN = 2;
    public static final int COST_WARRIOR= 30;
    public static final int COST_ARCHER= 50;
    public static final int COST_HORSEMAN= 70;

    public static final int RES_FOOD = 0;
    public static final int RES_GOLD = 1;

    private List<InformationBuilding> infoList;
    private static Info bim = new Info();

    public static Info getInstance(){
        return bim;
    }

    private Info(){
        infoList = new ArrayList<>();
        infoList.add(new InformationBuilding(0,"terrain", "Este es un territorio en el cual podes construir edificios"));
        infoList.add(new InformationBuilding(1,"castle", "Este tipo de edificio es un castillo. Debes cuidarlo muy bien o tus oponentes te lo arrebataran"));
        infoList.add(new InformationBuilding(2,"archery", "Este tipo de edificio es una arqueria. Aca trabajan los arqueros, responsables de defender tu territorio"));
        infoList.add(new InformationBuilding(3,"barracks", "Este tipo de edificio te ayuda a defenderte de los ataques enemigos"));
        infoList.add(new InformationBuilding(4,"gold", "Este tipo de edificio te ayudara a generar oro para mejorar tus tropas y construcciones."));
        infoList.add(new InformationBuilding(5,"terrgold", "Terreno propicio para construir una mina de oro."));
        infoList.add(new InformationBuilding(6,"mill", "Este tipo de edificio es un molino y se utiliza para obtener comida."));
        infoList.add(new InformationBuilding(7,"blacksmith", "Este tipo de edificio para mejorar a las tropas"));
        infoList.add(new InformationBuilding(8,"stable", "En este edificio se puede reclutar a los caballeros,la mejor tropa del juego"));
    }

    public InformationBuilding getBuildingInformation(int id){
        return infoList.get(id);

    }
    public ArrayList<InformationBuilding> getConstructable(int type){
        ArrayList<InformationBuilding> res = new ArrayList<>();
        if(type == EMPTY){
            for(InformationBuilding i : infoList) {
                if(i.getId() != EMPTY && i.getId() != CASTLE && i.getId() != GOLD && i.getId() != TERR_GOLD)
                    res.add(i);
            }
        }else{
            res.add(infoList.get(type));
        }
        return res;
    }
}
