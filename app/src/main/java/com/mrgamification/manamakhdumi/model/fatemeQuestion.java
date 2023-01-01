package com.mrgamification.manamakhdumi.model;

public class fatemeQuestion {
    String question,gozine1,gozine2,gozine3,gozine4,msg;
    int answer;

    public fatemeQuestion(String question, String gozine1, String gozine2, String gozine3, String gozine4, String msg, int answer) {
        this.question = question;
        this.gozine1 = gozine1;
        this.gozine2 = gozine2;
        this.gozine3 = gozine3;
        this.gozine4 = gozine4;
        this.msg = msg;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getGozine1() {
        return gozine1;
    }

    public void setGozine1(String gozine1) {
        this.gozine1 = gozine1;
    }

    public String getGozine2() {
        return gozine2;
    }

    public void setGozine2(String gozine2) {
        this.gozine2 = gozine2;
    }

    public String getGozine3() {
        return gozine3;
    }

    public void setGozine3(String gozine3) {
        this.gozine3 = gozine3;
    }

    public String getGozine4() {
        return gozine4;
    }

    public void setGozine4(String gozine4) {
        this.gozine4 = gozine4;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
