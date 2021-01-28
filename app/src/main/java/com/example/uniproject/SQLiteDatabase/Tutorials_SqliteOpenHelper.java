package com.example.uniproject.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.uniproject.R;

public class Tutorials_SqliteOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String ID = "_id";
    public static final String DB_NAME = "TUTORIALS_DB";
    public static final String TABLE_NAME = "TUTORIALS_INFO";

    //detailed_info
    public static final String LECTURE_VIDEO_URL = "VIDEO_URL";
    public static final String LECTURE_TOPIC = "LECTURE_TOPIC"; // for both top and detailed
    public static final String TEACHER_NAME = "TEACHER_NAME";
    public static final String LECTURE_TIME_DURATION = "TIME";
    public static final String LECTURE_DETAIL_INFO = "LECTURE_INFO";
    public static final String TEACHER_IMAGE_URL = "TEACHER_IMAGE";
    public static final String TEACHER_PROFESSION = "TEACHER_PROFESSION";
    public static final String TEACHER_DETAIL_INFO = "TEACHER_INFO";
    public static final String HISTORY_FLAG = "FLAG_HISTORY";
    public static final String DOWNLOAD_FLAG = "FLAG_DOWNLOAD";
    public static final String ADD_TO_LIST_FLAG = "ADD_LIST_FLAG";
    public static final String FAVORITE_FLAG = "FAVORITE_FLAG";

    private Context tContext;




    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            LECTURE_VIDEO_URL + " TEXT, " +
            LECTURE_TOPIC + " TEXT," +
            TEACHER_NAME + " TEXT," +
            LECTURE_TIME_DURATION + " TEXT," +
            LECTURE_DETAIL_INFO + " TEXT," +
            TEACHER_IMAGE_URL + " INTEGER," +
            TEACHER_PROFESSION + " TEXT,"+
            TEACHER_DETAIL_INFO + " TEXT," +
            DOWNLOAD_FLAG + " INTEGER," +
            HISTORY_FLAG + " INTEGER," +
            FAVORITE_FLAG + " INTEGER, " +
            ADD_TO_LIST_FLAG + " INTEGER);";




    public Tutorials_SqliteOpenHelper(Context sqlContext){
        super(sqlContext, DB_NAME,null, DB_VERSION);
        this.tContext = sqlContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        updateMyDatabase(db, oldVersion, newVersion);

    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_CREATE_ENTRIES);

        //inserting table values

        insertTutorials(db, tContext.getString(R.string.media_url), "Computer", tContext.getString(R.string.teacher_name),"3:25", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Machine learning", tContext.getString(R.string.teacher_name),"10:55", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Deep learning", tContext.getString(R.string.teacher_name),"55:30", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "AI", tContext.getString(R.string.teacher_name),"20:45", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Data Analysis", tContext.getString(R.string.teacher_name),"19", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Computer", tContext.getString(R.string.teacher_name),"10", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Machine learning", tContext.getString(R.string.teacher_name),"50:23", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Deep learning", tContext.getString(R.string.teacher_name),"48:50", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "AI", tContext.getString(R.string.teacher_name),"30:56", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Data Analysis", tContext.getString(R.string.teacher_name),"7:10", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Computer", tContext.getString(R.string.teacher_name),"25:47", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Machine learning", tContext.getString(R.string.teacher_name),"11:30", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Deep learning", tContext.getString(R.string.teacher_name),"9:00", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "AI", tContext.getString(R.string.teacher_name),"36:40", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);
        insertTutorials(db, tContext.getString(R.string.media_url), "Data Analysis", tContext.getString(R.string.teacher_name),"40:30", tContext.getString(R.string.lecture_info),R.drawable.elonmusk, tContext.getString(R.string.teacher_profession),tContext.getString(R.string.teacher_info),0,0,0, 0);



    }

    private static void insertTutorials(SQLiteDatabase db,
                                        String lectureVideoUrl,     // indexColumn 0
                                        String lectureTopic,        // indexColumn 1
                                        String teacherName,         // indexColumn 2
                                        String timeLength,             // indexColumn 3
                                        String lectureInfo,         // indexColumn 4
                                        int teacherImg,             // indexColumn 5
                                        String teacherPro,          // indexColumn 6
                                        String teacherInfo,         // indexColumn 7
                                        int download_flag,          // indexColumn 8
                                        int history_flag,           // indexColumn 9
                                        int favorite_flag ,         // indexColumn 10
                                        int add_to_list_flag){      // indexColumn 11

        ContentValues tutorialsValues = new ContentValues();
        tutorialsValues.put(LECTURE_VIDEO_URL, lectureVideoUrl);
        tutorialsValues.put(LECTURE_TOPIC, lectureTopic);
        tutorialsValues.put(TEACHER_NAME, teacherName);
        tutorialsValues.put(LECTURE_TIME_DURATION,timeLength);
        tutorialsValues.put(LECTURE_DETAIL_INFO, lectureInfo);
        tutorialsValues.put(TEACHER_IMAGE_URL,teacherImg);
        tutorialsValues.put(TEACHER_PROFESSION, teacherPro);
        tutorialsValues.put(TEACHER_DETAIL_INFO, teacherInfo);
        tutorialsValues.put(DOWNLOAD_FLAG, download_flag);
        tutorialsValues.put(HISTORY_FLAG, history_flag);
        tutorialsValues.put(ADD_TO_LIST_FLAG, add_to_list_flag);
        tutorialsValues.put(FAVORITE_FLAG, favorite_flag);
        db.insert(TABLE_NAME,null, tutorialsValues);
    }

}
