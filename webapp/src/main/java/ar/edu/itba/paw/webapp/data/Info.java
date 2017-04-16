package ar.edu.itba.paw.webapp.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team Muffin on 9/18/16.
 */
public abstract class Info {

	public static final int MAP_SIZE = 99;
	public static final int VIEW_RANGE = 4;
	
    public static final int EMPTY = 0;
    public static final int CASTLE = 1;
    public static final int ARCHERY = 2;
    public static final int BARRACKS = 3;
    public static final int GOLD = 4;
    public static final int TERR_GOLD = 5;
    public static final int MILL = 6;
    public static final int STABLE = 8;

    public static final int WARRIOR = 0;
    public static final int ARCHER = 1;
    public static final int HORSEMAN = 2;
    public static final int COST_WARRIOR= 30;
    public static final int COST_ARCHER= 50;
    public static final int COST_HORSEMAN= 70;

    public static final int RES_FOOD = 0;
    public static final int RES_GOLD = 1;

    
    public static List<Integer> getPlainTerrainBuildings(){
    	List<Integer> plainTerrainBuildings = new ArrayList<Integer>();
    	plainTerrainBuildings.add(Info.STABLE);
    	plainTerrainBuildings.add(Info.ARCHERY);
    	plainTerrainBuildings.add(Info.BARRACKS);
    	plainTerrainBuildings.add(Info.MILL);
    	return plainTerrainBuildings;
    }

    public static ArrayList<Integer> getConstructable(int type){
        ArrayList<Integer> res = new ArrayList<>();
        if(type == EMPTY){
            res.add(2);
            res.add(3);
            res.add(6);
            res.add(8);
        }else{
            res.add(4);
        }
        return res;
    }
    public static int getCost(int type){
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

    public static int getCostBuilding(int type, int i) {
        return 1000 + i*i*i*i;
    }

    public static double getBonus(int type, int level){
        switch (type){
            case Info.ARCHERY:
            case Info.BARRACKS:
            case Info.STABLE:
                return (Info.getCost(type)-(level-1));
            case Info.GOLD:
            case Info.MILL:
                return (level*0.1);
            case Info.CASTLE:
                return (level*10);
        }
        return 0;
    }
    
    public static int getBonusType(int type){
    	switch (type){
	        case Info.ARCHERY:
	        case Info.BARRACKS:
	        case Info.STABLE:
	        case Info.MILL:
	            return Info.RES_FOOD;
	        case Info.GOLD:
	            return Info.RES_GOLD;
//	        case Info.CASTLE:
//	        	return Info.
    	}
    	return 0;
    }
}
