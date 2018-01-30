package ar.edu.itba.paw.webapp.data;

import java.text.MessageFormat;

/**
 * Created by lucas on 05/10/16.
 */
public final class Validator {

	private static final String basePath = "/map/{0}/{1}";
	
	private Validator(){
		
	}

    public static boolean validBoardPosition(String pos) {
        if(isInteger(pos)){
            if(Integer.parseInt(pos) <= Info.MAP_SIZE){
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
	public static String format(int x, int y) {
    	return MessageFormat.format(basePath, x, y);
	}
}
