package com.example.shafy.bakingpal.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.databinding.FragmentStepDetailsBinding;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

/**
 * Created by shafy on 28/11/2017.
 */

public class StepDetailsFragment extends Fragment  {

    private String mVideoUrl;
    private String mImageUrl;
    private String mDescription;
    private long mPosition;

    private static String POSITION ="position";

    private String TAG = "StepDetailsFragment";

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private FragmentStepDetailsBinding mBinding;


    public StepDetailsFragment() {
    }

    public void setmVideoUrl(String mVideoUrl) {
        this.mVideoUrl = mVideoUrl;
        if(mBinding!=null)
            updateUi();
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mPosition=0;
        if(savedInstanceState!=null){
            mPosition=savedInstanceState.getLong(POSITION);
        }

        mBinding = FragmentStepDetailsBinding.inflate(inflater,container,false);

        updateUi();

        return mBinding.getRoot();
    }
    void updateUi(){

        mBinding.tvDescription.setText(mDescription);
        mPlayerView=mBinding.simpleExoPlayerView;

        if (mExoPlayer!=null)
            releasePlayer();
        if(mVideoUrl!=null&&!mVideoUrl.equals("")){
            mPlayerView.setVisibility(View.VISIBLE);
            initializeMediaSession();
            initializePlayer(Uri.parse(mVideoUrl));
        }
        else if(mImageUrl!=null&&!mImageUrl.equals("")){
            mPlayerView.setVisibility(View.INVISIBLE);
            Picasso.with(getContext()).load(mImageUrl).error(R.drawable.ic_bakingapplogo).into(mBinding.ivExoBack);
        }else{
            mPlayerView.setVisibility(View.INVISIBLE);
            mBinding.ivExoBack.setImageResource(R.drawable.ic_bakingapplogo);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mExoPlayer != null) {
            mPosition = mExoPlayer.getCurrentPeriodIndex();
        }

        outState.putLong(POSITION,mPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBinding==null||mExoPlayer==null)
            updateUi();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
        if(mMediaSession!=null)
            mMediaSession.setActive(false);
        mMediaSession=null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        if(mMediaSession!=null)
            mMediaSession.setActive(false);
        mMediaSession=null;
    }

    private void initializePlayer(Uri mediaUri) {
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(mBinding.getRoot().getContext(), trackSelector, loadControl);
        mPlayerView.setPlayer(mExoPlayer);
        String userAgent = Util.getUserAgent(mBinding.getRoot().getContext(), "BakingPal");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                mBinding.getRoot().getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        if(mPosition!=0)mExoPlayer.seekTo(mPosition);
        mExoPlayer.prepare(mediaSource);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    void initializeMediaSession(){
        mMediaSession = new MediaSessionCompat(mBinding.getRoot().getContext(),TAG);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS|MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mMediaSession.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY|
                                PlaybackStateCompat.ACTION_PAUSE|
                                PlaybackStateCompat.ACTION_PLAY_PAUSE|
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS|
                                PlaybackStateCompat.ACTION_SEEK_TO
                );
        mMediaSession.setPlaybackState(mStateBuilder.build());
        mMediaSession.setCallback(new Callbacks());
        mMediaSession.setActive(true);
    }

    class Callbacks extends MediaSessionCompat.Callback{
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }

        @Override
        public void onSeekTo(long pos) {
            mExoPlayer.seekTo(pos);
        }

        @Override
        public void onStop() {
            mExoPlayer.stop();
        }
    }
    public static class Receiver extends BroadcastReceiver {
        public Receiver(){}
        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession,intent);
        }
    }
}
