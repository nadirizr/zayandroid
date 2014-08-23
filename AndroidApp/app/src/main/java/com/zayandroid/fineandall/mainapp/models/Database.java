package com.zayandroid.fineandall.mainapp.models;

import com.zayandroid.fineandall.mainapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {
    public List<com.zayandroid.fineandall.mainapp.common.Question> questions;
    private static boolean initialized;
    private static Database db;

    public Database() {
        questions = new ArrayList<com.zayandroid.fineandall.mainapp.common.Question>();
        questions.add(new com.zayandroid.fineandall.mainapp.common.Question(0, 0, "Your girlfriend is fine and all but she got hairy armpits"));
        questions.add(new com.zayandroid.fineandall.mainapp.common.Question(0, 0, "Your girlfriend is fine and all but she likes lagi"));
    }

    public com.zayandroid.fineandall.mainapp.common.Question getQuestion() {
        Random r = new Random();
        int idx = r.nextInt() % questions.size();
        return questions.get(idx);
    }

    public void addQuestion(com.zayandroid.fineandall.mainapp.common.Question q) {
        questions.add(q);
    }

    public Database getInstance() {
        if (initialized == false) {
            db = new Database();
            initialized = true;
        }

        return db;
    }

}
