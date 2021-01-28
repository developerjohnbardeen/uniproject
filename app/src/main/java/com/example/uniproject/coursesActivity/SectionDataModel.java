package com.example.uniproject.coursesActivity;

import java.util.ArrayList;

public class SectionDataModel {

    private String headerTitle;
    private ArrayList<SingleItemModel> allItemInSection;

    public SectionDataModel(){}

    public SectionDataModel(String headerTitle, ArrayList<SingleItemModel> allItemInSection){
        this.headerTitle = headerTitle;
        this.allItemInSection = allItemInSection;
    }

    public String getHeaderTitle(){return headerTitle;}
    public ArrayList<SingleItemModel> getAllItemSection(){return allItemInSection;}



    public void setHeaderTitle(String sHeaderTitle){this.headerTitle = sHeaderTitle;}
    public void setAllItemInSection(ArrayList<SingleItemModel> sAllItemInSection){this.allItemInSection = sAllItemInSection;}


}
