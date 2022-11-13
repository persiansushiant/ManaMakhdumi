package com.mrgamification.manamakhdumi.model;

import com.orm.SugarRecord;

public class Note extends SugarRecord<Note> {
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Note(String text) {
        this.text = text;
    }

    public Note(){}

}
