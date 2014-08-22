package com.zayandroid.fineandall.mainapp.common;

/**
 * Created by esheetrit on 22/08/2014.
 */
public class Question {

    public String question;
    public int yesCount;
    public int noCount;

    //TODO: add question comments
    //TODO: add question type
    //TODO: add question rate

    //constructor
    public Question(int a, int b) {
        yesCount = a;
        noCount = b;
        question = "Your boyfriend  is fine and all.. but he like to jerk off to gradle!";
    }

    public Question() {
        yesCount = 0;
        noCount = 0;
        question = "Your girlfriend  is fine and all.. ";
    }
}
