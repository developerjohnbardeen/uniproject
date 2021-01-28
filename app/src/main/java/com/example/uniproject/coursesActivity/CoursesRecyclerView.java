package com.example.uniproject.coursesActivity;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.uniproject.R;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.SectionDataRecyclerView;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.VerticalCourseListActivity;

import java.net.PasswordAuthentication;
import java.util.ArrayList;

import be.tim.rijckaert.snaprecyclerview.GravitySnapHelper;

public class CoursesRecyclerView extends RecyclerView.Adapter<CoursesRecyclerView.ViewHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context rContext;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private SnapHelper snapHelper;


    public CoursesRecyclerView(Context context, ArrayList<SectionDataModel> dataList){
        this.rContext = context;
        this.dataList = dataList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater rInflater = LayoutInflater.from(rContext);
        view = rInflater.inflate(R.layout.courses_list_activity, parent, false);
        snapHelper = new GravitySnapHelper(Gravity.START);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesRecyclerView.ViewHolder holder, int position) {

        //Start Vertical Activity
        final Intent vIntent = new Intent(rContext, VerticalCourseListActivity.class);

        //final Intent intent = new Intent(rContext, );
        final String sectionName = dataList.get(position).getHeaderTitle();
        ArrayList<SingleItemModel> singleItemModels = dataList.get(position).getAllItemSection();
        holder.title.setText(sectionName);

        //setting Margin for RelativeLayout
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)holder.relativeLayout.getLayoutParams();
        params.setMargins(0,25,0,50);
        holder.relativeLayout.setLayoutParams(params);

        //holder.cardView.setElevation(10);

        //launching VerticalCourseListActivity
        holder.relativeLayout.setOnClickListener(v -> {
            rContext.startActivity(vIntent);
            //Toast.makeText(v.getContext(), sectionName, Toast.LENGTH_LONG).show();
        });

        //RecyclerView inside another RecyclerView
        SectionDataRecyclerView adapter = new SectionDataRecyclerView(rContext, singleItemModels);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(rContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setRecycledViewPool(recycledViewPool);
        snapHelper.attachToRecyclerView(holder.recyclerView);
    }

    @Override
    public int getItemCount() {
        return null != dataList ? dataList.size() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView recyclerView;
        CardView cardView;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.itemTitle);
            this.recyclerView = itemView.findViewById(R.id.single_item_recyclerView);
            this.cardView = itemView.findViewById(R.id.sectionId);
            this.relativeLayout = itemView.findViewById(R.id.listId);
            this.linearLayout = itemView.findViewById(R.id.sectionLinear);
        }
    }
}
