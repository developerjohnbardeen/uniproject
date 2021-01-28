package com.example.uniproject.coursesActivity;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uniproject.MainActivity;
import com.example.uniproject.R;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import java.util.ArrayList;
import java.util.Objects;


public class CoursesActivity extends AppCompatActivity {

    private ArrayList<SingleItemModel> courseList;
    private ImageView crsMenu;
    private Context crsContext;
    RecyclerView cRecyclerView;
    ArrayList<SectionDataModel> allSampleData = new ArrayList<>();
    RelativeLayout rlvLayout;
    private String msg = "Android : ";




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        crsContext = CoursesActivity.this;




        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.custom_imageview, null);
        TextView cTxt = view.findViewById(R.id.course_title);
        cTxt.setText(R.string.courses_list);
        rlvLayout = view.findViewById(R.id.bck_bttn_layout);
        rlvLayout.setOnClickListener(v -> onBackPressed());
        actionBar.setCustomView(view);



        //getting course data
        CourseThread thread = new CourseThread();
        thread.execute();
        //getCourseData();





    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
        //Toast.makeText(this, "CoursesActivity is on start..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
        //Toast.makeText(this, "CoursesActivity is on Resume..", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.crs_setting):
                // do whatever
                Toast.makeText(CoursesActivity.this, "All Course lists setting Menu..", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CourseThread extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute(){
            cRecyclerView = findViewById(R.id.courseRecyclerView);
            cRecyclerView.setHasFixedSize(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                getCourseData();
            }catch (Exception e){
                Toast.makeText(crsContext, "An error occurred", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids){
            //setting RecyclerView
            /*reading sample data*/
            //Row number one
            SectionDataModel courseOne = new SectionDataModel();
            courseOne.setHeaderTitle("Artificial Intelligence");
            courseOne.setAllItemInSection(courseList);
            allSampleData.add(courseOne);


            //Row number two
            SectionDataModel courseTwo = new SectionDataModel();
            courseTwo.setHeaderTitle("Machine Learning");
            courseTwo.setAllItemInSection(courseList);
            allSampleData.add(courseTwo);
            CoursesRecyclerView adapter = new CoursesRecyclerView(CoursesActivity.this, allSampleData);
            cRecyclerView.setLayoutManager(new LinearLayoutManager(CoursesActivity.this, LinearLayoutManager.VERTICAL, false));
            cRecyclerView.setAdapter(adapter);
        }
    }

    public void getCourseData(){
        //in final app it's needed to change Database..
        //to read each single database separately..
        //in another word, read database dynamically

        SQLiteDatabase db;
        CourseActivityDatabase courseDB;
        courseDB = new CourseActivityDatabase(this);

        db = courseDB.getReadableDatabase();
        courseList = new ArrayList<>();

        Cursor cursor = db.query(
                DB_TABLES.COURSE_TABLE_NAME,
                new String[]{DB_TABLES.COURSE_ID, DB_TABLES.LECTURE_IMAGE_URL, DB_TABLES.LECTURE_TOPIC},
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() != 0) {
            courseList.clear();
            while (cursor.moveToNext()) {
                SingleItemModel courseInfo = new SingleItemModel();

                //getting data from database
                int lecture_id = cursor.getInt(cursor.getColumnIndex(DB_TABLES.COURSE_ID));
                int lectureImg = cursor.getInt(cursor.getColumnIndex(DB_TABLES.LECTURE_IMAGE_URL));
                String lectureTopic = cursor.getString(cursor.getColumnIndex(DB_TABLES.LECTURE_TOPIC));



                //sending data to SingleItemModel
                courseInfo.setFlagId(lecture_id);
                courseInfo.setLectureImg(lectureImg);
                courseInfo.setLectureTopic(lectureTopic);

                //adding data to ArrayList
                courseList.add(courseInfo);

            }
        }
        //closing cursor and database
        assert cursor != null;
        cursor.close();
        db.close();
    }

    //Back arrow in toolbar
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
        //Toast.makeText(CoursesActivity.this, "MainActivity is on Pause..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
        //Toast.makeText(this, "CoursesActivity is on stop..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
        //Toast.makeText(this, "CoursesActivity has been destroyed..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "CoursesActivity is on Restart..", Toast.LENGTH_LONG).show();
    }

}