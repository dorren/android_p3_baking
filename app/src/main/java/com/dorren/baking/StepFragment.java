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
import android.widget.ImageView;
import android.widget.TextView;

import com.dorren.baking.databinding.FragmentStepBinding;
import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;
import com.dorren.baking.utils.RecipeUtil;
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
import com.squareup.picasso.Picasso;

import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFragment.StepFragmentListener} interface
 * to handle interaction events.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFragment extends Fragment {
    private int recipeIndex;
    private int stepIndex;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ImageView mThumbnail;
    private TextView mNoVideo;

    private StepFragmentListener mListener;

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
            mNoVideo = (TextView) rootView.findViewById(R.id.step_video_unavailable);
            mNoVideo.setVisibility(View.VISIBLE);
        }else {
            setupThumbnail(rootView);
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

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepIndex > 0) {
                    mListener.onClickPrevious(recipeIndex, stepIndex - 1);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int length = getRecipe(recipeIndex).getSteps().length;
                if(stepIndex < (length - 1)) {
                    mListener.onClickNext(recipeIndex, stepIndex + 1);
                }
            }
        });
    }

    public void setupThumbnail(View rootView) {
        Context context = getContext();
        Recipe recipe = getRecipe(recipeIndex);
        Step step = recipe.getSteps()[stepIndex];

        // set image
        String thumbnailURL = step.getmThumbnailURL();
        mThumbnail = (ImageView) rootView.findViewById(R.id.video_thumb);
        mThumbnail.setVisibility(View.VISIBLE);
        if(thumbnailURL == null || thumbnailURL.equals("")){ // set default
            mThumbnail.setTag(android.R.drawable.ic_media_play);
            Picasso.with(context).load(android.R.drawable.ic_media_play).into(mThumbnail);
        }else{
            Picasso.with(context).load(thumbnailURL).into(mThumbnail);
        }

        mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo(v);
            }
        });
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepFragmentListener) {
            mListener = (StepFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement StepFragmentListener");
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

    public void playVideo(View view){
        mThumbnail.setVisibility(View.INVISIBLE);
        mPlayerView.setVisibility(View.VISIBLE);
        mExoPlayer.setPlayWhenReady(true);
    }


    public interface StepFragmentListener {
        // TODO: Update argument type and name
        void onClickPrevious(int recipeIndex, int stepIndex);
        void onClickNext(int recipeIndex, int stepIndex);
    }
}
