package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.DownloadTab;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.SQLiteDatabase.Tutorials_SqliteOpenHelper;
import com.example.uniproject.UpdateDatabase.UpdateDatabase;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.PlayActivity.PlayActivity;
import com.example.uniproject.coursesActivity.SingleItemModel;
import java.util.ArrayList;

public class DownloadRecyclerView extends RecyclerView.Adapter<DownloadRecyclerView.ViewHolder> {
    private final Context downContext;
    private final ArrayList<SingleItemModel> downOneRowList;
    private final LayoutInflater inflater;

    public DownloadRecyclerView(Context dContext, ArrayList<SingleItemModel> dOneRowList){
        this.inflater = LayoutInflater.from(dContext);
        this.downContext = dContext;
        this.downOneRowList = dOneRowList;
    }


    @NonNull
    @Override
    public DownloadRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.download_recylerview_activity,parent,false);
        return new DownloadRecyclerView.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DownloadRecyclerView.ViewHolder holder, int position) {
        final Intent downIntent = new Intent(downContext, PlayActivity.class);

        SingleItemModel downMain = downOneRowList.get(position);
        holder.download_lectureImg.setImageResource(downMain.getLectureImg());
        holder.download_lectureTopic.setText(downMain.getLectureTopic());
        holder.download_lectureInfo.setText(downMain.getLectureInfo());


        holder.download_vMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(downContext, holder.download_vMenu);
            popup.inflate(R.menu.download_menu_options);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case (R.id.download_watch):

                        UpdateDatabase downUpdate = new UpdateDatabase(downContext);
                        downUpdate.updateCourse(DB_TABLES.LECTURE_HISTORY_FLAG, downMain.getFlagId());
                        downUpdate.updateTutorial(DB_TABLES.HISTORY_FLAG, downMain.getFlagId());

                        downIntent.putExtra("data", downMain.getFlagId());
                        downIntent.putExtra("dataNm",downMain.getLectureTopic());
                        downContext.startActivity(downIntent);
                        //Toast.makeText(downContext, "starting playActivity...", Toast.LENGTH_LONG).show();
                        break;

                    case (R.id.download_remove):

                        //put all the codes for item "add to list" here
                        /*UpdateDatabase rmvUpdate = new UpdateDatabase(downContext);
                        rmvUpdate.updateCourse(DB_TABLES.LECTURE_DOWNLOAD_FLAG, 0);
                        rmvUpdate.updateTutorial(DB_TABLES.DOWNLOAD_FLAG, 0);*/

                        CourseActivityDatabase courseDatabase = new CourseActivityDatabase(downContext);
                        Tutorials_SqliteOpenHelper tutorials = new Tutorials_SqliteOpenHelper(downContext);

                        SQLiteDatabase db = courseDatabase.getWritableDatabase();
                        SQLiteDatabase tDB = tutorials.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(DB_TABLES.LECTURE_DOWNLOAD_FLAG, 0);
                        db.update(DB_TABLES.COURSE_TABLE_NAME,
                                values,
                                DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(downMain.getFlagId())});


                        ContentValues tValue = new ContentValues();
                        tValue.put(DB_TABLES.DOWNLOAD_FLAG, 0);
                        tDB.update(DB_TABLES.TABLE_NAME,
                                tValue, DB_TABLES.ID + " = ? ", new String[]{Integer.toString(downMain.getFlagId())});

                        tDB.close();
                        db.close();


                        downOneRowList.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(downContext, "Video has been removed from download list", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            });
            popup.show();
        });
        holder.download_card.setOnClickListener(v ->{
            downIntent.putExtra("data", downMain.getFlagId());
            downIntent.putExtra("dataNm",downMain.getLectureTopic());
            downContext.startActivity(downIntent);
        });
    }


    @Override
    public int getItemCount() {
        return downOneRowList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView download_lectureImg;
        ImageView download_vMenu;
        TextView download_lectureTopic;
        TextView download_lectureInfo;
        CardView download_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            download_lectureImg = itemView.findViewById(R.id.download_vImgList);
            download_vMenu = itemView.findViewById(R.id.download_dot_menu);
            download_lectureTopic = itemView.findViewById(R.id.download_vTxtTitle);
            download_lectureInfo = itemView.findViewById(R.id.download_vTxtDesc);
            download_card = itemView.findViewById(R.id.download_tab_cardView);
        }
    }
}
