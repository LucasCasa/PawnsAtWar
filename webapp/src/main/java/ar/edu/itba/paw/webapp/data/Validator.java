package ar.edu.itba.paw.webapp.data;

import ar.edu.itba.model.Point;
import java.text.MessageFormat;

public final class Validator {

    private static final String basePath = "api/map/{0}/{1}";

    private Validator() {

    }

    public static boolean validBoardPosition(Point point) {
      return validBoardPosition(point.getX()) && validBoardPosition(point.getY());
    }


    public static boolean validBoardPosition(int pos) {
      return pos >= 0 && pos <= Info.MAP_SIZE;
    }

    public static int getValidPos(int num) {
        if (num < Info.VIEW_RANGE)
            num = Info.VIEW_RANGE;
        else if (num > Info.MAP_SIZE - Info.VIEW_RANGE)
            num = Info.MAP_SIZE - Info.VIEW_RANGE;
        return num;
    }

    private static boolean isValidPosToDisplay(int num) {
        return num >= Info.VIEW_RANGE && num <= Info.MAP_SIZE - Info.VIEW_RANGE;
    }

    public static String format(int x, int y) {
        if (isValidPosToDisplay(x) && isValidPosToDisplay(y)) {
            return MessageFormat.format(basePath, x, y);
        } else {
            return "#";
        }
    }
}
