package com;

import java.util.Date;

public class Temperature {
    private String date=null;
    private int tmax;
    private int tmin;

    public Temperature(String date,int tmax,int tmin){
        this.date=date;
        this.tmax=tmax;
        this.tmin=tmin;
    }

    public String getDate(){
        return date;
    }

    public int getTmax(){
        return tmax;
    }

    public int getTmin(){
        return tmin;
    }

    public void setDate(String date){
        this.date=date;
    }

    public void setTmax(int tmax){
        this.tmax=tmax;
    }

    public void setTmin(int tmin){
        this.tmin=tmin;
    }
}
