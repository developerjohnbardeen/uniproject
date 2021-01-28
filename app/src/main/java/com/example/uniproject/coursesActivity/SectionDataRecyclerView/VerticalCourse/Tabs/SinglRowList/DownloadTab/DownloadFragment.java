package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.DownloadTab;

import android.app.Notification;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.HistoryTab.HistoryRecyclerView;
import com.example.uniproject.coursesActivity.SingleItemModel;
import com.google.android.exoplayer2.offline.Download;

import java.util.ArrayList;
import java.util.Objects;

public class DownloadFragment extends Fragment {
    private final Context context;
    private ArrayList<SingleItemModel> vdCourseList;
    private LinearLayout download_layout;
    private SQLiteDatabase db;
    private FragmentActivity listener;
    private RecyclerView vRecyclerView;
    private DownloadRecyclerView vdAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Toast.makeText(getActivity(), "downloadFragment is onAttach", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //Toast.makeText(getActivity(), "downloadFragment is on onCreated", Toast.LENGTH_LONG).show();
    }



    //public HistoryFragment(){}
    public DownloadFragment(Context hfContext){
        this.context = hfContext;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_download_list_fragment, container, false);
        download_layout = view.findViewById(R.id.download_layout);
        //sampleData(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        sampleData();
        vdAdapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Toast.makeText(getActivity(), "downloadFragment is on onActivityCreated", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        //Toast.makeText(getActivity(), "downloadFragment is on onStart", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        sampleData();
        //Toast.makeText(getActivity(), "downloadFragment is on onResume ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getActivity(), "downloadFragment is on onPause", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getActivity(), "downloadFragment is on onStop ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Toast.makeText(getActivity(), "downloadFragment is on onDestroyView", Toast.LENGTH_LONG).show();
    }



    public void sampleData(){
        vRecyclerView = getView().findViewById(R.id.download_RecyclerView_fragment);
        try {
            CourseActivityDatabase hCourseDB;
            hCourseDB = new CourseActivityDatabase(getActivity());

            db = hCourseDB.getReadableDatabase();
            vdCourseList = new ArrayList<>();

            Cursor cursor = db.query(
                    DB_TABLES.COURSE_TABLE_NAME,
                    new String[]{DB_TABLES.COURSE_ID, DB_TABLES.LECTURE_IMAGE_URL, DB_TABLES.LECTURE_TOPIC, DB_TABLES.LECTURE_GENERAL_INFO},
                    DB_TABLES.LECTURE_DOWNLOAD_FLAG + " > 0",
                    null, null, null, null);


            if (cursor != null && cursor.getCount() != 0) {
                vdCourseList.clear();
                while (cursor.moveToNext()) {
                    SingleItemModel courseInfo = new SingleItemModel();

                    int lecture_id = cursor.getInt(cursor.getColumnIndex(DB_TABLES.COURSE_ID));
                    int lectureImg = cursor.getInt(cursor.getColumnIndex(DB_TABLES.LECTURE_IMAGE_URL));
                    String lectureTopic = cursor.getString(cursor.getColumnIndex(DB_TABLES.LECTURE_TOPIC));
                    String lectureInfo = cursor.getString(cursor.getColumnIndex(DB_TABLES.LECTURE_GENERAL_INFO));

                    courseInfo.setFlagId(lecture_id);
                    courseInfo.setLectureImg(lectureImg);
                    courseInfo.setLectureTopic(lectureTopic);
                    courseInfo.setLectureInfo(lectureInfo);

                    vdCourseList.add(courseInfo);


                    if (vdCourseList.size() != 0){
                        download_layout.setVisibility(View.GONE);
                    }else {
                        download_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
            close(db, cursor);
        }catch (SQLiteException e){
            Toast.makeText(getActivity(), "Database is not available", Toast.LENGTH_LONG).show();
        }

        vdAdapter = new DownloadRecyclerView(getActivity(), vdCourseList);
        vRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        vRecyclerView.setAdapter(vdAdapter);
        //listVisibility();
        vdAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getActivity(), "downloadFragment is on destroy", Toast.LENGTH_LONG).show();
    }

    public void close(SQLiteDatabase db, Cursor cCursor){
        db.close();
        cCursor.close();
    }

}