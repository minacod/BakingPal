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
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

/**
 * Created by shafy on 28/11/2017.
 */

public class StepDetailsFragment extends Fragment implements ExoPlayer.EventListener {

    private String mVideoUrl;
    private String mImageUrl;
    private String mDescription;

    private static String VIDEO_URL ="video_url";
    private static String IMAGE_URL ="image_url";
    private static String DESCRIPTION ="description";

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
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState!=null){
            mImageUrl = savedInstanceState.getString(IMAGE_URL);
            mVideoUrl = savedInstanceState.getString(VIDEO_URL);
            mDescription = savedInstanceState.getString(DESCRIPTION);
        }

        mBinding = FragmentStepDetailsBinding.inflate(inflater,container,false);

        mBinding.tvDescription.setText(mDescription);
        mPlayerView=mBinding.simpleExoPlayerView;

        if(mVideoUrl!=null&&!mVideoUrl.equals("")){
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

        return mBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(IMAGE_URL,mImageUrl);
        outState.putString(VIDEO_URL,mVideoUrl);
        outState.putString(DESCRIPTION,mDescription);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        if(mMediaSession!=null)
            mMediaSession.setActive(false);
    }

    private void initializePlayer(Uri mediaUri) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(mBinding.getRoot().getContext(), trackSelector, loadControl);
        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.addListener(this);

        String userAgent = Util.getUserAgent(mBinding.getRoot().getContext(), "BakingPal");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                mBinding.getRoot().getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        mExoPlayer.prepare(mediaSource);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
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

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            Log.d(TAG, "onPlayerStateChanged: PLAYING");
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,mExoPlayer.getCurrentPosition(),1f);
            mMediaSession.setPlaybackState(mStateBuilder.build());
        } else if((playbackState == ExoPlayer.STATE_READY)){
            Log.d(TAG, "onPlayerStateChanged: PAUSED");
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,mExoPlayer.getCurrentPosition(),1f);
            mMediaSession.setPlaybackState(mStateBuilder.build());
        }

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

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
    }
    public static class Receiver extends BroadcastReceiver {
        public Receiver(){}
        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession,intent);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
