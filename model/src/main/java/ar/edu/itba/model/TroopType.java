package ar.edu.itba.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 28/11/16.
 */
public enum TroopType {
    warrior(0),archer(1),horseman(2);
    private int type;

    TroopType(int numVal) {
        this.type = numVal;
    }

    public int getType() {
        return type;
    }
    // Lookup table
    private static final Map<Integer,TroopType> lookup = new HashMap<>();

    // Populate the lookup table on loading time
    static {
        for (TroopType s : EnumSet.allOf(TroopType.class))
            lookup.put(s.getType(), s);
    }

    // This method can be used for reverse lookup purpose
    public static TroopType get(int type) {
        return lookup.get(type);
    }
}
