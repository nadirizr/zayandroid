package com.zayandroid.fineandall.mainapp.models;

/**
 * Created by esheetrit on 22/08/2014.
 */
public class Question {

    public String question;
    public String imageUrl;
    public int yesCount;
    public int noCount;

    //TODO: add question comments
    //TODO: add question type
    //TODO: add question rate

    public Question(String question, String imageUrl, int yesCount, int noCount) {
        this.question = question;
        this.imageUrl = imageUrl;
        this.yesCount = yesCount;
        this.noCount = noCount;
    }
}
