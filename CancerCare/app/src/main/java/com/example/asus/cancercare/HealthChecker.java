package com.example.asus.cancercare;

/**
 * Created by Asus on 17.12.2016.
 */

public class HealthChecker {

    private String name;
    private String banalysis;
    private String fever;
    private String urine;
    private String Date;

    public HealthChecker() {
        this.name = "";
        this.banalysis = null;
        this.fever = "";
        this.urine = "";
        this.Date = "";
    }


    public HealthChecker(String name, String fever, String banalysis,  String urine, String Date) {
        this.name = name;
        this.fever = fever;
        this.banalysis = banalysis;
        this.urine = urine;
        this.Date = Date;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getBanalysis() {
        return banalysis;
    }

    public void setBanalysis(String banalysis) {
        this.banalysis = banalysis;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getUrine() {
        return urine;
    }

    public void setUrine(String urine) {
        this.urine = urine;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }


}
