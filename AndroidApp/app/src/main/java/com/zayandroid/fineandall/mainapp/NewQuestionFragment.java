package com.zayandroid.fineandall.mainapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zayandroid.fineandall.mainapp.models.Database;
import com.zayandroid.fineandall.mainapp.models.Question;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class NewQuestionFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ProgressDialog progress;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewQuestionFragment newInstance() {
        NewQuestionFragment fragment = new NewQuestionFragment();
        return fragment;
    }
    public NewQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_question, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_new_question).setVisible(false);
        inflater.inflate(R.menu.compose, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                InputMethodManager imm =
                        (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                View editText = getView().findViewById(R.id.question_text);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                createAndDisplayQuestion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createAndDisplayQuestion() {
        String text = ((TextView) getView().findViewById(R.id.question_template)).getText().toString() +
                ((EditText) getView().findViewById(R.id.question_text)).getText().toString();
        String imageUrl = null;
        Database.getInstance().addQuestion(getActivity(), text, imageUrl, new Database.NewQuestionListener() {
            @Override
            public void onQuestionCreated(Question question) {
                if (progress != null) {
                    progress.dismiss();
                    progress = null;
                }
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, ExploreQuestionsFragment.newInstance(question.id))
                        .commit();
            }

            @Override
            public void onFailure(Exception e) {
                if (progress != null) {
                    progress.dismiss();
                    progress = null;
                }
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, ExploreQuestionsFragment.newInstance(null))
                        .commit();
            }
        });
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Creating ...");
        progress.show();
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

}
