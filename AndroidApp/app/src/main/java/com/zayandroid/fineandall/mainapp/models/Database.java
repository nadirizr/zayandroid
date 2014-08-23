package com.zayandroid.fineandall.mainapp.models;

import com.zayandroid.fineandall.mainapp.ServerApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {
    public List<Question> questions;
    private static boolean initialized;
    private static Database db;

    public Database() {
        questions = new ArrayList<Question>();
        questions.add(new Question("1", "Your girlfriend is fine and all but she got hairy armpits", null, 70, 30));
        questions.add(new Question("2", "Your girlfriend is fine and all but she likes lagi", "http://upload.wikimedia.org/wikipedia/en/4/43/The_Ramen_Girl_poster.jpg", 20, 80));
    }

    public Question getQuestion() {
        Random r = new Random();
        int idx = r.nextInt(questions.size());
        return questions.get(idx);
    }

    public Question getQuestion(String questionId) {
        for (Question question : questions) {
            if (questionId.equals(question.id)) {
                return question;
            }
        }
        return null;
    }

    public Question addQuestion(String text, String imageUrl) {
        Question question = ServerApi.createQuestion(text, imageUrl);

        // TODO - replace this stub with a new question
        question = new Question(Integer.toString(new Random().nextInt()), text, imageUrl, 0, 0);

        questions.add(question);
        return question;
    }

    public static Database getInstance() {
        if (initialized == false) {
            db = new Database();
            initialized = true;
        }

        return db;
    }

}
