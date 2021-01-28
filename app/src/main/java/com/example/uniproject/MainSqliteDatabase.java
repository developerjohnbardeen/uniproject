package com.example.uniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MainSqliteDatabase extends SQLiteOpenHelper {
    //top_database_table_info
    public static final int TOP_DB_VERSION = 1;
    public static final String TOP_DATABASE = "CLUSTER_DATABASE";
    //top_table_name
    public static final String TOP_TABLE_NAME = "CLUSTER_TABLE";
    //detail_table_columns
    public static final String TOP_ID = "_topId";
    public static final String IMG_CLUSTER = "CLUSTER_IMAGE";
    public static final String CLUSTER_TOPIC = "CLUSTER_TOPIC";
    public static final String GENERAL_INFO_OF_CLUSTER = "GENERAL_INFO";
    public static final String MAIN_DOWNLOAD_FLAG = "MAIN_DOWNLOAD_FLAG";
    public static final String MAIN_HISTORY_FLAG = "MAIN_HISTORY_FLAG";
    public static final String MAIN_ADD_TO_LIST_FLAG = "MAIN_ADD_TO_LIST_FLAG";

    private Context context;

    private static final String SQLite_TOP_TABLE = "CREATE TABLE " + TOP_TABLE_NAME + " (" +
            TOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            IMG_CLUSTER + " INTEGER, " +
            CLUSTER_TOPIC + " TEXT, " +
            GENERAL_INFO_OF_CLUSTER + " TEXT, " +
            MAIN_DOWNLOAD_FLAG + " NUMERIC, " +
            MAIN_HISTORY_FLAG + " NUMERIC, " +
            MAIN_ADD_TO_LIST_FLAG + " NUMERIC);";




    public MainSqliteDatabase(Context sqlContext){
        super(sqlContext,TOP_DATABASE,null,TOP_DB_VERSION);
        this.context = sqlContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mainUpdateMyDatabase(db,0,TOP_DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOP_TABLE_NAME);
        mainUpdateMyDatabase(db, oldVersion, newVersion);
    }

    //populating data
    private void mainUpdateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQLite_TOP_TABLE);

       /*Row #1*/ insertTopTable(db, R.drawable.computerscience,context.getString(R.string.computer), context.getString(R.string.Computer),0,0,0);
       /*Row #2*/ insertTopTable(db, R.drawable.machine, context.getString(R.string.machine),context.getString(R.string.MachineLearning),0,0,0);
       /*Row #3*/ insertTopTable(db, R.drawable.ai, context.getString(R.string.ais),context.getString(R.string.AIS),0,0,0);
       /*Row #4*/ insertTopTable(db, R.drawable.dataanalysis,context.getString(R.string.dataanalysis), context.getString(R.string.DataAnalysis),0,0,0);
    }

    //Inserting data
    private static void insertTopTable(SQLiteDatabase db,
                                        int clusterImg,
                                        String clusterTopic,
                                        String clusterInfo,
                                        int download_flag,
                                        int history_flag,
                                        int add_to_list_flag){

        ContentValues insertTopTable = new ContentValues();
        insertTopTable.put(IMG_CLUSTER, clusterImg);
        insertTopTable.put(CLUSTER_TOPIC, clusterTopic);
        insertTopTable.put(GENERAL_INFO_OF_CLUSTER,clusterInfo);
        insertTopTable.put(MAIN_DOWNLOAD_FLAG, download_flag);
        insertTopTable.put(MAIN_HISTORY_FLAG, history_flag);
        insertTopTable.put(MAIN_ADD_TO_LIST_FLAG, add_to_list_flag);
        db.insert(TOP_TABLE_NAME,null, insertTopTable);
    }
}
