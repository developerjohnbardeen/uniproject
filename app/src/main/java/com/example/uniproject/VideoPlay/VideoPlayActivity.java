package com.example.uniproject.VideoPlay;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uniproject.R;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;


import java.util.ArrayList;

public class VideoPlayActivity extends AppCompatActivity {


    private SimpleExoPlayer player;
    private PlayerView playerView;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition;
    private PlaybackStateListener playbackStatsListener;
    private static final String TAG = VideoPlayActivity.class.getName();
    ArrayList<Uri> videoPath = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_video_play);


        videoPath.add(Uri.parse(getString(R.string.media_url)));
        videoPath.add(Uri.parse(getString(R.string.media_url)));

        playbackStatsListener = new PlaybackStateListener();
        playerView = findViewById(R.id.video_view);
        initializePlayer();
    }

    private void initializePlayer(){
        if (player == null){
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            trackSelector.setParameters(
                    trackSelector.buildUponParameters().setMaxVideoSizeSd()
            );
            player = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
        }

        //player = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(player);
        Uri uri = Uri.parse(getString(R.string.media_url));
        MediaSource mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.addListener(playbackStatsListener);
        player.prepare(mediaSource, false, false);

    }

    private MediaSource buildMediaSource(Uri uri){
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this,"exoplayer-codelib");


        ProgressiveMediaSource.Factory mediaSourceFactory =
                new ProgressiveMediaSource.Factory(dataSourceFactory);

        MediaSource mediaSource_1 = mediaSourceFactory.createMediaSource(uri);

        Uri audio = Uri.parse(getString(R.string.media_url));
        MediaSource mediaSource_2 = mediaSourceFactory.createMediaSource(audio);
        return new ConcatenatingMediaSource(mediaSource_1,mediaSource_2);
        /*
        DashMediaSource.Factory mediaSourceFactory = new DashMediaSource.Factory(dataSourceFactory);
        return mediaSourceFactory.createMediaSource(uri);*/
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @SuppressLint("InlinedApi")
    //never delete this part of code
    /*private void hideSystemUi(){
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }*/

    private void releasePlayer(){
        if (player != null){
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.removeListener(playbackStatsListener);
            player.release();
            player = null;
        }
    }


    private static class PlaybackStateListener implements Player.EventListener {
        public void onPlayerState(boolean playWhenReady, int playbackState){
            String stateString;
            switch (playbackState){
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE  -";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE -";
                    break;
            }
            Log.d(TAG,"changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }
}