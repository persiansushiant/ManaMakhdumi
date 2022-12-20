package com.mrgamification.manamakhdumi.model;

import com.orm.SugarRecord;

/**
 * Created by kamran on 12/1/2017.
 */

public class Badge extends SugarRecord<Badge> {
    String name;
    String image;
    String Ation;
    int tedad;
    public Badge(){}

    public String getAtion() {
        return Ation;
    }

    public void setAtion(String ation) {
        Ation = ation;
    }

    public int getTedad() {
        return tedad;
    }

    public void setTedad(int tedad) {
        this.tedad = tedad;
    }

    public Badge(String name, String image, String ation, int tedad) {
        this.name = name;
        this.image = image;
        Ation = ation;
        this.tedad = tedad;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
