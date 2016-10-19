package ar.edu.itba.paw.webapp.dataClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by root on 9/18/16.
 */
public class Info {

	public static final int MAP_SIZE = 99;
	public static final int VIEW_RANGE = 4;
	
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
    private List<String> engDesc;
    private static Info bim = new Info();
    public static Info getInstance(){
        return bim;
    }

    private Info(){
        infoList = new ArrayList<>();
        infoList.add(new InformationBuilding(0,"terrain", "Este es un territorio en el cual puedes construir edificios."));
        infoList.add(new InformationBuilding(1,"castle", "Este edificio es un castillo. Aumentarlo de nivel disminuye el costo de construir otros edificios. Si un jugador se queda sin castillos pierde."));
        infoList.add(new InformationBuilding(2,"archery", "Este edificio es una arquería. Aquí se pueden entrenar arqueros."));
        infoList.add(new InformationBuilding(3,"barracks", "Este edificio es un cuartel. Aquí se pueden entrenar guerreros."));
        infoList.add(new InformationBuilding(4,"gold", "Este edificio genera oro."));
        infoList.add(new InformationBuilding(5,"terrgold", "Terreno propicio para construir una mina de oro."));
        infoList.add(new InformationBuilding(6,"mill", "Este edificio genera comida."));
        infoList.add(new InformationBuilding(7,"blacksmith", "Este tipo de edificio te permite mejorar a las tropas."));
        infoList.add(new InformationBuilding(8,"stable", "Este edificio es un establo. Aquí se pueden entrenar caballeros."));
        engDesc = new ArrayList<>();
        engDesc.add("This is an area available for building construction.");
        engDesc.add("This building is a castle. Leveling up will reduce the cost of other buildings. If a player is left with no castles he loses.");
        engDesc.add("This building is an archery where archers can be recruited.");
        engDesc.add("This building is a barracks where warriors can be recruited.");
        engDesc.add("This building generates gold.");
        engDesc.add("This is an area available for gold mine construction.");
        engDesc.add("This building generates food.");
        engDesc.add("This building is a blacksmith where you may level up your troops.");
        engDesc.add("This building is a stable where knights can be recruited.");

    }
    
    public List<Integer> getPlainTerrainBuildings(){
    	List<Integer> plainTerrainBuildings = new ArrayList<Integer>();
    	plainTerrainBuildings.add(Info.STABLE);
    	plainTerrainBuildings.add(Info.ARCHERY);
    	plainTerrainBuildings.add(Info.BARRACKS);
    	plainTerrainBuildings.add(Info.MILL);
    	return plainTerrainBuildings;
    }

    public InformationBuilding getBuildingInformation(int id,String language){
        String desc;
        if(language.equals(new Locale("en").getLanguage())){
            desc = engDesc.get(id);
        }else{
            desc = infoList.get(id).getDescription();
        }
        return new InformationBuilding(infoList.get(id).getId(),infoList.get(id).getName(),desc);

    }
    public ArrayList<InformationBuilding> getConstructable(int type){
        ArrayList<InformationBuilding> res = new ArrayList<>();
        if(type == EMPTY){
            for(InformationBuilding i : infoList) {
                if(i.getId() != EMPTY && i.getId() != CASTLE && i.getId() != GOLD && i.getId() != TERR_GOLD && i.getId() != BLACKSMITH)
                    res.add(i);
            }
        }else{
            res.add(infoList.get(type));
        }
        return res;
    }
    public int getCost(int type){
        switch (type){
            case ARCHERY:
                return COST_ARCHER;
            case STABLE:
                return COST_HORSEMAN;
            case BARRACKS:
                return COST_WARRIOR;
            default:
                return 0;
        }
    }
}
