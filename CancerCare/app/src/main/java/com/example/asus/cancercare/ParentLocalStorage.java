package com.example.asus.cancercare;

import android.content.SharedPreferences;
import android.util.Log;

public class ParentLocalStorage {

    private final String loggerTag = ParentLocalStorage.class.getSimpleName();
    private final static String idKey = "IDKEY";
    private final static String lastNameKey = "LASTNAMEKEY";
    private final static String nameKey = "NAMEKEY";
    private final static String phoneKey = "PHONEKEY";
    private final static String passwordKey = "PASSWORDKEY";
    private final static String addressKey = "ADDRESSKEY";
    private final static String cityKey = "CITYKEY";
    private final static String emailKey = "EMAILKEY";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    public ParentLocalStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        sharedPreferencesEditor = this.sharedPreferences.edit();
    }

    public void storeId(String id) {
        if (isNullOrWhiteSpace(id)) {
            Log.d(loggerTag, "Id is null or empty!");
            sharedPreferencesEditor.putString(idKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        sharedPreferencesEditor.putString(idKey, id);
        sharedPreferencesEditor.apply();
    }

    public void storeName(String name) {
        if (isNullOrWhiteSpace(name)) {
            Log.d(loggerTag, "Name is null or empty!");
            sharedPreferencesEditor.putString(nameKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        sharedPreferencesEditor.putString(nameKey, name);
        sharedPreferencesEditor.apply();
    }

    public void storeLastName(String surName) {
        if (isNullOrWhiteSpace(surName)) {
            Log.d(loggerTag, "Surname is null or empty!");
            sharedPreferencesEditor.putString(lastNameKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        sharedPreferencesEditor.putString(lastNameKey, surName);
        sharedPreferencesEditor.apply();
    }

    public void storePhoneNumber(String phoneNumber) {
        if (isNullOrWhiteSpace(phoneNumber)) {
            Log.d(loggerTag, "Phone number is null or empty!");
            sharedPreferencesEditor.putString(phoneKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        sharedPreferencesEditor.putString(phoneKey, phoneNumber);
        sharedPreferencesEditor.apply();
    }

    public void storePassword(String password) {
        if (isNullOrWhiteSpace(password)) {
            Log.d(loggerTag, "Password is null or empty!");
            sharedPreferencesEditor.putString(passwordKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        sharedPreferencesEditor.putString(passwordKey, password);
        sharedPreferencesEditor.apply();
    }

    public void storeAddress(String address) {
        if (isNullOrWhiteSpace(address)) {
            Log.d(loggerTag, "Adress is null or empty!");
            sharedPreferencesEditor.putString(addressKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        sharedPreferencesEditor.putString(addressKey, address);
        sharedPreferencesEditor.apply();
    }

    public void storeCity(String city) {
        if (isNullOrWhiteSpace(city)) {
            Log.d(loggerTag, "City is null or empty!");
            sharedPreferencesEditor.putString(cityKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        Log.d(loggerTag, "City is " + city);
        sharedPreferencesEditor.putString(cityKey, city);
        sharedPreferencesEditor.apply();
    }

    public void storeEmail(String email) {
        if (isNullOrWhiteSpace(email)) {
            Log.d(loggerTag, "Email is null or empty!");
            sharedPreferencesEditor.putString(emailKey, "");
            sharedPreferencesEditor.apply();
            return;
        }

        Log.d(loggerTag, "Email is " + email);
        sharedPreferencesEditor.putString(emailKey, email);
        sharedPreferencesEditor.apply();
    }

    public void storeParent(Parent parent) {
        if (parent == null) {
            Log.d(loggerTag, "Parent Is NULL!");
            return;
        }

        if (!isNullOrWhiteSpace(parent.getId())) {
            storeId(parent.getId());
        }

        storeName(parent.getName());
        storeLastName(parent.getLastName());
        storePhoneNumber(parent.getPhoneNumber());
        storePassword(parent.getPassword());
        storeCity(parent.getCity());
        storeAddress(parent.getAddress());
        storeEmail(parent.getEmail());
        sharedPreferencesEditor.commit();
    }

    public String getParentId() {
        return sharedPreferences.getString(idKey, null);
    }

    public String getParentName() {
        return sharedPreferences.getString(nameKey, null);
    }

    public String getParentLastName() {
        return sharedPreferences.getString(lastNameKey, null);
    }

    public String getParentPhoneNumber() {
        return sharedPreferences.getString(phoneKey, null);
    }

    public String getParentPassword() {
        return sharedPreferences.getString(passwordKey, null);
    }

    public String getParentCity() {
        return sharedPreferences.getString(cityKey, null);
    }

    public String getParentAddress() {
        return sharedPreferences.getString(addressKey, null);
    }

    public String getParentEmail() {
        return sharedPreferences.getString(emailKey, null);
    }

    public void clearStorage(){
        storeId(null);
        storeName(null);
        storeLastName(null);
        storePhoneNumber(null);
        storePassword(null);
        storeAddress(null);
        storeCity(null);
        //
        // storeEmail(null);
    }

    private boolean isNullOrWhiteSpace(String string){
        return (string == null || string.trim().equals(""));
    }
}