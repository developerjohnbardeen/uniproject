package com.example.uniproject.coursesActivity;

import android.text.method.SingleLineTransformationMethod;

public class SingleItemModel {

    private int flagId;
    private int lectureImg;
    private String lectureTopic;
    private String lectureInfo;


    /*public SingleItemModel(int flag, int img, String tLecture, String iLecture){
        this.flagId = flag;
        this.lectureImg = img;
        this.lectureTopic = tLecture;
        this.lectureInfo = iLecture;
    }*/

    public SingleItemModel(){}

    /*public SingleItemModel(int flag, int img, String topic){
        this.flagId = flag;
        this.lectureImg = img;
        this.lectureTopic = topic;
    }*/

    //Getters
    public int getFlagId(){return flagId;}
    public int getLectureImg(){return lectureImg;}
    public String getLectureTopic(){return lectureTopic;}
    public String getLectureInfo(){return  lectureInfo;}




    //Setters
    public void setFlagId(int sFlagId){this.flagId = sFlagId;}
    public void setLectureImg(int sLectureImg){this.lectureImg = sLectureImg;}
    public void setLectureTopic(String sLectureName){this.lectureTopic = sLectureName;}
    public void setLectureInfo(String sLectureInfo){this.lectureInfo = sLectureInfo;}


}
