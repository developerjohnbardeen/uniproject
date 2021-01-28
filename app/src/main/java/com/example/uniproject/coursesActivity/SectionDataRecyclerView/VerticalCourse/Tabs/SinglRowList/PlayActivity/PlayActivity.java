package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.PlayActivity;


import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.*;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.Glide;
import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.SQLiteDatabase.Tutorials_SqliteOpenHelper;
import com.example.uniproject.UpdateDatabase.UpdateDatabase;
import com.example.uniproject.VideoPlay.VideoPlayActivity;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SingleItemModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import java.util.ArrayList;
import java.util.Objects;

import be.tim.rijckaert.snaprecyclerview.GravitySnapHelper;

public class PlayActivity extends AppCompatActivity {
    private RecyclerView playListRecyclerView;
    private RecyclerView.RecycledViewPool recyclePool;
    private SnapHelper hSnap;
    private PlayTheRestRecyclerView playTheRestRecyclerView;
    private ArrayList<SingleItemModel> plyRestList;
    private LinearLayout activity;
    private RelativeLayout playRlvLayout;
    private int xSize;
    private int ySize;


    private PlayerView plyView;
    private SimpleExoPlayer exoPlayer;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition;
    private PlayActivity.PlaybackStateListener playbackStatsListener;
    private static final String TAG = PlayActivity.class.getName();
    //private Uri vUri = Uri.parse("http://s1.dlserver.info/Movie/The.Avengers.2012/The.Avengers.2012_Trailer.mp4");
    private String vUri;
    private String sample;

    private TextView lectureTopic;
    private TextView lectureInfo;
    private TextView teacherName;
    private TextView timeId;
    private ImageView teacherImg;
    private TextView teacherProfession;
    private TextView teacherInfo;
    private TextView plyTxt;
    private String msg = "Android : ";
    private ImageButton addImg;
    private ImageButton likeImg;
    private ImageButton downloadImg;
    private ImageButton shareImg;
    private static boolean addFlag = false;
    private static boolean likeFlag = false;
    private static boolean shareFlag = false;
    private static boolean downloadFlag = false;
    private int milisecond = 5;
    private Intent pIntent;
    private Intent downIntent;
    private Intent historyIntent;
    private int courseNo;
    private int downCourseNo;
    private int PROGRESS_MAX = 100;
    private int PROGRESS_CURRENT = 0;
    private Intent iIntent;
    private Intent sIntent;
    private int val;
    private String sVal;
    private static int likeInt;
    private static int addInt;
    private static int downInt;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        actionBarDesign();
        setContentView(R.layout.activity_play);

        iIntent = getIntent();
        sIntent = getIntent();
        val = iIntent.getIntExtra("data", 0);
        sVal = sIntent.getStringExtra("dataNm");



        playbackStatsListener = new PlaybackStateListener();

        //components' id
        activity = findViewById(R.id.new_activity);
        plyView = findViewById(R.id.exoView);
        lectureTopic = findViewById(R.id.lecture_topic);
        lectureInfo = findViewById(R.id.lecture_info);
        teacherName = findViewById(R.id.teacher_name);
        timeId = findViewById(R.id.time_id);
        teacherImg = findViewById(R.id.teacher_img);
        teacherProfession = findViewById(R.id.teacher_profession);
        teacherInfo = findViewById(R.id.teacher_info);

        //
        addImg = findViewById(R.id.add_to_list);
        likeImg = findViewById(R.id.like_id);
        downloadImg = findViewById(R.id.download_id);
        shareImg = findViewById(R.id.share_id);


        lectureTopic.setText(sVal);
        teacherName.setText("Elon Musk");

        setTitle(R.string.now_playing);
        PlayNextThread playThread = new PlayNextThread(val);
        playThread.execute();

        //new PlyThread(val).execute(val);


        //insertComponentsValues(val);

        addImg.setOnClickListener(v -> {
            if (!addFlag) {
                addImg.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                UpdateDatabase plyUpdate = new UpdateDatabase(PlayActivity.this);
                plyUpdate.updateCourse(DB_TABLES.LECTURE_ADD_TO_LIST_FLAG, val);
                plyUpdate.updateTutorial(DB_TABLES.ADD_TO_LIST_FLAG, val);
                addFlag = true;
            } else{
                //addToList(val);
                AddThread thread = new AddThread(val);
                thread.execute();

            }
        });

