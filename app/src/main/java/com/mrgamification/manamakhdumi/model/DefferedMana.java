package com.mrgamification.manamakhdumi.model;

import com.orm.SugarRecord;

public class DefferedMana extends SugarRecord<DefferedMana> {
    String project;
    String userId;
    String tozih;
    String action;
    String time;
public DefferedMana(){}
    public DefferedMana(String project, String userId, String tozih, String action, String time) {
        this.project = project;
        this.userId = userId;
        this.tozih = tozih;
        this.action = action;
        this.time = time;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTozih() {
        return tozih;
    }

    public void setTozih(String tozih) {
        this.tozih = tozih;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
