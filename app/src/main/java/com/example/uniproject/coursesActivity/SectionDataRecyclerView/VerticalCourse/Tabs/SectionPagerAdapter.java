package com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.DownloadTab.DownloadFragment;
import com.example.uniproject.coursesActivity.SectionDataRecyclerView.VerticalCourse.Tabs.SinglRowList.HistoryTab.HistoryFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private final Context fContext;


    public SectionPagerAdapter(@NonNull Context context, FragmentManager fm) {
        super(fm);
        fContext = context;
    }


    @Override
    public CharSequence getPageTitle(int position){

        if (position == 0){
            return "";
        }else if (position == 1){
            return "";
        }else {
            return "";
        }
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new VerticalListFragment(fContext);
        }else if (position == 1){
            return new DownloadFragment(fContext);
        }else {
            return new HistoryFragment(fContext);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
