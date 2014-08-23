package com.zayandroid.fineandall.mainapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zayandroid.fineandall.mainapp.models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lagi on 8/23/14.
 */
public class ServerApi {
    public static final String SERVER_URL = "whispering-beyond-9340.herokuapp.com";

    public static void fetchQuestions(Context context, final QuestionsResponseListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String uri = String.format("http://%s/questions", SERVER_URL);
        JsonArrayRequest request = new JsonArrayRequest(uri,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Question> questions = new ArrayList<Question>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Question question = Question.fromJSON(object);
                                questions.add(question);
                            } catch (JSONException e) {
                                Log.e("ServerApi", "Error while parsing post", e);
                                e.printStackTrace();
                            }
                        }

                        listener.onQuestionsReceived(questions);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ServerApi", "Error while getting posts", error);
                        error.printStackTrace();
                    }
                });
        queue.add(request);
    }

    public static void answerQuestion(Question question, Answer answer) {
        // TODO report to the server
    }

    public static void createQuestion(Context context, String text, String imageUrl, final NewQuestionListener listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String uri = String.format("http://%s/questions", SERVER_URL);
        try {
            JSONObject params = new JSONObject();
            params.put("text", text);
            params.put("imageUrl", imageUrl);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, uri, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Question question = Question.fromJSON(response);
                                listener.onQuestionCreated(question);
                            } catch (JSONException e) {
                                listener.onFailure(e);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ServerApi", "Error while getting posts", error);
                            error.printStackTrace();
                            listener.onFailure(error);
                        }
                    });
            queue.add(request);
        } catch (JSONException e) {
            listener.onFailure(e);
        }
    }

    public enum Answer {
        YES,
        NO
    }

    public interface QuestionsResponseListener {
        public void onQuestionsReceived(List<Question> questions);
    }

    public interface NewQuestionListener {
        public void onQuestionCreated(Question question);
        public void onFailure(Exception e);
    }
}
