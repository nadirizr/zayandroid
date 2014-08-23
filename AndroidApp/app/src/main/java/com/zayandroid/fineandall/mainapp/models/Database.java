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

    public Question getQuestion(Context context) {
        new DownloadQuestion(context).execute(1);

        while (questions.isEmpty()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Random generator = new Random();
        Question[] values = questions.values().toArray(new Question[]{});
        return values[generator.nextInt(values.length)];
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

    private class DownloadQuestion extends AsyncTask<Integer, Integer, Void> {
        private Context context;

        private DownloadQuestion(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            ServerApi.fetchQuestions(context, new UpdateCurrentQuestion());
            return null;
        }

        class UpdateCurrentQuestion implements ServerApi.QuestionsResponseListener {
            @Override
            public void onQuestionsReceived(List<Question> questionsFromServer) {
                for (Question question : questionsFromServer) {
                    questions.put(question.id, question);
                }
            }

            @Override
            public void onFailure(Exception e) {
                questions.put("Mock", new Question("Mock", "MOCK MOCK"));
            }
        }
    }

}