        likeImg.setOnClickListener(v -> {
            if (!likeFlag){
                likeImg.setImageResource(R.drawable.ic_red_heart);
                UpdateDatabase favorite = new UpdateDatabase(PlayActivity.this);
                favorite.updateCourse(DB_TABLES.LECTURE_FAVORITE_FLAG, val);
                favorite.updateTutorial(DB_TABLES.FAVORITE_FLAG, val);
                likeFlag = true;

            }else {
                //favoriteFlag(val);
                FavoriteThread fThread = new FavoriteThread(val);
                fThread.execute();

            }
        });

        downloadImg.setOnClickListener(v -> {

            if (!downloadFlag){
                Glide.with(this).load(R.drawable.downloadgif).into(downloadImg);
                progressBar(val, sVal);
                downloadFlag = true;
            } else{

                //downloadFlag(val);
                DownThread downThread = new DownThread(val);
                downThread.execute();

            }
        });
        //share Intent
        shareImg.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "www.google.com");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, "Share video info via..");
            startActivity(shareIntent);
        });

        //Radius View for exoplayer
        plyView.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0,0,view.getWidth(), view.getHeight(), 50);
            }
        });
        plyView.setClipToOutline(true);
        //Getting Screen Info
        screenSizeFunction();

        //Dynamic View Height&Width
        plyView.getLayoutParams().height = ySize*40/100;

        activity.setOnClickListener(v -> {
            Intent intent = new Intent(PlayActivity.this, VideoPlayActivity.class);
            startActivity(intent);
        });
    }




    private class AddThread extends AsyncTask<Void, Void, Void>{
        int id;

        @Override
        protected void onPreExecute(){
            addImg.setImageResource(R.drawable.add_to_list);
        }

        public AddThread(int position){
            this.id = position;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            addToList(id);
            return null;
        }



        @Override
        protected void onPostExecute(Void voids){
            addFlag = false;
        }
    }





    public void addToList(int id){
        CourseActivityDatabase courseDatabase = new CourseActivityDatabase(PlayActivity.this);
        Tutorials_SqliteOpenHelper tutorials = new Tutorials_SqliteOpenHelper(PlayActivity.this);

        SQLiteDatabase db = courseDatabase.getWritableDatabase();
        SQLiteDatabase tDB = tutorials.getWritableDatabase();
        ContentValues courseValues = new ContentValues();

        courseValues.put(DB_TABLES.LECTURE_ADD_TO_LIST_FLAG, 0);
        db.update(DB_TABLES.COURSE_TABLE_NAME,
                courseValues, DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(id)});

        ContentValues tContent = new ContentValues();
        tContent.put(DB_TABLES.ADD_TO_LIST_FLAG, 0);
        tDB.update(DB_TABLES.TABLE_NAME,
                tContent, DB_TABLES.ID + " = ? ", new String[]{Integer.toString(id)});

        db.close();
        tDB.close();
    }


    private class DownThread extends AsyncTask<Void, Void, Void>{
        int id;
        public DownThread(int position){
            this.id = position;
        }

        @Override
        protected void onPreExecute(){
            downloadImg.setImageResource(R.drawable.download_com);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            downloadFlag(id);
            return null;
        }
        @Override
        protected void onPostExecute(Void voids){
            downloadFlag = false;
        }

    }


    public void downloadFlag(int id){
        CourseActivityDatabase courseDatabase = new CourseActivityDatabase(PlayActivity.this);
        Tutorials_SqliteOpenHelper tutorials = new Tutorials_SqliteOpenHelper(PlayActivity.this);

        SQLiteDatabase db = courseDatabase.getWritableDatabase();
        SQLiteDatabase tDB = tutorials.getWritableDatabase();

        ContentValues courseValues = new ContentValues();
        courseValues.put(DB_TABLES.LECTURE_DOWNLOAD_FLAG, 0);
        db.update(DB_TABLES.COURSE_TABLE_NAME,
                courseValues, DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(id)});

        ContentValues tContent= new ContentValues();
        tContent.put(DB_TABLES.DOWNLOAD_FLAG, 0);
        tDB.update(DB_TABLES.TABLE_NAME,
                tContent, DB_TABLES.ID + " = ? ", new String[]{Integer.toString(id)});

        db.close();
        tDB.close();
    }


    private class FavoriteThread extends AsyncTask<Void, Void, Void>{
        int id;

        public FavoriteThread(int position){
            this.id = position;
        }

        @Override
        protected void onPreExecute(){
            likeImg.setImageResource(R.drawable.ic_empty_heart);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            favoriteFlag(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void voids){
            likeFlag = false;
        }

    }





    public void favoriteFlag(int id){
        CourseActivityDatabase courseDatabase = new CourseActivityDatabase(PlayActivity.this);
        Tutorials_SqliteOpenHelper tutorials = new Tutorials_SqliteOpenHelper(PlayActivity.this);

        SQLiteDatabase db = courseDatabase.getWritableDatabase();
        SQLiteDatabase tDB = tutorials.getWritableDatabase();
        ContentValues courseValues = new ContentValues();

        courseValues.put(DB_TABLES.LECTURE_FAVORITE_FLAG, 0);
        db.update(DB_TABLES.COURSE_TABLE_NAME,
                courseValues, DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(id)});

        ContentValues tContent = new ContentValues();
        tContent.put(DB_TABLES.FAVORITE_FLAG, 0);
        tDB.update(DB_TABLES.TABLE_NAME,
                tContent, DB_TABLES.ID + " = ? ", new String[]{Integer.toString(id)});

        db.close();
        tDB.close();
    }



    public void progressBar(int flag, String topic){
        String CHANNEL_ID = "my_channel_01";
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) PlayActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //String CHANNEL_ID = "my_channel_01";
            CharSequence name = "Download channel";
            String Description = "This channel is for downloaded videos";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }


        NotificationManagerCompat prgrsBarNotification = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("" + topic)
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.download_com)
                .setPriority(NotificationCompat.PRIORITY_LOW);


        new CountDownTimer(20000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
                builder.setContentTitle("" + topic);
                builder.setSubText(PROGRESS_CURRENT + "%");
                prgrsBarNotification.notify(notificationId, builder.build());
                PROGRESS_CURRENT += 5;
            }

            @Override
            public void onFinish() {


                builder.setContentTitle("" + topic);
                builder.setContentText("Download complete")
                        .setProgress(0,0,false);
                builder.setSubText("");
                builder.setSmallIcon(R.drawable.ic_baseline_check_24);
                prgrsBarNotification.notify(notificationId, builder.build());
                downloadImg.setImageResource(R.drawable.ic_baseline_check_24);
                PROGRESS_CURRENT = 0;

                //updating database
                UpdateDatabase plyUpdate = new UpdateDatabase(PlayActivity.this);
                plyUpdate.updateCourse(DB_TABLES.LECTURE_DOWNLOAD_FLAG, flag);
                plyUpdate.updateTutorial(DB_TABLES.DOWNLOAD_FLAG, flag);
            }
        }.start();
    }



    //Getting Data for "Watch Next" RecyclerView
    public void getPlyNxtRestCourseData(){
        SQLiteDatabase db;
        CourseActivityDatabase courseDB;
        courseDB = new CourseActivityDatabase(this);

        db = courseDB.getWritableDatabase();
        plyRestList = new ArrayList<>();

        Cursor cursor = db.query(
                DB_TABLES.COURSE_TABLE_NAME,
                new String[]{DB_TABLES.COURSE_ID, DB_TABLES.LECTURE_IMAGE_URL, DB_TABLES.LECTURE_TOPIC, DB_TABLES.LECTURE_GENERAL_INFO},
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() != 0) {
            plyRestList.clear();

            while (cursor.moveToNext()) {
                SingleItemModel plyNextList = new SingleItemModel();

                //Getting data from database
                int dFlag = cursor.getInt(cursor.getColumnIndex(DB_TABLES.COURSE_ID));
                int lectureImg = cursor.getInt(cursor.getColumnIndex(DB_TABLES.LECTURE_IMAGE_URL));
                String lectureTopic = cursor.getString(cursor.getColumnIndex(DB_TABLES.LECTURE_TOPIC));
                String lectureInfo = cursor.getString(cursor.getColumnIndex(DB_TABLES.LECTURE_GENERAL_INFO));

                //Putting data to components
                plyNextList.setFlagId(dFlag);
                plyNextList.setLectureImg(lectureImg);
                plyNextList.setLectureTopic(lectureTopic);
                plyNextList.setLectureInfo(lectureInfo);

                plyRestList.add(plyNextList);
            }
        }

        //closing cursor and database
        assert cursor != null;
        cursor.close();
        db.close();
    }


    private class PlayNextThread extends  AsyncTask<Void, Void, Void>{

        int id = 0;

        public PlayNextThread(int position){
            this.id = position;
        }

        @Override
        protected void onPreExecute(){
            //Play next RecyclerView
            playListRecyclerView = findViewById(R.id.related_content_recyclerview);
            recyclePool = new RecyclerView.RecycledViewPool();
            hSnap = new GravitySnapHelper(Gravity.START);
        }



        @Override
        protected Void doInBackground(Void... voids) {
            try {

                insertComponentsValues(id);
                getPlyNxtRestCourseData();

            }catch (Exception e){
                Toast.makeText(PlayActivity.this, "An Error occurred ", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids){



            //PlayList RecyclerView
            playTheRestRecyclerView = new PlayTheRestRecyclerView(PlayActivity.this, plyRestList);
            LinearLayoutManager mNew = new LinearLayoutManager(PlayActivity.this,LinearLayoutManager.HORIZONTAL, false);
            playListRecyclerView.setLayoutManager(mNew);
            playListRecyclerView.setRecycledViewPool(recyclePool);

            //playListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            playListRecyclerView.setAdapter(playTheRestRecyclerView);
            hSnap.attachToRecyclerView(playListRecyclerView);

            mNew.scrollToPositionWithOffset(200,20);


            if (downInt > 0) {
                downloadImg.setImageResource(R.drawable.ic_baseline_check_24);
                downloadFlag = true;
            } else {
                downloadImg.setImageResource(R.drawable.download_com);
                downloadFlag = false;
            }
            if (addInt > 0) {
                addImg.setImageResource(R.drawable.ic_baseline_playlist_add_check_24);
                addFlag = true;
            } else {
                addImg.setImageResource(R.drawable.add_to_list);
                addFlag = false;
            }
            if (likeInt > 0) {
                likeImg.setImageResource(R.drawable.ic_red_heart);
                likeFlag = true;
            } else {
                likeImg.setImageResource(R.drawable.ic_empty_heart);
                likeFlag = false;
            }
        }

    }


    //data for each single component
    public void insertComponentsValues(int id){
        //create previous activity database and tables(including row) variable
        //once it is connected to server table is changeable(in cursor line)


        try {
            SQLiteOpenHelper cDatabaseHelper = new Tutorials_SqliteOpenHelper(PlayActivity.this);
            SQLiteDatabase db = cDatabaseHelper.getWritableDatabase();

            Cursor cCursor = db.query(DB_TABLES.TABLE_NAME,
                    new String[] {DB_TABLES.ID, DB_TABLES.LECTURE_VIDEO_URL, DB_TABLES.LECTURE_TOPIC, DB_TABLES.TEACHER_NAME, DB_TABLES. LECTURE_TIME_DURATION,
                            DB_TABLES.LECTURE_DETAIL_INFO, DB_TABLES.TEACHER_IMAGE_URL, DB_TABLES.TEACHER_PROFESSION, DB_TABLES.TEACHER_DETAIL_INFO, DB_TABLES.HISTORY_FLAG
                            , DB_TABLES.DOWNLOAD_FLAG, DB_TABLES.ADD_TO_LIST_FLAG, DB_TABLES.FAVORITE_FLAG},
                    DB_TABLES.ID + " = ?",
                    new String[]{Integer.toString(id)},
                    null,null,null,null);
            cCursor.moveToFirst();
            if (cCursor.getCount() != 0) {
                if (cCursor.moveToFirst()) {
                    //getting components' values
                    String vdUri = cCursor.getString(cCursor.getColumnIndex(DB_TABLES.LECTURE_VIDEO_URL));
                    String lctrTopc = cCursor.getString(cCursor.getColumnIndex(DB_TABLES.LECTURE_TOPIC));
                    String tchrName = cCursor.getString(cCursor.getColumnIndex(DB_TABLES.TEACHER_NAME));
                    String tmId = cCursor.getString(cCursor.getColumnIndex(DB_TABLES.LECTURE_TIME_DURATION));
                    String lctrInfo = cCursor.getString(cCursor.getColumnIndex(DB_TABLES.LECTURE_DETAIL_INFO));
                    String tchrProfession = cCursor.getString(cCursor.getColumnIndex(DB_TABLES.TEACHER_PROFESSION));
                    String tchrInfo = cCursor.getString(cCursor.getColumnIndex(DB_TABLES.TEACHER_DETAIL_INFO));
                    int tchrImg = cCursor.getInt(cCursor.getColumnIndex(DB_TABLES.TEACHER_IMAGE_URL));
                    int downFlagint = cCursor.getInt(cCursor.getColumnIndex(DB_TABLES.DOWNLOAD_FLAG));
                    int addFlagint = cCursor.getInt(cCursor.getColumnIndex(DB_TABLES.ADD_TO_LIST_FLAG));
                    int favoriteint = cCursor.getInt(cCursor.getColumnIndex(DB_TABLES.FAVORITE_FLAG));
                    //boolean isHistory = (cCursor.getInt(9) == 1); //HISTORY_FLAG

                    //setting components values
                    vUri = vdUri;
                    sample = vUri;

                    lectureTopic.setText(lctrTopc);
                    teacherName.setText(tchrName);
                    timeId.setText(tmId);
                    lectureInfo.setText(lctrInfo + "!!");
                    teacherImg.setImageResource(tchrImg);
                    teacherImg.setContentDescription(lctrTopc);
                    teacherProfession.setText(tchrProfession);
                    teacherInfo.setText(tchrInfo);

                    likeInt = favoriteint;
                    addInt = addFlagint;
                    downInt = downFlagint;
                }
                //closing cursor and db
                cCursor.close();
                db.close();
            }


        }catch (SQLiteException e){
            Toast.makeText(this, "Database unavailable!", Toast.LENGTH_SHORT).show();
        }
    }



    public void actionBarDesign(){

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.custom_action_design_play_activity, null);
        playRlvLayout = view.findViewById(R.id.ply_bck_bttn_layout);
        playRlvLayout.setOnClickListener(v -> onBackPressed());
        plyTxt = view.findViewById(R.id.ply_course_title);
        plyTxt.setText(R.string.now_playing);
        actionBar.setCustomView(view);
    }



    //Menu code
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_ply_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.main_ply_setting):
                Toast.makeText(PlayActivity.this, "Main Play Activity Setting Menu..", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
























    private void initializePlayer(String strUri){
        Uri uri = null;
        plyView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        //exoPlayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

        if (exoPlayer == null){
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            trackSelector.setParameters(
                    trackSelector.buildUponParameters().setMaxVideoSizeSd()
            );
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
        }

        //player = ExoPlayerFactory.newSimpleInstance(this);
        plyView.setPlayer(exoPlayer);
        uri = Uri.parse(strUri);

        MediaSource mediaSource = buildMediaSource(uri);
        exoPlayer.setPlayWhenReady(playWhenReady);
        exoPlayer.seekTo(currentWindow, playbackPosition);
        exoPlayer.addListener(playbackStatsListener);
        exoPlayer.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri){
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this,"exoplayer-codelib");

        ProgressiveMediaSource.Factory mediaSourceFactory =
                new ProgressiveMediaSource.Factory(dataSourceFactory);

        MediaSource mediaSource_1 = mediaSourceFactory.createMediaSource(uri);

        Uri audio = Uri.parse(vUri);
        MediaSource mediaSource_2 = mediaSourceFactory.createMediaSource(audio);
        return new ConcatenatingMediaSource(mediaSource_1, mediaSource_2);
        /*
        DashMediaSource.Factory mediaSourceFactory = new DashMediaSource.Factory(dataSourceFactory);
        return mediaSourceFactory.createMediaSource(uri);*/
    }

    @Override
    public void onStart() {
        super.onStart();

        try {
            while(vUri == null) {
                Thread.sleep(2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initializePlayer(vUri);
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if (exoPlayer == null) {
            initializePlayer(vUri);
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

    /*@SuppressLint("InlinedApi")
    private void hideSystemUi(){
        plyView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

     */

    private void releasePlayer(){
        if (exoPlayer != null){
            playWhenReady = exoPlayer.getPlayWhenReady();
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.removeListener(playbackStatsListener);
            exoPlayer.release();
            exoPlayer = null;
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
            Log.d(TAG,"changed state to " + stateString + " playWhenReady: " + playWhenReady);
        }
    }

    private void screenSizeFunction(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        xSize = size.x;
        ySize = size.y;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.util.Log.d(msg, "The onDestroy() event");
    }
}