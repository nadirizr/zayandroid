package com.zayandroid.fineandall.mainapp;


import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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

        openChart();

        return view;
    }

    private void openChart(){

        double totalCount    = yesCount + noCount;
        double yesPercentage = yesCount / totalCount;
        double noPercentage  = noCount / totalCount;

        // Pie Chart Section Names
        String[] code = new String[] { "No", "Yes" };

        // Pie Chart Section Value
        double[] distribution = { noPercentage, yesPercentage };

        // Color of each Pie Chart Sections
        int[] colors = { Color.RED, Color.GREEN };

        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries("");
        for(int i=0 ;i < distribution.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }

        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ;i<distribution.length;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }

        defaultRenderer.setChartTitle("Question Yes/No results");
        defaultRenderer.setChartTitleTextSize(60);
        defaultRenderer.setLabelsTextSize(40);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setLegendTextSize(40);
        defaultRenderer.setZoomButtonsVisible(true);

        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getActivity(), distributionSeries , defaultRenderer, "Question Results");

        // Start Activity
        startActivity(intent);

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
