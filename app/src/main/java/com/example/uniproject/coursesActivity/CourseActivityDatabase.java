package com.example.uniproject.coursesActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uniproject.R;

public class CourseActivityDatabase extends SQLiteOpenHelper {

    //Database
    public static final  int COURSE_DB_VERSION = 1;
    public static final String COURSE_DB = "COURSE_DATABASE";
    public static final String  COURSE_TABLE_NAME = "TABLE_NUMBER_ONE";

    //Columns
    public static final String COURSE_ID = "_id";
    public static final String LECTURE_IMAGE_URL = "IMAGE_URL";
    public static final String LECTURE_TOPIC = "LECTURE_TOPIC";
    public static final String LECTURE_GENERAL_INFO = "LECTURE_GENERAL_INFO";
    public static final String LECTURE_DOWNLOAD_FLAG = "LECTURE_DOWNLOAD_FLAG";
    public static final String LECTURE_HISTORY_FLAG = "LECTURE_HISTORY_FLAG";
    public static final String LECTURE_ADD_TO_LIST_FLAG = "LECTURE_ADD_TO_LIST_FLAG";
    public static final String LECTURE_FAVORITE_FLAG = "LECTURE_FAVORITE_FLAG";

    private Context cContext;

    private static final String COURSE_SQLITE_TABLE = "CREATE TABLE " + COURSE_TABLE_NAME + " (" +
            COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LECTURE_IMAGE_URL + " INTEGER," +
            LECTURE_TOPIC + " TEXT," +
            LECTURE_GENERAL_INFO + " TEXT," +
            LECTURE_DOWNLOAD_FLAG + " INTEGER," +
            LECTURE_ADD_TO_LIST_FLAG + " INTEGER," +
            LECTURE_FAVORITE_FLAG + " ENTEGER," +
            LECTURE_HISTORY_FLAG + " INTEGER);";



    public CourseActivityDatabase(Context context){
        super(context, COURSE_DB, null, COURSE_DB_VERSION);
        this.cContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        courseDatabaseUpdate(db,0, COURSE_DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE_NAME);
        courseDatabaseUpdate(db, oldVersion, newVersion);
    }


    public  void courseDatabaseUpdate(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(COURSE_SQLITE_TABLE);

        //insert values here
        insertCourseValues(db, R.drawable.computerscience,"Computer", "this is about computer",0,0,0, 0);
        insertCourseValues(db, R.drawable.machine,"Machine learning", "this is about machine",0,0,0 , 0);
        insertCourseValues(db, R.drawable.deeplearning,"Deep learning", "this is about deep learning",0,0,0, 0);
        insertCourseValues(db, R.drawable.apple,"AI", "this is about Artificial Intelligence",0,0,0, 0);
        insertCourseValues(db, R.drawable.dataanalysis,"DataAnalysis", "this is about dataAnalysis",0,0,0, 0);

        insertCourseValues(db, R.drawable.computerscience,"Computer", "this is about computer",0,0,0, 0);
        insertCourseValues(db, R.drawable.machine,"Machine learning", "this is about machine",0,0,0, 0);
        insertCourseValues(db, R.drawable.deeplearning,"Deep learning", "this is about deep learning",0,0,0, 0);
        insertCourseValues(db, R.drawable.apple,"AI", "this is about Artificial Intelligence",0,0,0, 0);
        insertCourseValues(db, R.drawable.dataanalysis,"DataAnalysis", "this is about dataAnalysis",0,0,0, 0);

        insertCourseValues(db, R.drawable.computerscience,"Computer", "this is about computer",0,0,0, 0);
        insertCourseValues(db, R.drawable.machine,"Machine learning", "this is about machine",0,0,0, 0);
        insertCourseValues(db, R.drawable.deeplearning,"Deep learning", "this is about deep learning",0,0,0, 0);
        insertCourseValues(db, R.drawable.apple,"AI", "this is about Artificial Intelligence",0,0,0, 0);
        insertCourseValues(db, R.drawable.dataanalysis,"DataAnalysis", "this is about dataAnalysis",0,0,0, 0);
    }

    public void insertCourseValues(SQLiteDatabase db,
                                   int lectureImage,
                                   String lectureTopic,
                                   String lectureInfo,
                                   int lecture_download_flag,
                                   int lecture_add_to_list_flag,
                                   int lecture_favorite_flag,
                                   int lecture_history_flag ){

        ContentValues insertCourseValues = new ContentValues();
        insertCourseValues.put(LECTURE_IMAGE_URL,lectureImage);
        insertCourseValues.put(LECTURE_TOPIC, lectureTopic);
        insertCourseValues.put(LECTURE_GENERAL_INFO, lectureInfo);
        insertCourseValues.put(LECTURE_DOWNLOAD_FLAG, lecture_download_flag);
        insertCourseValues.put(LECTURE_HISTORY_FLAG, lecture_add_to_list_flag);
        insertCourseValues.put(LECTURE_FAVORITE_FLAG, lecture_favorite_flag);
        insertCourseValues.put(LECTURE_ADD_TO_LIST_FLAG, lecture_history_flag);
        db.insert(COURSE_TABLE_NAME, null, insertCourseValues);
    }


}
