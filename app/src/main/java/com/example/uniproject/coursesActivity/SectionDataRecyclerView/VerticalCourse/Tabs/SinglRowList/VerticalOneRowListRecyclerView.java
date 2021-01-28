package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.SQLiteDatabase.Tutorials_SqliteOpenHelper;
import com.example.uniproject.UpdateDatabase.UpdateDatabase;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.PlayActivity.PlayActivity;
import com.example.uniproject.coursesActivity.SingleItemModel;
import java.util.ArrayList;

public class VerticalOneRowListRecyclerView extends RecyclerView.Adapter<VerticalOneRowListRecyclerView.ViewHolder> {

    private final Context mContext;
    private final ArrayList<SingleItemModel> mOneRowList;
    private final int PROGRESS_MAX = 100;
    private int PROGRESS_CURRENT = 0;

    public VerticalOneRowListRecyclerView(Context context, ArrayList<SingleItemModel> onerowList){
        this.mContext = context;
        this.mOneRowList = onerowList;
    }

    @NonNull
    @Override
    public VerticalOneRowListRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vertical_one_row_list_course,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalOneRowListRecyclerView.ViewHolder holder, int position) {
        final Intent intent = new Intent(mContext, PlayActivity.class);

        SingleItemModel vMain = mOneRowList.get(position);
        holder.lectureImg.setImageResource(vMain.getLectureImg());
        holder.lectureTopic.setText(vMain.getLectureTopic());
        holder.lectureInfo.setText(vMain.getLectureInfo());



        holder.vMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(mContext, holder.vMenu);
            popup.inflate(R.menu.vertical_menu);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case (R.id.watch):

                        UpdateDatabase update = new UpdateDatabase(mContext);
                        update.updateCourse(DB_TABLES.LECTURE_HISTORY_FLAG, vMain.getFlagId());
                        update.updateTutorial(DB_TABLES.HISTORY_FLAG, vMain.getFlagId());


                        intent.putExtra("data", vMain.getFlagId());
                        intent.putExtra("dataNm", vMain.getLectureTopic());
                        mContext.startActivity(intent);
                        break;

                    case (R.id.add_to_list):
                        //put all the codes for item "add to list" here

                        UpdateDatabase addUpdate = new UpdateDatabase(mContext);
                        addUpdate.updateCourse(DB_TABLES.LECTURE_ADD_TO_LIST_FLAG, vMain.getFlagId());
                        addUpdate.updateTutorial(DB_TABLES.ADD_TO_LIST_FLAG, vMain.getFlagId());


                        Toast.makeText(mContext, "Added to My list" /*+ count*/ , Toast.LENGTH_LONG).show();
                        break;
                    case  (R.id.download):
                        //put all the codes for item "download" here

                        //update (course and tutorial)  Database
                        UpdateDatabase downUpdate = new UpdateDatabase(mContext);
                        downUpdate.updateCourse(DB_TABLES.LECTURE_DOWNLOAD_FLAG, vMain.getFlagId());
                        downUpdate.updateTutorial(DB_TABLES.DOWNLOAD_FLAG, vMain.getFlagId());


                        progressBar(vMain.getFlagId(), vMain.getLectureTopic());


                        Toast.makeText(mContext, "video is downloading" , Toast.LENGTH_LONG).show();
                        break;
                    default:
                        return true;
                }
                return true;
            });
            popup.show();
        });
        holder.card.setOnClickListener(v -> {

            UpdateDatabase history = new UpdateDatabase(mContext);
            history.updateCourse(DB_TABLES.LECTURE_HISTORY_FLAG, vMain.getFlagId());
            history.updateTutorial(DB_TABLES.HISTORY_FLAG, vMain.getFlagId());

            intent.putExtra("data", vMain.getFlagId());
            intent.putExtra("dataNm", vMain.getLectureTopic());
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
                builder.setContentText("Download complete")
                        .setProgress(0,0,false);
                builder.setSubText("");
                builder.setSmallIcon(R.drawable.ic_baseline_check_24);
                prgrsBarNotification.notify(notificationId, builder.build());
                PROGRESS_CURRENT = 0;
                /*
                CourseActivityDatabase downloadDB = new CourseActivityDatabase(mContext);
                SQLiteDatabase dDB = downloadDB.getWritableDatabase();
                ContentValues downloadValues = new ContentValues();
                downloadValues.put(DB_TABLES.LECTURE_DOWNLOAD_FLAG, flag);
                dDB.update(DB_TABLES.COURSE_TABLE_NAME,
                        downloadValues,
                        DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(flag)});
                dDB.close();*/
                UpdateDatabase prUpdate = new UpdateDatabase(mContext);
                prUpdate.updateCourse(DB_TABLES.LECTURE_DOWNLOAD_FLAG, flag);
                prUpdate.updateTutorial(DB_TABLES.DOWNLOAD_FLAG, flag);
                //Toast.makeText(PlayActivity.this, "" + val, Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return mOneRowList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView lectureImg;
        ImageView vMenu;
        TextView lectureTopic;
        TextView lectureInfo;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lectureImg = itemView.findViewById(R.id.vImgList);
            vMenu = itemView.findViewById(R.id.vertical_list_menu);
            lectureTopic = itemView.findViewById(R.id.vTxtTitle);
            lectureInfo = itemView.findViewById(R.id.vTxtDesc);
            card = itemView.findViewById(R.id.single_row_id_card);
        }
    }
}