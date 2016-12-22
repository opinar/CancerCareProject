package com.example.asus.cancercare;

/**
 * Created by Asus on 27.11.2016.
 */

public class Parent {
    private String id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
    private String password;
    private String email;

    public Parent(){
        this.id = null;
        this.name = null;
        this.lastName = null;
        this.phoneNumber = null;
        this.address = null;
        this.city = null;
        this.password = null;
        this.email = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
