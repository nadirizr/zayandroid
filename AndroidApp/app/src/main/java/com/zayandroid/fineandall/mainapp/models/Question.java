package com.zayandroid.fineandall.mainapp.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by esheetrit on 22/08/2014.
 */
public class Question {

    public String id;
    public String question;
    public String imageUrl;
    public int yesCount;
    public int noCount;

    //TODO: add question comments
    //TODO: add question type
    //TODO: add question rate

    public Question(String id, String question, String imageUrl, int yesCount, int noCount) {
        this.id = id;
        this.question = question;
        this.imageUrl = imageUrl;
        this.yesCount = yesCount;
        this.noCount = noCount;
    }

    public static Question fromJSON(JSONObject json) throws JSONException {
        String id = json.getString("_id");
        String text = json.getString("text");
        String imageUrl = json.getString("imageUrl");
        int yesCount = json.getInt("yes");
        int noCount = json.getInt("no");

        return new Question(id, text, imageUrl, yesCount, noCount);
    }
}
