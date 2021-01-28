package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.VerticalOneRowListRecyclerView;
import com.example.uniproject.coursesActivity.SingleItemModel;
import java.util.ArrayList;
import java.util.Objects;

public class VerticalListFragment extends Fragment {

    private ArrayList<SingleItemModel> vCourseList;
    private final Context vrContext;
    private Cursor cursor;
    private SQLiteDatabase db;
    private VerticalOneRowListRecyclerView vAdapter;
    private RecyclerView vRecyclerView;

    public VerticalListFragment(Context context) {
        this.vrContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_vertical_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        VerticalFragmentThread thread = new VerticalFragmentThread();
        thread.execute();
        //sampleData(view);
        //vAdapter.notifyDataSetChanged();

    }

    private class VerticalFragmentThread extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            vRecyclerView = Objects.requireNonNull(getView()).findViewById(R.id.vertical_Content_Recyclerview);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sampleData();
            }catch (Exception e){
                Toast.makeText(vrContext, "An error occurred", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids){
            vAdapter = new VerticalOneRowListRecyclerView(vrContext, vCourseList);
            vRecyclerView.setLayoutManager(new GridLayoutManager(vrContext,1));
            vAdapter.notifyDataSetChanged();
            vRecyclerView.setAdapter(vAdapter);
        }
    }


    public void sampleData(){


        CourseActivityDatabase courseDB = new CourseActivityDatabase(vrContext);
        db = courseDB.getReadableDatabase();
        vCourseList = new ArrayList<>();
        /*SQLiteStatement s = db.compileStatement();
        s.bindString();*/

        cursor = db.query(
                DB_TABLES.COURSE_TABLE_NAME,
                new String[]{DB_TABLES.COURSE_ID, DB_TABLES.LECTURE_IMAGE_URL, DB_TABLES.LECTURE_TOPIC, DB_TABLES.LECTURE_GENERAL_INFO},
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() != 0) {
            vCourseList.clear();
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

                vCourseList.add(courseInfo);

            }
        }
        //closing cursor and database
        assert cursor != null;
        cursor.close();
        db.close();


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
