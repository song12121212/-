package com.example.androidfinal_project;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HistoryData {
    private String date; //날짜
    private String bodyfat; //체지방률
    private String cal; //칼로리 소모량
    private String time; //운동시간

    public HistoryData() { }

    public String getDate() { return date; }

    public String getBodyfat() { return bodyfat; }

    public String getCal() { return cal; }

    public String getTime() { return time; }

    public void setDate(String date) { this.date = date; }

    public void setBodyfat(String bodyfat) { this.bodyfat = bodyfat; }

    public void setCal(String cal) { this.cal = cal; }

    public void setTime(String time) { this.time = time; }

}
