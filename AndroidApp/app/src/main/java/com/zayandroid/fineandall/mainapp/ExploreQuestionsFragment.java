package com.zayandroid.fineandall.mainapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zayandroid.fineandall.mainapp.models.Database;
import com.zayandroid.fineandall.mainapp.models.Question;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExploreQuestionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExploreQuestionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ExploreQuestionsFragment extends Fragment {
    private static final String QUESTION_ID = "question_id";

    private String questionId;

    private OnFragmentInteractionListener mListener;
    private ProgressDialog progress;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ExploreQuestionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreQuestionsFragment newInstance(String questionId) {
        ExploreQuestionsFragment fragment = new ExploreQuestionsFragment();
        if (questionId != null) {
            Bundle args = new Bundle();
            args.putString(QUESTION_ID, questionId);
            fragment.setArguments(args);
        }
        return fragment;
    }
    public ExploreQuestionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionId = getArguments().getString(QUESTION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (questionId == null) {
            Database.getInstance().getQuestion(getActivity(), new Database.QuestionListener() {
                @Override
                public void onQuestion(Question question) {
                    if (progress != null) {
                        progress.dismiss();
                        progress = null;
                    }
                    updateView(getView(), question);
                }

                @Override
                public void onFailure(Exception e) {
                    if (progress != null) {
                        progress.dismiss();
                        progress = null;
                    }
                }
            });
            progress = new ProgressDialog(getActivity());
            progress.setTitle("Loading ...");
            progress.show();
            return displayQuestion(inflater, container, null);
        } else {
            Question question = Database.getInstance().getQuestion(questionId);
            return displayQuestion(inflater, container, question);
        }
    }

    private View displayQuestion(LayoutInflater inflater, ViewGroup container, final Question question) {
        View view = inflater.inflate(R.layout.fragment_explore_questions, container, false);

        if (question != null) {
            updateView(view, question);
        }
        return view;
    }

    private void updateView(View view, final Question question) {
        TextView textView = (TextView) view.findViewById(R.id.question_text);
        textView.setTextSize(40);
        textView.setText(question.question);

        if (question.imageUrl != null) {
            new DownloadImageTask((ImageView) view.findViewById(R.id.question_image)).execute(question.imageUrl);
        }

        Button yesButton = (Button) view.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerApi.answerQuestion(question, ServerApi.Answer.YES);
                displayResultsScreen(question.yesCount + 1, question.noCount);
            }
        });

        Button noButton = (Button) view.findViewById(R.id.no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerApi.answerQuestion(question, ServerApi.Answer.NO);
                displayResultsScreen(question.yesCount, question.noCount + 1);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void displayResultsScreen(int yesCount, int noCount) {
        Fragment resultsFragment = QuestionResultsFragment.newInstance(yesCount, noCount);
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, resultsFragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap icon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                icon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
