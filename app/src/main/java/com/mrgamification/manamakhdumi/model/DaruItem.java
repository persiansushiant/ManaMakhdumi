package com.mrgamification.manamakhdumi.model;

import com.orm.SugarRecord;

public class DaruItem extends SugarRecord<DaruItem> {
    String daruName;
    String daruLenght;
    String nextStop;
    int status;

    public DaruItem(String daruName, String daruLenght, String nextStop, int status) {
        this.daruName = daruName;
        this.daruLenght = daruLenght;
        this.nextStop = nextStop;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DaruItem() {
    }

    public String getDaruName() {
        return daruName;
    }

    public void setDaruName(String daruName) {
        this.daruName = daruName;
    }

    public String getDaruLenght() {
        return daruLenght;
    }

    public void setDaruLenght(String daruLenght) {
        this.daruLenght = daruLenght;
    }

    public String getNextStop() {
        return nextStop;
    }

    public void setNextStop(String nextStop) {
        this.nextStop = nextStop;
    }


}
