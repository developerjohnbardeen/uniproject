package com.example.uniproject.UpdateDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.SQLiteDatabase.Tutorials_SqliteOpenHelper;
import com.example.uniproject.coursesActivity.CourseActivityDatabase;

public class UpdateDatabase {
    private final Context upContext;

    public UpdateDatabase(Context context){
        this.upContext = context;
    }

    public void updateCourse(String column, int id){

        CourseActivityDatabase courseDatabase = new CourseActivityDatabase(upContext);
        SQLiteDatabase db = courseDatabase.getWritableDatabase();
        ContentValues courseValues = new ContentValues();
        courseValues.put(column, id);
        db.update(DB_TABLES.COURSE_TABLE_NAME,
                courseValues,
                DB_TABLES.COURSE_ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }
    public void updateTutorial(String column, int id){

        Tutorials_SqliteOpenHelper tutorials = new Tutorials_SqliteOpenHelper(upContext);
        SQLiteDatabase db = tutorials.getWritableDatabase();
        ContentValues tutorialsValues = new ContentValues();
        tutorialsValues.put(column, id);
        db.update(DB_TABLES.TABLE_NAME,
                tutorialsValues,
                DB_TABLES.ID + " = ?", new String[]{Integer.toString(id)});
        db.close();
    }









    //first group of code is the same as second group
                /*
                 topCluster.setImg(cursor.getInt(0));
                topCluster.setTitle(cursor.getString(1));
                topCluster.setDesc(cursor.getString(2));

                 topCluster.setImg(cursor.getInt(cursor.getColumnIndex(DB_TABLES.IMG_CLUSTER)));
                topCluster.setTitle(cursor.getString(cursor.getColumnIndex(DB_TABLES.CLUSTER_TOPIC)));
                topCluster.setDesc(cursor.getString(cursor.getColumnIndex(DB_TABLES.GENERAL_INFO_OF_CLUSTER)));
                 */
    //sending data to "Main" class


}
