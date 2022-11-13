package com.mrgamification.manamakhdumi.model;

public class Question {
    String q;
    String j1;
    String j2;
    String j3;
    int answer;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getJ1() {
        return j1;
    }

    public void setJ1(String j1) {
        this.j1 = j1;
    }

    public String getJ2() {
        return j2;
    }

    public void setJ2(String j2) {
        this.j2 = j2;
    }

    public String getJ3() {
        return j3;
    }

    public void setJ3(String j3) {
        this.j3 = j3;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public Question(String q, String j1, String j2, String j3, int answer) {
        this.q = q;
        this.j1 = j1;
        this.j2 = j2;
        this.j3 = j3;
        this.answer = answer;
    }
}
