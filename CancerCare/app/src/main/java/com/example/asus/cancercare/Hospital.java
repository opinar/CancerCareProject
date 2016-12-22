package com.example.asus.cancercare;

import java.util.ArrayList;

/**
 * Created by Asus on 27.11.2016.
 */

public class Hospital {

    private String name;

    private String phoneNumber;

    private String address;

    private String city;

    private String email;

    private String hospitalId;

    public Hospital (){
        this.name = null;
        this.phoneNumber = null;
        this.address = null;
        this.city = null;
        this.email = null;
    }


    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
