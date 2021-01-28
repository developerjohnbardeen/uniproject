package com.example.uniproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainRecyclerView extends RecyclerView.Adapter<MainRecyclerView.ViewHolder> {
    private Context mContext;
    private List<Main> mainList;
    public static int mPositioin;
    public MainSqliteDatabase mainDB;


    public MainRecyclerView(Context context, List<Main> mainList){
        this.mContext = context;
        this.mainList = mainList;
        this.mainDB = new MainSqliteDatabase(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_activity,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Main cardTitle = mainList.get(position);

        holder.mImg.setImageResource(cardTitle.getImg());
        holder.mTitle.setText(cardTitle.getTitle());
        holder.mDesc.setText(cardTitle.getDesc());
        mPositioin = position;
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mDesc;
        ImageView mImg;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.mainTitle);
            mDesc = itemView.findViewById(R.id.mainDesc);
            mImg = itemView.findViewById(R.id.mainImg);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
