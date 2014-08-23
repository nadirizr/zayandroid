package com.zayandroid.fineandall.mainapp.models;

import android.content.Context;
import android.os.AsyncTask;

import com.zayandroid.fineandall.mainapp.ServerApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Database {
    public Map<String, Question> questions;
    private static boolean initialized;
    private static Database db;

    public Database() {
        questions = new HashMap<String, Question>();
    }

    public void getQuestion(Context context, final QuestionListener listener) {
        if (!questions.isEmpty()) {
            sendRandomQuestionToListener(listener);
        }
        ServerApi.fetchQuestions(context, new ServerApi.QuestionsResponseListener() {
            @Override
            public void onQuestionsReceived(List<Question> questionsFromServer) {
                for (Question question : questionsFromServer) {
                    questions.put(question.id, question);
                }
                sendRandomQuestionToListener(listener);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure(e);
            }
        });
    }

    private void sendRandomQuestionToListener(QuestionListener listener) {
        Random generator = new Random();
        Question[] values = questions.values().toArray(new Question[]{});
        Question question = values[generator.nextInt(values.length)];
        listener.onQuestion(question);
    }

    public Question getQuestion(String questionId) {
        return questions.get(questionId);
    }

    public void addQuestion(Context context, String text, String imageUrl, final NewQuestionListener listener) {
        ServerApi.createQuestion(context, text, imageUrl, new ServerApi.NewQuestionListener() {
            @Override
            public void onQuestionCreated(Question question) {
                questions.put(question.id, question);
                listener.onQuestionCreated(question);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure(e);
            }
        });
    }

    public static Database getInstance() {
        if (!initialized) {
            db = new Database();
            initialized = true;
        }

        return db;
    }

    public interface NewQuestionListener {
        public void onQuestionCreated(Question question);
        public void onFailure(Exception e);
    }

    public interface QuestionListener {
        public void onQuestion(Question question);
        public void onFailure(Exception e);
    }
}
