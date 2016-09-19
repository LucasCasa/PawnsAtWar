package ar.edu.itba.paw.webapp.dataClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/18/16.
 */
public class BuildingInformationMap {

    private List<InformationBuilding> infoList;
    private static BuildingInformationMap bim = new BuildingInformationMap();

    public static BuildingInformationMap getInstance(){
        return bim;
    }

    private BuildingInformationMap(){
        infoList = new ArrayList<>();
        infoList.add(new InformationBuilding(0,"terrain", "Este es un territorio en el cual podes construir edificios"));
        infoList.add(new InformationBuilding(1,"castle", "Este tipo de edificio es un castillo. Debes cuidarlo muy bien o tus oponentes te lo arrebataran"));
        infoList.add(new InformationBuilding(2,"archery", "Este tipo de edificio es una arqueria. Aca trabajan los arqueros, responsables de defender tu territorio"));
        infoList.add(new InformationBuilding(3,"barracks", "Este tipo de edificio te ayuda a defenderte de los ataques enemigos"));
        infoList.add(new InformationBuilding(4,"gold", "Este tipo de edificio te ayudara a generar oro para mejorar tus tropas y construcciones."));
        infoList.add(new InformationBuilding(5,"terrgold", "Terreno propicio para construir una mina de oro."));
        infoList.add(new InformationBuilding(6,"mill", "Este tipo de edificio es un molino y se utiliza para obtener comida."));
        infoList.add(new InformationBuilding(7,"blacksmith", "Este tipo de edificio para mejorar a las tropas"));
    }

    public InformationBuilding getBuildingInformation(int id){
        return infoList.get(id);

    }
}
