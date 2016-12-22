package com.example.asus.cancercare;

/**
 * Created by Asus on 3.12.2016.
 */

public class Doctor {

    private String name;

    private String lastname;

    private String branch;

    private String doctorId;

    public Doctor() {
        this.name = "";
        this.lastname = "";
        this.branch = null;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getname() {
        return name;
    }

    public void setName(String fname) {
        this.name = fname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lname) {
        this.lastname = lname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}

