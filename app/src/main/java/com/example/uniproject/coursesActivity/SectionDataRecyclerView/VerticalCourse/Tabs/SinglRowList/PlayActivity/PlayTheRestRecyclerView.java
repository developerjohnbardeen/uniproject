package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.PlayActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.style.IconMarginSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.uniproject.Main;
import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SingleItemModel;

import java.util.ArrayList;

import be.tim.rijckaert.snaprecyclerview.GravitySnapHelper;

public class PlayTheRestRecyclerView extends RecyclerView.Adapter<PlayTheRestRecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<SingleItemModel> mList;
    private int PROGRESS_MAX = 100;
    private int PROGRESS_CURRENT = 0;

    public PlayTheRestRecyclerView(Context context ,ArrayList<SingleItemModel> list){
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater pInflater = LayoutInflater.from(mContext);
        view = pInflater.inflate(R.layout.activity_rest_of_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Intent intent = new Intent(mContext, PlayActivity.class);

        SingleItemModel pMain = mList.get(position);
        holder.lectureImg.setImageResource(pMain.getLectureImg());
        holder.lectureTopic.setText(pMain.getLectureTopic());
        holder.lectureInfo.setText(pMain.getLectureInfo());

        holder.menu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(mContext, holder.menu);
            popup.inflate(R.menu.playvideo_menu);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case (R.id.ply_add_to_list):
                            //put all the codes for item "add to list" here
                            Toast.makeText(mContext, "added to list", Toast.LENGTH_LONG).show();
                            return true;
                        case  (R.id.ply_download):
                            ////put all the codes for item "download" here
                            Toast.makeText(mContext, "video is downloading", Toast.LENGTH_LONG).show();
                            progressBar(pMain.getFlagId(), pMain.getLectureTopic());
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popup.show();
        });

        holder.layout.setOnClickListener(v -> {
            //set the code of revert back to playActivity
            //and getting data from database here
            intent.putExtra("data", pMain.getFlagId());
            intent.putExtra("dataNm", pMain.getLectureTopic());
            mContext.startActivity(intent);
        });
    }
    public void progressBar(int flag, String topic){
        String CHANNEL_ID = "my_channel_01";
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

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


        NotificationManagerCompat prgrsBarNotification = NotificationManagerCompat.from(mContext);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, CHANNEL_ID);
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
                builder.setContentText("Video downloaded")
                        .setProgress(0,0,false);
                builder.setSubText("");
                builder.setSmallIcon(R.drawable.ic_baseline_check_24);
                prgrsBarNotification.notify(notificationId, builder.build());
                PROGRESS_CURRENT = 0;


                CourseActivityDatabase downloadDB = new CourseActivityDatabase(mContext);
                SQLiteDatabase dDB = downloadDB.getWritableDatabase();
                ContentValues downloadValues = new ContentValues();
                downloadValues.put(DB_TABLES.LECTURE_DOWNLOAD_FLAG, flag);
                dDB.update(DB_TABLES.COURSE_TABLE_NAME,
                        downloadValues,
                        DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(flag)});
                dDB.close();
                //Toast.makeText(PlayActivity.this, "" + val, Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView lectureImg;
        ImageView menu;
        TextView lectureTopic;
        TextView lectureInfo;
        RelativeLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lectureImg = itemView.findViewById(R.id.video_img);
            menu = itemView.findViewById(R.id.dot_menu);
            lectureTopic = itemView.findViewById(R.id.title_id);
            lectureInfo = itemView.findViewById(R.id.desc_id);
            layout = itemView.findViewById(R.id.main_relative);
        }
    }
}
