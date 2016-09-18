package ar.edu.itba.paw.webapp.dataClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/18/16.
 */
public class InformationBuilding {
    private int id;
    private String name;
    private String description;

    public InformationBuilding(int id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }






}
