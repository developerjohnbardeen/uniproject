package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.HistoryTab;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Layout;
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

public class HistoryRecyclerView extends RecyclerView.Adapter<HistoryRecyclerView.ViewHolder> {
    private final Context hisContext;
    private ArrayList<SingleItemModel> hisOneRowList;
    private final LayoutInflater inflater;

    public HistoryRecyclerView(Context hContext , ArrayList<SingleItemModel> hOneRowList){
        this.inflater = LayoutInflater.from(hContext);
        this.hisContext = hContext;
        this.hisOneRowList = hOneRowList;
    }


    @NonNull
    @Override
    public HistoryRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.history_recylerview_activity,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecyclerView.ViewHolder holder, int position) {
        final Intent hisIntent = new Intent(hisContext, PlayActivity.class);

        SingleItemModel vMain = hisOneRowList.get(position);
        holder.history_lectureImg.setImageResource(vMain.getLectureImg());
        holder.history_lectureTopic.setText(vMain.getLectureTopic());
        holder.history_lectureInfo.setText(vMain.getLectureInfo());


        holder.history_vMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(hisContext, holder.history_vMenu);
            popup.inflate(R.menu.history_menu_option);
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()){
                    case (R.id.history_watch):
                        //Toast.makeText(hisContext, "starting playActivity..." , Toast.LENGTH_LONG).show();
                        hisIntent.putExtra("data", vMain.getFlagId());
                        hisIntent.putExtra("dataNm",vMain.getLectureTopic());
                        hisContext.startActivity(hisIntent);
                        break;

                    case (R.id.history_remove):

                        //put all the codes for item "add to list" here
                        CourseActivityDatabase courseDatabase = new CourseActivityDatabase(hisContext);
                        Tutorials_SqliteOpenHelper tutorials = new Tutorials_SqliteOpenHelper(hisContext);

                        SQLiteDatabase db = courseDatabase.getWritableDatabase();
                        SQLiteDatabase tDB = tutorials.getWritableDatabase();

                        ContentValues courseValues = new ContentValues();
                        courseValues.put(DB_TABLES.LECTURE_HISTORY_FLAG, 0);
                        db.update(DB_TABLES.COURSE_TABLE_NAME,
                                courseValues, DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(vMain.getFlagId())});

                        ContentValues tContent = new ContentValues();
                        tContent.put(DB_TABLES.HISTORY_FLAG, 0);
                        tDB.update(DB_TABLES.TABLE_NAME,
                                tContent, DB_TABLES.ID + " = ? ", new String[]{Integer.toString(vMain.getFlagId())});

                        db.close();
                        tDB.close();

                        Toast.makeText(hisContext, "Video has been removed from history list " , Toast.LENGTH_SHORT).show();
                        hisOneRowList.remove(position);
                        notifyDataSetChanged();

                        break;
                    default:
                        break;
                }
                return true;
            });
            popup.show();
        });
        holder.history_card.setOnClickListener(v ->{
            hisIntent.putExtra("data", vMain.getFlagId());
            hisIntent.putExtra("dataNm",vMain.getLectureTopic());
            hisContext.startActivity(hisIntent);
        });
    }



    @Override
    public int getItemCount() {
        return hisOneRowList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView history_lectureImg;
        ImageView history_vMenu;
        TextView history_lectureTopic;
        TextView history_lectureInfo;
        CardView history_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            history_lectureImg = itemView.findViewById(R.id.history_vImgList);
            history_vMenu = itemView.findViewById(R.id.history_dot_menu);
            history_lectureTopic = itemView.findViewById(R.id.history_vTxtTitle);
            history_lectureInfo = itemView.findViewById(R.id.history_vTxtDesc);
            history_card = itemView.findViewById(R.id.history_tab_cardView);
        }
    }
}
