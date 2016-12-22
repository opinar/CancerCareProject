package com.example.asus.cancercare;


public class Child {
    private String fname;
    private String lname;
    private String birthday;
    private String bloodtype;

    public Child() {
        this.fname = "";
        this.lname = "";
        this.birthday = null;
        this.bloodtype = "";
    }

    public Child(String fname, String lname, String birthday, String bloodtype) {
        this.fname = fname;
        this.lname = lname;
        this.birthday = birthday;
        this.bloodtype = bloodtype;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getL_name() {
        return lname;
    }

    public void setL_name(String lname) {
        this.lname = lname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBloodtype() {
        return bloodtype;
    }

    public void setBloodtype(String bloodtype) {
        this.bloodtype = bloodtype;
    }

}