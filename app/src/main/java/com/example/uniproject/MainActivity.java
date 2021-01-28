package com.example.uniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.uniproject.ItemClickSupport.ItemClickSupport;
import com.example.uniproject.SQLiteDatabase.DB_TABLES;
import com.example.uniproject.coursesActivity.CoursesActivity;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Main> mainList;
    private MainRecyclerView position;
    private String msg = "Android : ";
    private RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainThread mainThread = new MainThread();
        mainThread.execute();

        //sampleData();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private class MainThread extends AsyncTask<Void, Void, Void>{


        @Override
        protected void onPreExecute() {
            //RecyclerView related codes
            recyclerView = findViewById(R.id.mainRecyclerView);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sampleData();
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids){
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            MainRecyclerView mainAdapter = new MainRecyclerView(MainActivity.this, mainList);
            recyclerView.setAdapter(mainAdapter);

            ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView1, position, v) -> {
                if (position == 0){
                    Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
                    startActivity(intent);
                }
            });
        }


    }



    private void sampleData(){

        SQLiteDatabase db;
        MainSqliteDatabase mainDB;
        mainDB = new MainSqliteDatabase(this);

        db = mainDB.getReadableDatabase();
        mainList = new ArrayList<>();

        Cursor cursor = db.query(
                DB_TABLES.TOP_TABLE_NAME,
                new String[]{DB_TABLES.IMG_CLUSTER, DB_TABLES.CLUSTER_TOPIC, DB_TABLES.GENERAL_INFO_OF_CLUSTER},
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() != 0) {

            while (cursor.moveToNext()) {
                Main topCluster = new Main();
                topCluster.setImg(cursor.getInt(0));        //image of cluster
                topCluster.setTitle(cursor.getString(1));   // topic of cluster
                topCluster.setDesc(cursor.getString(2));    // general info of cluster

                mainList.add(topCluster);

            }
        }
        //closing cursor and database
        cursor.close();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.activity_main_setting):
                // do whatever
                Toast.makeText(MainActivity.this, "Main Activity Setting Menu..", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /*private void close(Cursor cursor, SQLiteDatabase db){
        cursor.close();
        db.close();
    }*/


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

}
