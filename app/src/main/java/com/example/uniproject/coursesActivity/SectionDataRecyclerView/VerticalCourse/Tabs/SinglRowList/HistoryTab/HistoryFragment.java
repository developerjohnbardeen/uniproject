package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.HistoryTab;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SingleItemModel;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryFragment extends Fragment {
    private final Context context;
    private ArrayList<SingleItemModel> vhCourseList;
    public LinearLayout his_layout;
    private SQLiteDatabase db;
    private Cursor cursor;
    private FragmentActivity listener;
    private RecyclerView vRecyclerView;
    private HistoryRecyclerView hAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //Toast.makeText(getActivity(), "HistoryFragment is onAttach", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //Toast.makeText(getActivity(), "HistoryFragment is on onCreated", Toast.LENGTH_LONG).show();
    }

    public HistoryFragment(Context hfContext){
        this.context = hfContext;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_history_list_fragment, container, false);
        his_layout = view.findViewById(R.id.history_layout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        sampleData(view);
        hAdapter.notifyItemChanged(vhCourseList.size());

    }


    public void sampleData(View view){
        vRecyclerView = view.findViewById(R.id.history_RecyclerView_fragment);

        try {
            CourseActivityDatabase hCourseDB;
            hCourseDB = new CourseActivityDatabase(context);

            db = hCourseDB.getReadableDatabase();
            vhCourseList = new ArrayList<>();

            cursor = db.query(
                    DB_TABLES.COURSE_TABLE_NAME,
                    new String[]{DB_TABLES.COURSE_ID, DB_TABLES.LECTURE_IMAGE_URL, DB_TABLES.LECTURE_TOPIC, DB_TABLES.LECTURE_GENERAL_INFO, DB_TABLES.LECTURE_HISTORY_FLAG},
                    DB_TABLES.LECTURE_HISTORY_FLAG + " > 0", null, null, null,  null);


            if (cursor != null && cursor.getCount() != 0) {
                vhCourseList.clear();
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

                    vhCourseList.add(courseInfo);

                    if (vhCourseList.size() != 0){
                        his_layout.setVisibility(View.INVISIBLE);
                    }else {
                        his_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }catch (SQLiteException e){
            Toast.makeText(getActivity(), "Database is not available..!!!", Toast.LENGTH_LONG).show();
        }

        hAdapter = new HistoryRecyclerView(context, vhCourseList);
        vRecyclerView.setLayoutManager(new GridLayoutManager(context,1));
        vRecyclerView.setAdapter(hAdapter);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
