package ar.edu.itba.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 26/11/16.
 */
public enum SectorType {
    EMPTY(0),
    CASTLE(1),
    ARCHERY(2),
    BARRACKS(3),
    GOLD(4),
    TERR_GOLD(5),
    MILL(6),
    STABLE(8);

    private int type;

    SectorType(int numVal) {
        this.type = numVal;
    }

    public int getType() {
        return type;
    }
    // Lookup table
    private static final Map<Integer,SectorType> lookup = new HashMap<>();

    // Populate the lookup table on loading time
    static {
        for (SectorType s : EnumSet.allOf(SectorType.class))
            lookup.put(s.getType(), s);
    }

    // This method can be used for reverse lookup purpose
    public static SectorType get(int type) {
        return lookup.get(type);
    }
}
