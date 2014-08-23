package com.zayandroid.fineandall.mainapp.models;

/**
 * Created by esheetrit on 22/08/2014.
 */
public class Question {

    private long id;
    public String question;
    public String imageUrl;
    public int yesCount;
    public int noCount;

    //TODO: add question comments
    //TODO: add question type
    //TODO: add question rate

    public Question(long id, String question, String imageUrl, int yesCount, int noCount) {
        this.id = id;
        this.question = question;
        this.imageUrl = imageUrl;
        this.yesCount = yesCount;
        this.noCount = noCount;
    }
}
