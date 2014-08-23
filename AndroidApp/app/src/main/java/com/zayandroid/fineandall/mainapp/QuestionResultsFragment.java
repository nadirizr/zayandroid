package com.zayandroid.fineandall.mainapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class QuestionResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String YES_COUNT = "yes_count";
    private static final String NO_COUNT = "no_count";

    // TODO: Rename and change types of parameters
    private int yesCount;
    private int noCount;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuestionResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuestionResultsFragment newInstance(int yesCount, int noCount) {
        QuestionResultsFragment fragment = new QuestionResultsFragment();
        Bundle args = new Bundle();
        args.putInt(YES_COUNT, yesCount);
        args.putInt(NO_COUNT, noCount);
        fragment.setArguments(args);
        return fragment;
    }
    public QuestionResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            yesCount = getArguments().getInt(YES_COUNT);
            noCount = getArguments().getInt(NO_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_results, container, false);

//        TextView yesCountView = (TextView) view.findViewById(R.id.yes_count);
//        yesCountView.setText("YES:\n" + yesCount);
//
//        TextView noCountView = (TextView) view.findViewById(R.id.no_count);
//        noCountView.setText("NO:\n" + noCount);

        return view;
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
