package com.example.asus.cancercare;

/**
 * Created by PC on 19.12.2016.
 */

public class MedicationData {
    public String m_name;
    public Long m_time;
    public Long quantity;
    public String fname;

    public MedicationData(String m_name, Long m_time, Long quantity) {
        this.m_name = m_name;
        this.m_time = m_time;
        this.quantity = quantity;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public Long getM_time() {
        return m_time;
    }

    public void setM_time(Long m_time) {
        this.m_time = m_time;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
