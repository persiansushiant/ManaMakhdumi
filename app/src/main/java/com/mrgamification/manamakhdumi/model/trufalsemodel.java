package com.mrgamification.manamakhdumi.model;

public class trufalsemodel {
    String question;
    boolean tf;
    String payam;

    public trufalsemodel(String question, boolean tf, String payam) {
        this.question = question;
        this.tf = tf;
        this.payam = payam;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    public String getPayam() {
        return payam;
    }

    public void setPayam(String payam) {
        this.payam = payam;
    }
}
