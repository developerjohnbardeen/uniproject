package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse;


import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.uniproject.MainActivity;
import com.example.uniproject.R;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SectionPagerAdapter;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.DownloadTab.DownloadFragment;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.DownloadTab.DownloadRecyclerView;
import com.google.android.material.tabs.TabLayout;
import java.util.Objects;

public class VerticalCourseListActivity extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar toolbar;
    private TabLayout tabLayout;
    private TextView txt;
    private ImageView tab_img_bttn;
    private ViewPager viewPager;
    private SectionPagerAdapter adapter;
    private RelativeLayout tbRlvLyt;
    private String msg = "Android : ";

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_vertical_list_course);



        //toolbarTitle();
        toolbar = findViewById(R.id.toolbar);
        tbRlvLyt = findViewById(R.id.tab_layout_bck_bttn_layout);
        viewPager = findViewById(R.id.viewPager);
        txt = findViewById(R.id.tab_layout_course_title);
        tab_img_bttn = findViewById(R.id.tab_layout_back_button);


        adapter = new SectionPagerAdapter(VerticalCourseListActivity.this, getSupportFragmentManager());
        viewPager.setAnimationCacheEnabled(true);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        toolbarbackarrow();
        customTabLayout();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
        //Toast.makeText(this, "MainActivity is on start..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
        //Toast.makeText(this, "MainActivity is on Resume..", Toast.LENGTH_LONG).show();
    }




    public void customTabLayout(){

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.list);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.download_com);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(R.drawable.history);
        tbRlvLyt.setOnClickListener(v -> onBackPressed());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        txt.setText(R.string.course_List);
                        break;
                    case 1:
                        txt.setText(R.string.download);
                        break;
                    case 2:
                        txt.setText(R.string.history);
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //arrow back icon
    public void toolbarbackarrow(){

        setSupportActionBar(toolbar);
        tab_img_bttn.setOnClickListener(v -> onBackPressed());
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.vertical_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.vertical_list_setring_menu):
                // do whatever
                Toast.makeText(VerticalCourseListActivity.this, "Certain Course Setting Menu..", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
        //Toast.makeText(this, "MainActivity is on Pause..", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
        //Toast.makeText(this, "MainActivity is on stop..", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
        //Toast.makeText(this, "MainActivity has been destroyed..", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(this, "MainActivity is on Restart..", Toast.LENGTH_LONG).show();

    }
}
