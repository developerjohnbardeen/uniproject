package com.example.uniproject;

public class Main {
    private String title;
    private String desc;
    private int img;


    public Main(int mImg, String mTitle, String mDesc){
        this.title = mTitle;
        this.desc = mDesc;
        this.img = mImg;
    }
    public Main(){}


    //Getters
    public int getImg(){return img;}
    public String getTitle(){return title;}
    public String getDesc(){return desc;}

    //Setters
    public void setTitle(String sTitle){this.title = sTitle;}
    public void setDesc(String sDesc){this.desc = sDesc;}
    public void setImg(int sImg){this.img = sImg;}


}
