package com.dorren.baking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dorren.baking.databinding.FragmentStepBinding;
import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.net.URISyntaxException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {
    private int recipeIndex;
    private int stepIndex;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private OnFragmentInteractionListener mListener;

    public StepFragment() {
        // Required empty public constructor
    }

    public Recipe getRecipe(int recipeIndex){
        return RecipeUtil.getCache(recipeIndex);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeIdx recipe index.
     * @param stepIdx recipe step index.
     * @return A new instance of fragment StepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepFragment newInstance(int recipeIdx, int stepIdx) {
        StepFragment fragment = new StepFragment();
        fragment.recipeIndex = recipeIdx;
        fragment.stepIndex = stepIdx;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStepBinding stepBinding = FragmentStepBinding.inflate(inflater, container, false);
        View rootView = stepBinding.getRoot();

        Recipe recipe = RecipeUtil.getCache(recipeIndex);
        Step step = recipe.getSteps()[stepIndex];
        stepBinding.setRecipe(recipe);
        stepBinding.setStep(step);

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
        URL videoUrl = step.getVideoURL();
        if(videoUrl == null){
            Log.d("StepFragment", "videoUrl null");
        }else {
            Uri uri = Uri.parse(step.getVideoURL().toString());
            initializePlayer(uri);
        }

        setupBtn(rootView);


        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        Context context = getActivity();

        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "BakingTime");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);

        }
    }

    private void setupBtn(View rootView){
        Button prevBtn = (Button)rootView.findViewById(R.id.prev_btn);
        Button nextBtn = (Button)rootView.findViewById(R.id.next_btn);

        prevBtn.setEnabled(stepIndex > 0);
        nextBtn.setEnabled(stepIndex < (getRecipe(recipeIndex).getSteps().length - 1));
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void previousStep(View view){
        Log.d("stepFrag", "prev btn clicked");
        if(stepIndex > 0) {
            Intent intent = new Intent(getActivity(), StepActivity.class);
            intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);
            intent.putExtra(RecipeUtil.STEP_INDEX, stepIndex - 1);

            startActivity(intent);
        }
    }


    public void nextStep(View view){
        Log.d("stepFrag", "next btn clicked");
        int length = getRecipe(recipeIndex).getSteps().length;
        if(stepIndex < (length - 1)) {
            Intent intent = new Intent(getActivity(), StepActivity.class);
            intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);
            intent.putExtra(RecipeUtil.STEP_INDEX, stepIndex + 1);

            startActivity(intent);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
