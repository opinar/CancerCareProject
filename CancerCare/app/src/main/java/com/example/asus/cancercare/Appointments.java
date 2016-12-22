package com.example.asus.cancercare;

/**
 * Created by Asus on 26.11.2016.
 */

public class Appointments {
    private String pname;
    private String drname;
    private String hospital;

    public Appointments() {
        this.pname = "";
        this.hospital = "";
        this.drname = "";


    }

    public Appointments(String pname, String hospital, String drname) {
        this.pname = pname;
        this.hospital = hospital;
        this.drname = drname;


    }

    public String getpname() {
        return pname;
    }

    public void setpname(String pname) {
        this.pname = pname;
    }

    public String getdrname() {
        return drname;
    }

    public void setdrname(String drname) {
        this.drname = drname;
    }

    public String gethospital() {
        return hospital;
    }

    public void sethospital(String hospital) {
        this.hospital = hospital;
    }
}




