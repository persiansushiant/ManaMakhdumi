package com.mrgamification.manamakhdumi.model;

import com.orm.SugarRecord;

public class Note extends SugarRecord<Note> {
    String text;
boolean isSoal;

    public boolean isSoal() {
        return isSoal;
    }

    public void setSoal(boolean soal) {
        isSoal = soal;
    }

    public Note(String text, boolean isSoal) {
        this.text = text;
        this.isSoal = isSoal;
    }

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
