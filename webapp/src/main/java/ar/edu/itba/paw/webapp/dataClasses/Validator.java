package ar.edu.itba.paw.webapp.dataClasses;

/**
 * Created by lucas on 05/10/16.
 */
public class Validator {
    private static final int DIM = 100;

    public static boolean validBoardPosition(String pos) {
        if(isInteger(pos)){
            if(Integer.parseInt(pos) < DIM){
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
}
