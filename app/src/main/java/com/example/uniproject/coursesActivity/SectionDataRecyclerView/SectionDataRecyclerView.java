package com.example.uniproject.coursesActivity.SectionDataRecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.UpdateDatabase.UpdateDatabase;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.PlayActivity.PlayActivity;
import com.example.uniproject.coursesActivity.SingleItemModel;

import java.util.ArrayList;

public class SectionDataRecyclerView extends RecyclerView.Adapter<SectionDataRecyclerView.ViewHolder> {

    private Context rContext;
    private ArrayList<SingleItemModel> rItemModels;


    public SectionDataRecyclerView( Context context,ArrayList<SingleItemModel> itemModels){
        this.rItemModels = itemModels;
        this.rContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater rInflater = LayoutInflater.from(rContext);
        view = rInflater.inflate(R.layout.activity_single_course_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SectionDataRecyclerView.ViewHolder holder, int position) {
        final Intent plyIntent = new Intent(rContext, PlayActivity.class);

        final SingleItemModel itemModel = rItemModels.get(position);
        holder.itemName.setText(itemModel.getLectureTopic());
        holder.itemImg.setImageResource(itemModel.getLectureImg());

        //staring playActivity
        holder.cardView.setOnClickListener(v -> {

            //updating databases(course&Tutorial)
            UpdateDatabase sectionUpdate = new UpdateDatabase(rContext);
            sectionUpdate.updateCourse(DB_TABLES.LECTURE_HISTORY_FLAG, itemModel.getFlagId());
            sectionUpdate.updateTutorial(DB_TABLES.HISTORY_FLAG, itemModel.getFlagId());

            plyIntent.putExtra("data", itemModel.getFlagId());
            plyIntent.putExtra("dataNm",itemModel.getLectureTopic());
            rContext.startActivity(plyIntent);

            //Toast.makeText(rContext, "" + itemModel.getFlagId() , Toast.LENGTH_LONG).show();
        });


    }

    @Override
    public int getItemCount() {
        return (null != rItemModels ? rItemModels.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImg;
        TextView itemName;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImg = itemView.findViewById(R.id.itemImage);
            itemName = itemView.findViewById(R.id.tvTitle);
            cardView = itemView.findViewById(R.id.singleLayout);
        }
    }

}
