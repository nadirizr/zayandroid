package com.zayandroid.fineandall.mainapp;

import android.content.Context;

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
        String uri = String.format("http://%s/posts", SERVER_URL);
        JsonArrayRequest request = new JsonArrayRequest(uri,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Question> questions = new ArrayList<Question>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String text = object.getString("text");
                                String imageUrl = object.getString("imageUrl");
                                int yesCount = object.getInt("yes");
                                int noCount = object.getInt("no");

                                Question question = new Question(text, imageUrl, yesCount, noCount);
                                questions.add(question);
                            } catch (JSONException e) {
                                // Damn
                            }
                        }

                        listener.onQuestionsReceived(questions);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Well, damn
                        error.printStackTrace();
                    }
                });
        queue.add(request);
    }

    public interface QuestionsResponseListener {
        public void onQuestionsReceived(List<Question> questions);
    }
}
