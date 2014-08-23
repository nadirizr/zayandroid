package com.zayandroid.fineandall.mainapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.Toast;

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

    public static abstract class OnSwipeTouchListener implements OnTouchListener {

        public final GestureDetector gestureDetector;

        public OnSwipeTouchListener (Context ctx){
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        private final class GestureListener extends SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }


    }
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
        view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeRight() {
            }

            public void onSwipeLeft() {
                //Toast.makeText(getActivity(), "onSwipeLeft", Toast.LENGTH_SHORT).show();

                //Question question = Database.getInstance().getQuestion();

                // Update view to present a new question
                //updateView( getView(), question );
                displayNewQuestion();
            }

            public void onSwipeTop() {
            }

            public void onSwipeBottom() {
            }

            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

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

    private void displayNewQuestion() {
        Database.getInstance().getQuestion(getActivity(), new Database.QuestionListener() {
            @Override
            public void onQuestion(Question question) {
                Fragment resultsFragment = ExploreQuestionsFragment.newInstance(question.id);
                final FragmentManager fm = getFragmentManager();
                final FragmentTransaction ft = fm.beginTransaction();
                getActivity().getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, 0, 0)
                        .replace(R.id.container, resultsFragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
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
