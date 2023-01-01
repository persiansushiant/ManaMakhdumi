package com.mrgamification.manamakhdumi.model;

public class chooseItem {
    String name;
    boolean isTrue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public chooseItem(String name, boolean isTrue) {
        this.name = name;
        this.isTrue = isTrue;
    }
}
