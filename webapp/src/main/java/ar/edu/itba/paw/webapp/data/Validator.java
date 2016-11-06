package ar.edu.itba.paw.webapp.data;

/**
 * Created by lucas on 05/10/16.
 */
public class Validator {

    public static boolean validBoardPosition(String pos) {
        if(isInteger(pos)){
            if(Integer.parseInt(pos) < Info.MAP_SIZE){
                return true;
            }
        }
        return false;
    }
    public static boolean isInteger(String pos){

        if(pos == null){
            return false;
        }
        String regex = "0*\\d{1,9}";
        return pos.matches(regex);
    }
	public static int getValidPos(int num) {
		if(num<Info.VIEW_RANGE)
			num = Info.VIEW_RANGE;
		else if(num>Info.MAP_SIZE-Info.VIEW_RANGE)
			num = Info.MAP_SIZE-Info.VIEW_RANGE;
		return num;
	}
}
