package com.example.uniproject.SQLiteDatabase;

public class DB_TABLES {

    //MainDatabase
    public static final int TOP_DB_VERSION = 1;
    public static final String TOP_DATABASE = "CLUSTER_DATABASE";
    public static final String TOP_TABLE_NAME = "CLUSTER_TABLE";
    public static final String TOP_ID = "_topId";
    public static final String IMG_CLUSTER = "CLUSTER_IMAGE";
    public static final String CLUSTER_TOPIC = "CLUSTER_TOPIC";
    public static final String GENERAL_INFO_OF_CLUSTER = "GENERAL_INFO";
    public static final String MAIN_DOWNLOAD_FLAG = "MAIN_DOWNLOAD_FLAG";
    public static final String MAIN_HISTORY_FLAG = "MAIN_HISTORY_FLAG";
    public static final String MAIN_ADD_TO_LIST_FLAG = "MAIN_ADD_TO_LIST_FLAG";


    //CourseDatabase
    public static final  int COURSE_DB_VERSION = 1;
    public static final String COURSE_DB = "COURSE_DATABASE";
    public static final String  COURSE_TABLE_NAME = "TABLE_NUMBER_ONE";
    public static final String COURSE_ID = "_id";
    public static final String LECTURE_IMAGE_URL = "IMAGE_URL";// for both top and detailed
    public static final String LECTURE_TOPIC = "LECTURE_TOPIC";// for both top and detailed
    public static final String LECTURE_GENERAL_INFO = "LECTURE_GENERAL_INFO";
    public static final String LECTURE_DOWNLOAD_FLAG = "LECTURE_DOWNLOAD_FLAG";
    public static final String LECTURE_HISTORY_FLAG = "LECTURE_HISTORY_FLAG";
    public static final String LECTURE_ADD_TO_LIST_FLAG = "LECTURE_ADD_TO_LIST_FLAG";
    public static final String LECTURE_FAVORITE_FLAG = "LECTURE_FAVORITE_FLAG";


    //TutorialDatabase
    public static final int DB_VERSION = 1;
    public static final String ID = "_id";
    public static final String DB_NAME = "TUTORIALS_DB";
    public static final String TABLE_NAME = "TUTORIALS_INFO";
    public static final String LECTURE_VIDEO_URL = "VIDEO_URL";
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
}
