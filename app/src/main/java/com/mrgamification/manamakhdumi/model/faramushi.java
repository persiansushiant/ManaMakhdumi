package com.mrgamification.manamakhdumi.model;

import com.orm.SugarRecord;

public class faramushi extends SugarRecord<faramushi> {
    int daruItem;
    String time;

    public int getDaruItem() {
        return daruItem;
    }
    public faramushi(){}

    public void setDaruItem(int daruItem) {
        this.daruItem = daruItem;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public faramushi(int daruItem, String time) {
        this.daruItem = daruItem;
        this.time = time;
    }
}
